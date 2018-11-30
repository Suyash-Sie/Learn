package com.iris.webapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iris.webapp.dao.FoodDAO;
import com.iris.webapp.dao.OrderDAO;
import com.iris.webapp.dao.OrderDetailDAO;
import com.iris.webapp.dao.ProductDAO;
import com.iris.webapp.dao.RestaurantDAO;
import com.iris.webapp.dao.StationDAO;
import com.iris.webapp.entity.Food;
import com.iris.webapp.entity.OrderDetail;
import com.iris.webapp.entity.Product;
import com.iris.webapp.entity.Restaurant;
import com.iris.webapp.form.CustomerForm;
import com.iris.webapp.model.CartInfo;
import com.iris.webapp.model.CustomerInfo;
import com.iris.webapp.model.Foo;
import com.iris.webapp.model.ProductInfo;
import com.iris.webapp.utils.PaginationResult;
import com.iris.webapp.utils.Utils;
import com.iris.webapp.validator.CustomerFormValidator;

@Controller
@Transactional
public class MainController {

	@Autowired
	private OrderDAO orderDAO;

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private StationDAO stationDAO;

	@Autowired
	private RestaurantDAO restaurantDAO;

	@Autowired
	private FoodDAO foodDAO;

	@Autowired
	private OrderDetailDAO orderDetailDAO;

	@Autowired
	private CustomerFormValidator customerFormValidator;

	private Map<Restaurant, List<Food>> foodItemsPerRestaurant;

	@InitBinder
	public void myInitBinder(WebDataBinder dataBinder) {
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}
		System.out.println("Target=" + target);

		// Case update quantity in cart
		// (@ModelAttribute("cartForm") @Validated CartInfo cartForm)
		if (target.getClass() == CartInfo.class) {

		}

		// Case save customer information.
		// (@ModelAttribute @Validated CustomerInfo customerForm)
		else if (target.getClass() == CustomerForm.class) {
			dataBinder.setValidator(customerFormValidator);
		}

	}

	@RequestMapping("/403")
	public String accessDenied() {
		return "/403";
	}

