import { Component, OnInit } from '@angular/core';
import { StationService } from '../station.service';

@Component({
  selector: 'app-stations',
  templateUrl: './stations.component.html',
  styleUrls: ['./stations.component.css']
})
export class StationsComponent implements OnInit {

  stations: Array<any>;

  constructor(private stationService: StationService) { }

  ngOnInit() {
    this.stationService.getAllStations().subscribe(data => {
      this.stations = data;
    });
  }
  
  getRestaurantsForStation(filterVal: any) { 
	if(filterVal != "0")
		this.stationService.getAllStations();
  }

}
