package com.iris.webapp.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iris.webapp.dao.FoodDAO;
import com.iris.webapp.dao.RestaurantDAO;
import com.iris.webapp.dao.StationDAO;
import com.iris.webapp.entity.Food;
import com.iris.webapp.entity.Restaurant;
import com.iris.webapp.entity.Station;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class HomeController {

	@Autowired
	private StationDAO stationDAO;
	
	@Autowired
	private RestaurantDAO restDAO;

	@Autowired
	private FoodDAO foodDAO;

	@RequestMapping("/getStations")
	public List<Station> home() {
		List<Station> allStations = stationDAO.getAllStations();
		return allStations;
	}

	@RequestMapping("/station/{id}")
	public List<Restaurant> getRestaurantsAtStation(@PathVariable("id") int id) {
		List<Restaurant> allRestaurants = restDAO.getAllRestaurantsForStation(id);
		return allRestaurants;
	}

	@RequestMapping("/restaurant/{restaurantIds}")
	public List<Food> getFoodAtRestaurants(@PathVariable String[] restaurantIds) {
		List<Food> foodItems = foodDAO.getFoodItemsFromListOfRestaurantIds(Arrays.asList(restaurantIds));
		return foodItems;
	}

}