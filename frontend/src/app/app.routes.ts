import { Routes } from '@angular/router';
import { TripComponent } from './trip/trip.component';
import { HomeComponent } from './home/home.component';
import { AppComponent } from './app.component';

export const routes: Routes = [
    // see index.html for start web page

    { path: 'home-component', component: HomeComponent },
    { path: 'trip-component', component: TripComponent }

];
