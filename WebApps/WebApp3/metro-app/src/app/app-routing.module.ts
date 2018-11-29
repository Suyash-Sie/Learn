import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RestaurantsComponent } from './restaurants/restaurants.component'
import { StationsComponent } from './stations/stations.component'

const routes: Routes = [
	{
		path: '',
		component: StationsComponent
	},
	{ 
		path: 'station/:id', 
		component: StationsComponent 
	}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
