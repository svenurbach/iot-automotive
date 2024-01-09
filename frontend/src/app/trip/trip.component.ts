import { Component, OnInit } from '@angular/core';
import { Trip } from '../model/trip.model';
import { TripService } from '../service/trip.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-trip',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './trip.component.html',
  styleUrl: './trip.component.css'
})
export class TripComponent {

  trips: Trip[] = [];

  constructor(private tripService: TripService) {
    this.getTrips();
  }

  getTrips(): void {
    this.tripService.getTrips()
    .subscribe(trips => this.trips = trips);
  }

}
