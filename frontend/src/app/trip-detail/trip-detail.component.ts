import {Component} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ActivatedRoute} from '@angular/router';
import * as utils from "../../utils";
import {Measurement} from "../model/measurement.model";
import {TripService} from "../service/trip.service";
import {Trip} from "../model/trip.model";
import {GoogleMapsModule} from '@angular/google-maps'
import {Vehicle} from "../model/vehicle.model";


@Component({
  selector: 'app-trip-detail',
  standalone: true,
  imports: [GoogleMapsModule, CommonModule],
  templateUrl: './trip-detail.component.html',
  styleUrl: './trip-detail.component.css'
})
export class TripDetailComponent {
  tripId: string | null;
  trip: Trip = new class implements Trip {
    average_speed: number = 0;
    distance: number = 0;
    end_latitude: number = 0;
    end_longitude: number = 0;
    id: number = 0;
    measurements: Measurement[] = [];
    start_latitude: number = 0;
    start_longitude: number = 0;
    state: string = "";
    trip_end: Date = new Date(0);
    trip_path: Object[] = [];
    trip_start: Date = new Date(0);
  };
  vehicleId: number = 0;
  vehicleModelName: string = "";
  vehicleModelManufacturer: string = "";
  tripPath: Array<Object> = [];
  startAddress: any = {};
  endAddress: any = {};
  distance: number = 0;
  duration: number = 0;
  durationString: string = "";
  average_speed: number = 0;
  mapOptions: google.maps.MapOptions = {
    zoom: 11,
    disableDefaultUI: true,
  };


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

  constructor(private tripService: TripService, private route: ActivatedRoute) {
    window.scrollTo(0, 0)
    this.tripId = this.route.snapshot.paramMap.get('id');
    this.getTrip();
  }

  getTrip(): void {
    this.distance = 0;
    this.duration = 0;
    this.average_speed = 0;
    this.tripService.getTrip(Number(this.tripId)).subscribe((data) => {
      this.trip = data;
      if (data.distance && data.distance > 0) {
        this.distance = data.distance;
      } else {
        this.tripService
          .getTotalDistanceForTrip(data.id)
          .subscribe((distance) => {
            this.distance = distance;
          })
      }

      let durationTemp: number = new Date(data.trip_end).getTime() - new Date(data.trip_start).getTime();
      if (durationTemp > 0) {
        this.duration = durationTemp
      }

      this.tripPath = this.getPointsForPath(data.measurements);
      if (data.average_speed) {
        this.average_speed = data.average_speed;
      } else {
        this.tripService.getAverageSpeedForTrip(data.id).subscribe((speed) => {
          this.average_speed = speed;
        });
      }
      this.tripService.getAddressFromCoordinates(data.start_latitude, data.start_longitude)
        .subscribe((addressData: any) => {
          this.startAddress = addressData.results[0];
        });
      if (data.end_longitude) {
        this.tripService.getAddressFromCoordinates(data.end_latitude, data.end_longitude)
          .subscribe((addressData: any) => {
            this.endAddress = addressData.results[0];
          });
      }
      this.getVehicle();

    });
  }

  getVehicle() {
    this.tripService.getVehicle(this.trip.id).subscribe((data) => {
      this.vehicleId = data.id;
      this.vehicleModelName = data.vehicleModel.modelName;
      this.vehicleModelManufacturer = data.vehicleModel.manufacturer;
    })
  }

  getPolyLineOptions(): Object {
    const polylineOptions = {
      path: this.tripPath,
      strokeColor: '#32a1d0',
      strokeOpacity: 1.0,
      strokeWeight: 5,
    };
    return polylineOptions;
  }

  getAddress(point: string): string {
    var address = ""
    if (point == "start") {
      address = this.startAddress.formatted_address
    }
    if (point == "end") {
      address = this.endAddress.formatted_address
    }
    return address;
  }
}
