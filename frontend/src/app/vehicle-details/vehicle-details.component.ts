import {Component} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {VehicleService} from "../service/vehicle.service";
import {Vehicle} from "../model/vehicle.model";
import {Insurance} from "../model/insurance.model";
import {TripService} from "../service/trip.service";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import {AsyncPipe} from "@angular/common";
import {Measurement} from "../model/measurement.model";
import Chart from 'chart.js/auto';
import {OsmViewComponent} from "../osm-view/osm-view.component";

@Component({
  selector: 'app-vehicle-details',
  standalone: true,
    imports: [
        AsyncPipe,
        OsmViewComponent
    ],
  templateUrl: './vehicle-details.component.html',
  styleUrl: './vehicle-details.component.css'
})
export class VehicleDetailsComponent {
  public chart: any;
  vehicle!: Vehicle;
  contract: Insurance = {} as Insurance;
  sumTrips$: Observable<number> = new Observable<number>();
  measurementErrors: Measurement[] = [];
  measurementTypes: string[] = [];
  countMap: { [key: string]: number } = {};

  constructor(private route: ActivatedRoute, private vehicleService: VehicleService, private tripService: TripService) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      const id = params['id'];
      this.vehicleService.getVehicle(id).subscribe(data => {
        this.vehicle = data;
        this.sumTrips$ = this.getTrips(id);
        this.getAllMeasurementErrors(id);
      });
    });
  }

  getTrips(id: number): Observable<number> {
    const idArray: number[] = [id];
    return this.tripService.getTrips(idArray, null, null).pipe(
      map(data => data.length)
    );
  }

  getAllMeasurementErrors(id: number): void {
    this.vehicleService.getAllMeasurementErrors(id)
      .subscribe((data: any[]) => {
        this.measurementErrors = data;
        const jsonData = JSON.stringify(this.measurementErrors);
        const parsedData = JSON.parse(jsonData);
        this.measurementTypes = parsedData.map((item: any) => item.measurementType);
        this.countMap = this.getSumOfMeasurementErrorOfType(this.measurementTypes);
        this.createPieChart();
      });
  }

  getSumOfMeasurementErrorOfType(measurementTypes: string[]): Record<string, number> {
    measurementTypes.forEach(type => {
      if (this.countMap[type]) {
        this.countMap[type]++;
      } else {
        this.countMap[type] = 1;
      }
    });
    return this.countMap;
  }

  getMeasurementTypeFromMap(countMap: { [key: string]: number }): String[] {
    const keys = Object.keys(countMap);
    const typeArray: string[] = [];
    keys.forEach(key => {
      typeArray.push(key);
    });
    return typeArray;
  }

  getSumOfMeasurementErrorsByType(countMap: { [key: string]: number }): number[] {
    const sums: number[] = [];
    Object.values(countMap).forEach(value => {
      sums.push(value);
    });
    return sums;
  }

  // Kreisdiagramm für Messfehler pro Fahrzeug
  createPieChart() {
    this.chart = new Chart("pieChartErrors", {
      type: 'pie',
      data: {
        labels: this.getMeasurementTypeFromMap(this.countMap),
        datasets: [
          {
            label: "Errors per vehicle",
            data: this.getSumOfMeasurementErrorsByType(this.countMap),
            backgroundColor: [
              "#b91d47",
              "#00aba9",
              "#2b5797",
              "#e8c3b9",
              "#1e7145"
            ]
          },
        ]
      },
      options: {
        aspectRatio: 2.5
      }
    });
  }
}
