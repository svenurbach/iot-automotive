import {Component} from '@angular/core';
import {Trip} from '../model/trip.model';
import {Measurement} from '../model/measurement.model';
import {Vehicle} from "../model/vehicle.model";
import {TripService} from '../service/trip.service';
import {VehicleService} from "../service/vehicle.service";
import {CommonModule} from '@angular/common';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSelectModule} from '@angular/material/select';
import {FormControl, ReactiveFormsModule} from '@angular/forms';
import {Injectable} from '@angular/core';
import {GoogleMapsModule} from '@angular/google-maps'
import {FormsModule} from "@angular/forms";
import * as utils from "../../utils"
import {dateInYyyyMmDdHhMmSs, getAverageSpeed} from "../../utils";
import _default from "chart.js/dist/plugins/plugin.tooltip";
import numbers = _default.defaults.animations.numbers;

import {RouterLink} from '@angular/router';
import {filter} from "rxjs";
@Injectable({
  providedIn: 'root',
})
@Component({
  selector: 'app-trip',
  standalone: true,
  imports: [MatFormFieldModule, ReactiveFormsModule, MatNativeDateModule, MatSelectModule, MatDatepickerModule, ReactiveFormsModule, FormsModule, CommonModule, GoogleMapsModule, RouterLink],
  templateUrl: './trip.component.html',
  styleUrl: './trip.component.css',
  providers: [],
})

export class TripComponent {
  trips: Trip[] = [];
  vehicles: Vehicle[] = [];
  tripPaths: Array<Array<Object>> = [];
  distance: number = 0;
  duration: number = 0;
  vehicleSelection: number = 1;
  durationString: string = "";
  speeds: number[] = [];
  displayedColumns: string[] = [
    'id',
    'trip_start',
    'trip_end',
    'average_speed',
  ];
  dataSource: any = [];
  mapOptions: google.maps.MapOptions = {
    zoom: 12,
    disableDefaultUI: true,
    gestureHandling: "none",
    clickableIcons: false
  };
  datePickerStart: Date | null
  datePickerEnd: Date | null

  dateInYyyyMmDdHhMmSs(date: Date, dateDiveder: string = "."): string {
    return utils.dateInYyyyMmDdHhMmSs(date, ".");
  }

  getAverageSpeed(arr: number[]): number {
    return utils.getAverageSpeed(arr);
  }

  getAverage(val: number, divisor: number): number {
    return utils.getAverage(val, divisor);
  }

  convertDuration(val: number): string {
    return utils.convertDuration(val)
  }

  getPointsForPath(measurements: Measurement[]): Object[] {
    return utils.getPointsForPath(measurements);
  }


  constructor(private tripService: TripService, private vehicleService: VehicleService) {
    this.datePickerStart = null
    this.datePickerEnd = null
    this.getVehicles();
    this.getTrips();
  }

  getVehicles(): void {
    this.vehicleService.getVehicles()
      .subscribe((data) => {
        this.vehicles = data;
      });
  }

  getTrips(): void {
    this.distance = 0;
    this.duration = 0;
    this.speeds = [];
    this.tripService.getTrips(this.vehicleSelection, this.datePickerStart, this.datePickerEnd).subscribe((data) => {
      this.trips = data;
      console.log(data);
      this.trips.forEach((trip) => {
        if (trip.distance) {
          this.distance += trip.distance;
        } else {
          this.tripService
            .getTotalDistanceForTrip(trip.id)
            .subscribe((distance) => {
              // Accumulate total distance for all trips
              this.distance += distance;

            });
        }
        this.duration += new Date(trip.trip_end).getTime() - new Date(trip.trip_start).getTime();
        this.addTripPathToTrip(trip.id, this.getPointsForPath(trip.measurements))
        if (trip.average_speed) {
          this.speeds.push(trip.average_speed);
        } else {
          this.tripService.getAverageSpeedForTrip(trip.id).subscribe((speed) => {
            this.speeds.push(speed);
          });
        }
      });
    });
    this.trips.sort((a, b) => new Date(a.trip_start).getTime() - new Date(b.trip_start).getTime());
  }

  addTripPathToTrip(tripId: number, tripPathArr: Object[]): void {
    const index = this.trips.findIndex(trip => trip.id === tripId);
    if (index !== -1) {
      this.trips[index].trip_path = tripPathArr;
    } else {
      console.error(`Trip with ID ${tripId} not found.`);
    }
  }

  getPolyLineOptions(trip: Trip): Object {
    const polylineOptions = {
      path: trip.trip_path,
      strokeColor: '#32a1d0',
      strokeOpacity: 1.0,
      strokeWeight: 5,
    };
    return polylineOptions;
  }

  changeDate(type: any, event: any): void {
    if (event.value) {
      if (type == "start") {
        this.datePickerStart = new Date(event.value)
      }
      if (type == "end") {
        this.datePickerEnd = new Date(event.value)
        this.getTrips();
        document.getElementsByClassName('clear-button')[0].classList.remove("hidden")
      }
    }
  }

  clearDate(): void {
    this.datePickerStart = null;
    this.datePickerEnd = null;
    this.getTrips();
    document.getElementsByClassName('clear-button')[0].classList.add("hidden")
  }

  onVehicleSelected(event: any) {
    this.vehicleSelection = event.value;
    this.getTrips();
  }
}
