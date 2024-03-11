import { Routes } from '@angular/router';
import { TripComponent } from './trip/trip.component';
import { HomeComponent } from './home/home.component';
import { InsuranceComponent } from './insurance/insurance.component';
import { VehicleComponent } from './vehicle/vehicle.component';
import {VehicleDetailsComponent} from "./vehicle-details/vehicle-details.component";
import {RouterModule, Routes} from '@angular/router';
import {TripComponent} from './trip/trip.component';
import {HomeComponent} from './home/home.component';
import {ContractOverviewComponent} from './insurance/contract-overview/contract-overview.component';
import {VehicleComponent} from './vehicle/vehicle.component';
import {TripDetailComponent} from "./trip-detail/trip-detail.component";
import {NgModule} from "@angular/core";

export const routes: Routes = [
  // see index.html for start web page
  {path: '', component: HomeComponent},
  {path: 'dashboard', component: HomeComponent},
  {path: 'insurance', component: ContractOverviewComponent},
  {path: 'vehicle', component: VehicleComponent},
  {path: 'trips', component: TripComponent},
  { path: 'vehicle/:id', component: VehicleDetailsComponent},
  {path: 'trips/:id', component: TripDetailComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
