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
	names: Array<Number>;
	
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
				let i = 0;
				this.quantity = 0;
				for(var rest of data) {
					this.names[i++] = rest.id;
					this.restCheckbox.set(rest.id, true);
				}
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
	
	increaseQuantity(id: number, value: number){
		this.quantityPerItem.set(id, value + 1);
		this.disableRemove.set(id, false);
    }
	
    decreaseQuantity(id: number, value: number){
		this.quantityPerItem.set(id, value - 1);
		if(value - 1 == 0)
			this.disableRemove.set(id, true);
		else
			this.disableRemove.set(id, false);
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
