import {Component, afterNextRender} from '@angular/core';
import {Trip} from '../model/trip.model';
import {TripService} from '../service/trip.service';
import {CommonModule} from '@angular/common';
import {MatTableModule} from '@angular/material/table';
import {Chart} from 'chart.js/auto';
import {ChartData} from 'chart.js';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
@Component({
  selector: 'app-trip',
  standalone: true,
  imports: [CommonModule, MatTableModule],
  templateUrl: './trip.component.html',
  styleUrl: './trip.component.css',
})
export class TripComponent {
  trips: Trip[] = [];
  distance: number = 0;
  duration: number = 0;
  durationString: string = "";
  speeds: number[] = [];
  displayedColumns: string[] = [
    'id',
    'trip_start',
    'trip_end',
    'average_speed',
  ];
  dataSource: any = [];

  constructor(private tripService: TripService) {
    this.getTrips();
    // console.log('distance', this.getDistance());
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
  getTrips(): void {
    this.tripService.getTrips().subscribe((data) => {
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
        this.tripService.getAverageSpeedForTrip(trip.id).subscribe((speed) => {
          this.speeds.push(speed);
        });
      });
    });
  }

  convertDuration(val: number): string {
    const hours = Math.floor(val / (1000 * 60 * 60));
    const minutes = Math.floor((val % (1000 * 60 * 60)) / (1000 * 60));
    return "" + hours + ":" + minutes + "";
  }

  getAverageSpeed(): number {
    let total = 0;
    for (let speed of this.speeds) {
      total += speed;
    }
    return Math.round(total / this.speeds.length);
  }
}
