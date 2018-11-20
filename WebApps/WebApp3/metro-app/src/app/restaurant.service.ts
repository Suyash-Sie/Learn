import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RestaurantService {

  constructor(private http: HttpClient) {
  }
  
  getRestaurantsAtStation(stationName: string) {
	  return this.http.get('//localhost:8080/getRestaurants');
  }
}
