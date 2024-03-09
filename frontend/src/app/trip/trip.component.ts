import {Component, afterNextRender} from '@angular/core';
import {Trip} from '../model/trip.model';
import {Measurement} from '../model/measurement.model';
import {TripService} from '../service/trip.service';
import {CommonModule} from '@angular/common';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSelectModule} from '@angular/material/select';
import {FormControl, ReactiveFormsModule} from '@angular/forms';
import {Injectable} from '@angular/core';
import {GoogleMapsModule} from '@angular/google-maps'
import {FormsModule} from "@angular/forms";

@Injectable({
  providedIn: 'root',
})
@Component({
  selector: 'app-trip',
  standalone: true,
  imports: [MatFormFieldModule, ReactiveFormsModule, MatNativeDateModule, MatSelectModule, MatDatepickerModule, ReactiveFormsModule, FormsModule, CommonModule, GoogleMapsModule,],
  templateUrl: './trip.component.html',
  styleUrl: './trip.component.css',
  providers: [],

})
export class TripComponent {
  trips: Trip[] = [];
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
    zoom: 11,
    disableDefaultUI: true
  };
  datePickerStart: Date | null
  datePickerEnd: Date | null

  constructor(private tripService: TripService) {
    this.datePickerStart = null
    this.datePickerEnd = null
    this.getTrips();
    console.log("date")
  }

  getTrips(): void {
    this.distance = 0;
    this.duration = 0;
    this.tripService.getTrips(this.vehicleSelection, this.datePickerStart, this.datePickerEnd).subscribe((data) => {
      this.trips = data;
      console.log(data);
      this.trips.forEach((trip) => {
        this.tripService
          .getTotalDistanceForTrip(trip.id)
          .subscribe((distance) => {
            // Accumulate total distance for all trips
            this.distance += distance;

          });
        this.duration += new Date(trip.trip_end).getTime() - new Date(trip.trip_start).getTime();
        this.addTripPathToTrip(trip.id, this.getPointsForPath(trip.measurements))
        this.tripService.getAverageSpeedForTrip(trip.id).subscribe((speed) => {
          this.speeds.push(speed);
        });
      });
    });
    this.trips.sort((a, b) => new Date(a.trip_start).getTime() - new Date(b.trip_start).getTime());
  }

  getPointsForPath(measurements: Measurement[]): Object[] {
    const pathsArr = [];

    // Filter and map measurements to lat/lng objects
    for (const measurement of measurements) {
      if (measurement.measurementType === "LocationMeasurement" && !measurement.isError) {
        pathsArr.push({
          lat: measurement.latitude,
          lng: measurement.longitude,
          timestamp: new Date(measurement.timestamp)
        });
      }
    }

    // Sort pathsArr by timestamp
    const sortedPathArr = pathsArr.sort((a, b) => a.timestamp.getTime() - b.timestamp.getTime());

    return sortedPathArr;
  }

  addTripPathToTrip(tripId: number, tripPathArr: Object[]): void {
    const index = this.trips.findIndex(trip => trip.id === tripId);
    if (index !== -1) {
      this.trips[index].trip_path = tripPathArr;
    } else {
      console.error(`Trip with ID ${tripId} not found.`);
    }
  }

  convertDuration(val: number): string {
    const hours = Math.floor(val / (1000 * 60 * 60));
    const minutes = Math.floor((val % (1000 * 60 * 60)) / (1000 * 60));
    return hours + " Std. " + minutes + " Min.";
  }

  getAverageSpeed(arr: number[]): number {
    let total = 0;
    for (let speed of arr) {
      total += speed;
    }
    return Math.round(total / arr.length);
  }

  getAverage(val: number, divisor: number): number {

    return Math.round(val / divisor);
  }

  padTwoDigits(num: number): string {
    return num.toString().padStart(2, "0");
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
        console.log("start", this.datePickerStart)
        console.log("end", this.datePickerEnd)
        this.getTrips();
      }
    }
  }

  clearDate(): void {
    this.datePickerStart = null;
    this.datePickerEnd = null;
    this.getTrips();

  }

  onVehicleSelected(event: any) {
    this.vehicleSelection = event.value;
    this.getTrips();
  }

  dateInYyyyMmDdHhMmSs(date: Date, dateDiveder: string = "."): string {
    const dateFormat = new Date(date);
    return (
      [
        this.padTwoDigits(dateFormat.getDate()),
        this.padTwoDigits(dateFormat.getMonth() + 1),
        dateFormat.getFullYear(),
      ].join(dateDiveder) +
      " " +
      [
        this.padTwoDigits(dateFormat.getHours()),
        this.padTwoDigits(dateFormat.getMinutes()),
        this.padTwoDigits(dateFormat.getSeconds()),
      ].join(":")
    );
  }
}


// createChart(): void {
//   const labels = this.trips.map(trip => trip.id); // X-Achse
//   const data = this.trips.map(trip => trip.average_speed); // Y-Achse

//   new Chart("myChart", {
//     type: 'line',
//     data: {
//       labels: labels,
//       datasets: [{
//         label: 'Average Speed',
//         data: data,
//         fill: false,
//         borderColor: 'rgb(75, 192, 192)',
//         tension: 0.1
//       }]
//     },
//     options: {
//       scales: {
//         y: {
//           beginAtZero: true
//         }
//       }
//     }
//   });
// }

// getTrips(): void {
//   this.tripService.getTrips().subscribe((data) => {
//     this.trips = data;
//     this.dataSource = data;
//     console.log(data);
//     // console.table(data)
//     // this.createChart();
//   });
// }
