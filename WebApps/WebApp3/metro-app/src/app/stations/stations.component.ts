import { Component, OnInit } from '@angular/core';
import { StationService } from '../station.service';
import { Router } from '@angular/router';

import { RestaurantService } from '../restaurant.service';
import { Restaurant } from '../restaurants/restaurants.model'
import { FoodService } from '../food.service';
import { Food } from '../food/food.model'

@Component({
  selector: 'app-stations',
  templateUrl: './stations.component.html',
  styleUrls: ['./stations.component.css']
})
export class StationsComponent implements OnInit {
	stations: Array<any>;
	restaurants: Array<Restaurant>;
	foodItems: Array<Food>;
	
	constructor(private stationService: StationService, 
			private router: Router, 
			private restService: RestaurantService, 
			private foodService: FoodService) { }

	ngOnInit() {
		this.stationService.getAllStations().subscribe(data => {
			this.stations = data;
		});
	}
  
	getRestaurantsForStation(filterVal: any) {
		if(filterVal != "0") {
			this.restService.getRestaurantsAtStation(filterVal).subscribe(data => {
				this.restaurants = data;
				let names = [];
				let i = 0;
				for(var rest of data) {
					names[i++] = rest.id;
				}
				this.foodService.getFoodItemsAtRestaurants(names).subscribe(food => {
					this.foodItems = food;
				});
			});
		}
	}
}