//	@RequestMapping("/")
//	public String home(Model model) {
//		List<String> allStations = stationDAO.getAllStations();
//		model.addAttribute("stations", allStations);
//		return "index";
//	}

	@RequestMapping(value = { "/getRestaurants" }, method = RequestMethod.POST)
	public Map<Restaurant, List<Food>> getRestaurants(Model model, //
			@RequestParam(value = "station", defaultValue = "") String station) {
		int stationIdFromName = stationDAO.getStationIdFromName(station);
		foodItemsPerRestaurant = new HashMap<>();
		if (stationIdFromName != -1) {
			List<Restaurant> allRestaurantsForStation = restaurantDAO.getAllRestaurantsForStation(stationIdFromName);
			model.addAttribute("restaurants", allRestaurantsForStation);
			List<String> restNames = new ArrayList<>();
			for (Restaurant restaurant : allRestaurantsForStation) {
				List<Food> foodItemsOfRestaurant = foodDAO.getFoodItemsOfRestaurant(restaurant.getId());
				foodItemsPerRestaurant.put(restaurant, foodItemsOfRestaurant);
				restNames.add(restaurant.getName());
			}
			Foo foo = new Foo();
			foo.setCheckedItems(restNames);
			model.addAttribute("foodItems", foodItemsPerRestaurant);
			model.addAttribute("foo", foo);
		}
		return foodItemsPerRestaurant;
	}
	
	@RequestMapping(value = { "/filterItems" }, method = RequestMethod.POST)
	public String filterFoodItems(Model model, //
			@ModelAttribute(value="foo") Foo foo) {
		Iterator<Entry<Restaurant, List<Food>>> iterator = foodItemsPerRestaurant.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<Restaurant, List<Food>> pair = (Map.Entry<Restaurant, List<Food>>)iterator.next();
			if(!foo.getCheckedItems().contains(pair.getKey().getName()))
				iterator.remove();
		}
		model.addAttribute("foodItems", foodItemsPerRestaurant);
		return "filteredItemsList";
	}

	@RequestMapping(value = { "/addToCart" }, method = RequestMethod.POST)
	public String addFoodItems(Model model, //
			@ModelAttribute(value="foo") Foo foo) {
		Map<String, String> cartItems = new HashMap<>();
		for(int i=0; i<foo.getCheckedItems().size(); i++)
			cartItems.put(foo.getCheckedItems().get(i), foo.getQuantity().get(i));
		model.addAttribute("cartItems", createOrderDetail(foo));
		return "viewCart";
	}

	private OrderDetail createOrderDetail(Foo foo) {
		OrderDetail detail = new OrderDetail();
		
		int maxOrderId = orderDetailDAO.getMaxOrderId();
		detail.setId(maxOrderId + 1);
		List<Food> foodItemsFromListOfNames = foodDAO.getFoodItemsFromListOfRestaurantIds(foo.getCheckedItems());
		detail.setFood(foodItemsFromListOfNames);
		return detail;
	}

	// Product List
	@RequestMapping({ "/productList" })
	public String listProductHandler(Model model, //
			@RequestParam(value = "name", defaultValue = "") String likeName,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		final int maxResult = 5;
		final int maxNavigationPage = 10;

		PaginationResult<ProductInfo> result = productDAO.queryProducts(page, //
				maxResult, maxNavigationPage, likeName);

		model.addAttribute("paginationProducts", result);
		return "productList";
	}

	@RequestMapping({ "/buyProduct" })
	public String listProductHandler(HttpServletRequest request, Model model, //
			@RequestParam(value = "code", defaultValue = "") String code) {

		Product product = null;
		if (code != null && code.length() > 0) {
			product = productDAO.findProduct(code);
		}
		if (product != null) {

			//
			CartInfo cartInfo = Utils.getCartInSession(request);

			ProductInfo productInfo = new ProductInfo(product);

			cartInfo.addProduct(productInfo, 1);
		}

		return "redirect:/shoppingCart";
	}

	@RequestMapping({ "/shoppingCartRemoveProduct" })
	public String removeProductHandler(HttpServletRequest request, Model model, //
			@RequestParam(value = "code", defaultValue = "") String code) {
		Product product = null;
		if (code != null && code.length() > 0) {
			product = productDAO.findProduct(code);
		}
		if (product != null) {

			CartInfo cartInfo = Utils.getCartInSession(request);

			ProductInfo productInfo = new ProductInfo(product);

			cartInfo.removeProduct(productInfo);

		}

		return "redirect:/shoppingCart";
	}

	// POST: Update quantity for product in cart
	@RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.POST)
	public String shoppingCartUpdateQty(HttpServletRequest request, //
			Model model, //
			@ModelAttribute("cartForm") CartInfo cartForm) {

		CartInfo cartInfo = Utils.getCartInSession(request);
		cartInfo.updateQuantity(cartForm);

		return "redirect:/shoppingCart";
	}

	// GET: Show cart.
	@RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.GET)
	public String shoppingCartHandler(HttpServletRequest request, Model model) {
		CartInfo myCart = Utils.getCartInSession(request);

		model.addAttribute("cartForm", myCart);
		return "shoppingCart";
	}

	// GET: Enter customer information.
	@RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.GET)
	public String shoppingCartCustomerForm(HttpServletRequest request, Model model) {

		CartInfo cartInfo = Utils.getCartInSession(request);

		if (cartInfo.isEmpty()) {

			return "redirect:/shoppingCart";
		}
		CustomerInfo customerInfo = cartInfo.getCustomerInfo();

		CustomerForm customerForm = new CustomerForm(customerInfo);

		model.addAttribute("customerForm", customerForm);

		return "shoppingCartCustomer";
	}

	// POST: Save customer information.
	@RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.POST)
	public String shoppingCartCustomerSave(HttpServletRequest request, //
			Model model, //
			@ModelAttribute("customerForm") @Validated CustomerForm customerForm, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			customerForm.setValid(false);
			// Forward to reenter customer info.
			return "shoppingCartCustomer";
		}

		customerForm.setValid(true);
		CartInfo cartInfo = Utils.getCartInSession(request);
		CustomerInfo customerInfo = new CustomerInfo(customerForm);
		cartInfo.setCustomerInfo(customerInfo);

		return "redirect:/shoppingCartConfirmation";
	}

	// GET: Show information to confirm.
	@RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.GET)
	public String shoppingCartConfirmationReview(HttpServletRequest request, Model model) {
		CartInfo cartInfo = Utils.getCartInSession(request);

		if (cartInfo == null || cartInfo.isEmpty()) {

			return "redirect:/shoppingCart";
		} else if (!cartInfo.isValidCustomer()) {

			return "redirect:/shoppingCartCustomer";
		}
		model.addAttribute("myCart", cartInfo);

		return "shoppingCartConfirmation";
	}

	// POST: Submit Cart (Save)
	@RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.POST)

	public String shoppingCartConfirmationSave(HttpServletRequest request, Model model) {
		CartInfo cartInfo = Utils.getCartInSession(request);

		if (cartInfo.isEmpty()) {

			return "redirect:/shoppingCart";
		} else if (!cartInfo.isValidCustomer()) {

			return "redirect:/shoppingCartCustomer";
		}
		try {
			orderDAO.saveOrder(cartInfo);
		} catch (Exception e) {

			return "shoppingCartConfirmation";
		}

		// Remove Cart from Session.
		Utils.removeCartInSession(request);

		// Store last cart.
		Utils.storeLastOrderedCartInSession(request, cartInfo);

		return "redirect:/shoppingCartFinalize";
	}

	@RequestMapping(value = { "/shoppingCartFinalize" }, method = RequestMethod.GET)
	public String shoppingCartFinalize(HttpServletRequest request, Model model) {

		CartInfo lastOrderedCart = Utils.getLastOrderedCartInSession(request);

		if (lastOrderedCart == null) {
			return "redirect:/shoppingCart";
		}
		model.addAttribute("lastOrderedCart", lastOrderedCart);
		return "shoppingCartFinalize";
	}

	@RequestMapping(value = { "/productImage" }, method = RequestMethod.GET)
	public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam("code") String code) throws IOException {
		Product product = null;
		if (code != null) {
			product = this.productDAO.findProduct(code);
		}
		if (product != null && product.getImage() != null) {
			response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
			response.getOutputStream().write(product.getImage());
		}
		response.getOutputStream().close();
	}

}