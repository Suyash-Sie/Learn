import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable({
  providedIn: 'root'
})
export class RestaurantService {

  constructor(private http: HttpClient) {
  }
  
  getRestaurantsAtStation(String stationName) {
	  let params = new HttpParams();
	  params = params.append('station', stationName);
	  return this.http.get('//localhost:8080/getRestaurants', {params: params});
  }
}
