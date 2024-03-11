import { Component } from '@angular/core';
import {VehicleModel} from "../model/vehicle-model.model";
import {Vehicle} from "../model/vehicle.model";
import {InsuranceService} from "../service/insurance.service";
import {VehicleService} from "../service/vehicle.service";

@Component({
  selector: 'app-vehicle',
  standalone: true,
  imports: [],
  templateUrl: './vehicle.component.html',
  styleUrl: './vehicle.component.css'
})
export class VehicleComponent {

  vehicles: Vehicle[] = [];

  constructor(private vehicleService: VehicleService) {
    this.getVehicles();
  }

  getVehicles(): void {
    this.vehicleService.getVehicles()
      .subscribe((data) => {
        this.vehicles = data;
        console.log(data)
        console.log(this.vehicles)
      });
  }
}
