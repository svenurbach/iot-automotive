import { Component, OnInit } from '@angular/core';
import { Trip } from '../model/trip';
import { TripService } from '../service/trip.service';
import { CommonModule } from '@angular/common';
import {NgFor, NgForOf} from "@angular/common";
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-trip',
  standalone: true,
  imports: [CommonModule, NgFor],
  templateUrl: './trip.component.html',
  styleUrl: './trip.component.css'
})
export class TripComponent {

  trips: Trip[] = [];

  constructor(private tripService: TripService) { }

  ngOnInit(): void {
    this.getTrips();
  }

  getTrips(): void {
    this.tripService.getTrips()
    .subscribe(trips => this.trips = trips);
  }

}
