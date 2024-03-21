import {VehicleDetailsComponent} from "./vehicle-details/vehicle-details.component";
import {RouterModule, Routes} from '@angular/router';
import {TripComponent} from './trip/trip.component';
import {ContractDetailsComponent} from './contract-details/contract-details.component';
import {ContractOverviewComponent} from './contract-overview/contract-overview.component';
import {VehicleComponent} from './vehicle/vehicle.component';
import {TripDetailComponent} from "./trip-detail/trip-detail.component";
import {NgModule} from "@angular/core";

export const routes: Routes = [
  {path: '', component: VehicleComponent},
  {path: 'insurance', component: ContractOverviewComponent},
  {path: 'trips', component: TripComponent},
  {path: 'vehicle/:id', component: VehicleDetailsComponent},
  {path: 'trips/:id', component: TripDetailComponent},
  {path: 'contract/:id', component: ContractDetailsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
