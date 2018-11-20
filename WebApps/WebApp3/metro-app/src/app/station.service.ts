import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class StationService {

  constructor(private http: HttpClient) {
  }

  getAllStations(): Observable<any> {
    return this.http.get('//localhost:8080/getStations');
  }
}
