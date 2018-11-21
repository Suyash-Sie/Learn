import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from 'rxjs/Rx';

@Injectable({
  providedIn: 'root'
})
export class RestaurantService {
  public API = '//localhost:8080';
  public REST_API = this.API + '/station';
  
  constructor(private http: HttpClient) {
  }
  
  getRestaurantsAtStation(id: string): Observable<any> {
	  return this.http.get(this.REST_API + '/' + id);
  }
}
