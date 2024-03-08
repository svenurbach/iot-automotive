import { Routes } from '@angular/router';
import { TripComponent } from './trip/trip.component';
import { HomeComponent } from './home/home.component';
import { ContractOverviewComponent } from './insurance/contract-overview/contract-overview.component';
import { VehicleComponent } from './vehicle/vehicle.component';

export const routes: Routes = [
    // see index.html for start web page
    { path: '', component: HomeComponent },
    { path: 'dashboard', component: HomeComponent },
    { path: 'insurance', component: ContractOverviewComponent },
    { path: 'vehicle', component: VehicleComponent },
    { path: 'trip', component: TripComponent }

];
