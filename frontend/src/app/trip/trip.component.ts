import { Component, afterNextRender } from '@angular/core';
import { Trip } from '../model/trip.model';
import { TripService } from '../service/trip.service';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { Chart } from 'chart.js/auto';
import { ChartData } from 'chart.js';

@Component({
  selector: 'app-trip',
  standalone: true,
  imports: [CommonModule, MatTableModule],
  templateUrl: './trip.component.html',
  styleUrl: './trip.component.css'
})
export class TripComponent {

  trips: Trip[] = [];
  displayedColumns: string[] = ['id', 'trip_start', 'trip_end', 'average_speed'];
  dataSource: any = []

  constructor(private tripService: TripService) {
    this.getTrips();
    // afterNextRender(() => {
    //   this.createChart();
    // });
  }

  createChart(): void {
    const labels = this.trips.map(trip => trip.id); // X-Achse
    const data = this.trips.map(trip => trip.average_speed); // Y-Achse

    new Chart("myChart", {
      type: 'line',
      data: {
        labels: labels,
        datasets: [{
          label: 'Average Speed',
          data: data,
          fill: false,
          borderColor: 'rgb(75, 192, 192)',
          tension: 0.1
        }]
      },
      options: {
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
  }

  getTrips(): void {
    this.tripService.getTrips()
      .subscribe((data) => {
        this.trips = data;
        this.dataSource = data;
        console.log(data)
        // console.table(data)
        this.createChart();
      });
  }

}
