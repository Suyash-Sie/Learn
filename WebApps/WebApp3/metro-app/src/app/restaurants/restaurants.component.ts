import { Component, OnInit } from '@angular/core';
import { RestaurantService } from '../restaurant.service';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

@Component({
	selector: 'app-restaurants',
	templateUrl: './restaurants.component.html',
	styleUrls: ['./restaurants.component.css']
})
export class RestaurantsComponent implements OnInit {
	restaurants: Array<any>;
	
	constructor(private route: ActivatedRoute, 
				private restService: RestaurantService, 
				private location: Location) { }

	ngOnInit() {
		this.getStation();
	}

	getStation(): void {
		const id = this.route.snapshot.paramMap.get('id');
		this.restService.getRestaurantsAtStation(id).subscribe(data => 
		{	return this.restaurants = data;	}
		);
	}
}
