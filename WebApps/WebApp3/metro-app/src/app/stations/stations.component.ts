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
	quantityPerItem: Array<Mapping>;
	quantity : number = 0;
	enableRemove: boolean = false;
	
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
			this.restService.getRestaurantsAtStation(filterVal).subscribe(data => {
				this.restaurants = data;
				let names = [];
				let i = 0;
				this.quantity = 0;
				for(var rest of data) {
					names[i++] = rest.id;
				}
				i = 0;
				this.foodService.getFoodItemsAtRestaurants(names).subscribe(food => {
					for(var f of food) {
						this.quantityPerItem = {id: f.id, value: 0};
					}
					this.foodItems = food;
				});
			});
		}
	}
	
	increaseQuantity(value: number){
        this.quantity = value + 1;
		this.enableRemove = true;
    }
	
    decreaseQuantity(value: number){
		this.quantity = value - 1;
		if(this.quantity == 0)
			this.enableRemove = false;
		else
			this.enableRemove = true;
    }
}