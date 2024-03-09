import { Routes } from '@angular/router';
import { TripComponent } from './trip/trip.component';
import { HomeComponent } from './home/home.component';
import { InsuranceComponent } from './insurance/insurance.component';
import { VehicleComponent } from './vehicle/vehicle.component';

export const routes: Routes = [
  // see index.html for start web page
  { path: '', component: HomeComponent },
  { path: 'dashboard', component: HomeComponent },
  { path: 'insurance', component: InsuranceComponent },
  { path: 'vehicle', component: VehicleComponent },
  { path: 'trips', component: TripComponent },
];
