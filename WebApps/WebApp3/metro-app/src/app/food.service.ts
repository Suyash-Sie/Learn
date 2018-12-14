import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

@Injectable({
  providedIn: 'root'
})
export class FoodService {
	public API = '//localhost:8080';
	public REST_API = this.API + '/restaurant/';
  
	constructor(private http: HttpClient) { }
	
	getFoodItemsAtRestaurants(ids: Array<number>): Observable<any> {
		return this.http.get(this.REST_API + ids);
	}
}
