import { Component, OnInit } from '@angular/core';
import { StationService } from '../station.service';
import { RestaurantService } from '../restaurant.service';

@Component({
  selector: 'app-stations',
  templateUrl: './stations.component.html',
  styleUrls: ['./stations.component.css']
})
export class StationsComponent implements OnInit {

  stations: Array<any>;
  restaurants: Array<any>;
  
  constructor(private stationService: StationService, private restService: RestaurantService) { }

  ngOnInit() {
    this.stationService.getAllStations().subscribe(data => {
      this.stations = data;
    });
  }
  
  getRestaurantsForStation(filterVal: any) {
	if(filterVal != "0") {
		this.restService.getRestaurantsAtStation(filterVal).subscribe(data => 
		{	return this.restaurants = data;	}
		);
	}
  }
}
