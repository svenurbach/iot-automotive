import { Routes } from '@angular/router';
import { TripComponent } from './trip/trip.component';
import { HomeComponent } from './home/home.component';

export const routes: Routes = [
    // see index.html for start web page
    { path: '', component: HomeComponent },
    { path: 'home', component: HomeComponent },
    { path: 'trip', component: TripComponent }

];
