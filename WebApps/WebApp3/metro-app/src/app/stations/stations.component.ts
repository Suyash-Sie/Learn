import { Component, OnInit } from '@angular/core';
import { StationService } from '../station.service';
import { Router } from '@angular/router';

import { RestaurantService } from '../restaurant.service';
import { Restaurant } from '../restaurants/restaurants.model';
import { FoodService } from '../food.service';
import { Food } from '../food/food.model';
import { Mapping } from './mapping.model';

@Component({
  selector: 'app-stations',
  templateUrl: './stations.component.html',
  styleUrls: ['./stations.component.css']
})
export class StationsComponent implements OnInit {
	stations: Array<any>;
	restaurants: Array<Restaurant>;
	foodItems: Array<Food>;
	quantityPerItem: Map<Number, Number>;
	quantity : number = 0;
	disableRemove: Map<Number, boolean>;
	restCheckbox: Map<Number, boolean>;
	names: Array<number>;
	onlyOneRestaurant: boolean = false;
	itemsInCart: Map<Food, number>;
	cartTotal: number;
	
	constructor(private stationService: StationService,
			private router: Router,
			private restService: RestaurantService,
			private foodService: FoodService) { }

	ngOnInit() {
		this.stationService.getAllStations().subscribe(data => {
			this.stations = data;
		});
	}
  
	getRestaurantsForStation(filterVal: string) {
		if(filterVal != "0") {
			this.restCheckbox = new Map();
			this.restService.getRestaurantsAtStation(filterVal).subscribe(data => {
				this.restaurants = data;
				this.names = [];
				this.itemsInCart = new Map();
				let i = 0;
				this.quantity = 0;
				for(var rest of data) {
					this.names[i++] = rest.id;
					this.restCheckbox.set(rest.id, true);
				}
				if(i == 1)
					this.onlyOneRestaurant = true;
				else
					this.onlyOneRestaurant = false;
				this.quantityPerItem = new Map();
				this.disableRemove = new Map();
				this.populateFoodItems();
			});
		}
	}
	
	populateFoodItems() {
		this.foodService.getFoodItemsAtRestaurants(this.names).subscribe(food => {
			for(var f of food) {
				if(this.quantityPerItem.get(f.id) == null)
					this.quantityPerItem.set(f.id, 0);
				if(this.disableRemove.get(f.id) == null)
					this.disableRemove.set(f.id, true);
			}
			this.foodItems = food;
		});
	}
	
	increaseQuantity(item: Food, value: number){
		this.quantityPerItem.set(item.id, value + 1);
		this.disableRemove.set(item.id, false);
		this.itemsInCart.set(item, value + 1);
		this.cartTotal = 0;
		this.itemsInCart.forEach((value: number, key: Food) => {
       		this.cartTotal = this.cartTotal + (key.price * value);
    	});
    }
	
    decreaseQuantity(item: Food, value: number){
		this.quantityPerItem.set(item.id, value - 1);
		if(value - 1 == 0) {
			this.disableRemove.set(item.id, true);
	    	this.itemsInCart.delete(item);
		}
		else {
			this.disableRemove.set(item.id, false);
			this.itemsInCart.set(item, value - 1);
		}
		this.cartTotal = 0;
		this.itemsInCart.forEach((value: number, key: Food) => {
       		this.cartTotal = this.cartTotal + (key.price * value);
    	});
    }
    
    onRestSelection(id: number, value: boolean) {
    	this.restCheckbox.set(id, value);
    	if(value == false) {
	    	let index = this.names.indexOf(id);
	    	this.names.splice(index, 1);
    	} else
    		this.names.push(id);
    	this.populateFoodItems();
    }
}