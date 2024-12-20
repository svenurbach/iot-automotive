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
import {AppComponent} from "../app.component";
import {RouterLink} from '@angular/router';
import {BehaviorSubject, filter, Observable} from "rxjs";
import {map, tap} from 'rxjs/operators';


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
  policyholderSelection: BehaviorSubject<number> = new BehaviorSubject<number>(0);
  tripPaths: Array<Array<Object>> = [];
  distance: number = 0;
  duration: number = 0;
  vehicleSelection: number = 0;
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


  constructor(private tripService: TripService, private vehicleService: VehicleService, private appComponent: AppComponent) {
    this.datePickerStart = null;
    this.datePickerEnd = null;
    this.policyholderSelection = this.appComponent._policyholderSelection;
    this.appComponent._policyholderSelection.subscribe((personId: number) => {
      this.getVehicles().subscribe(() => {
        this.getTrips();
      });
    });
  }

  getVehicles() {
    var id = 0;
    this.policyholderSelection.pipe(
      map(value => Number(value))
    ).subscribe((value: number) => {
      id = value;
    });
    if (id == 0) {
      return this.getAllVehicles()
    } else {
      return this.getVehiclesOfPerson(id)
    }
  }

  getAllVehicles(): Observable<any> {
    return this.vehicleService.getVehicles().pipe(
      tap(data => this.vehicles = data)
    );
  }

  getVehiclesOfPerson(id: number): Observable<any> {
    return this.vehicleService.getCarsByPerson(id).pipe(
      tap(data => this.vehicles = data)
    );
  }

  getTrips(): void {
    this.distance = 0;
    this.duration = 0;
    this.speeds = [];
    const vehicleIds: number[] = [];
    if (this.vehicleSelection == 0) {
      this.vehicles.forEach(function (vehicle) {
        vehicleIds.push(vehicle.id)
      });
    } else {
      vehicleIds.push(this.vehicleSelection)
    }
    this.tripService.getTrips(vehicleIds, this.datePickerStart, this.datePickerEnd).subscribe((data) => {
      if (data == null) {
        return;
      }
      this.trips = data;
      this.trips.forEach((trip) => {
        if (trip.distance) {
          this.distance += trip.distance;
        } else {
          this.tripService
            .getTotalDistanceForTrip(trip.id)
            .subscribe((distance) => {
              // Accumulate total distance for all trips
              if (distance > 0) {
                this.distance += distance;
              }
            });
        }
        let durationTemp: number = new Date(trip.trip_end).getTime() - new Date(trip.trip_start).getTime();
        if (durationTemp > 0) {
          this.duration += durationTemp
        }
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
