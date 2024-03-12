import { Component } from '@angular/core';
import {Vehicle} from "../model/vehicle.model";
import {VehicleService} from "../service/vehicle.service";
import {RouterLink} from "@angular/router";


@Component({
  selector: 'app-vehicle',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './vehicle.component.html',
  styleUrl: './vehicle.component.css'
})
export class VehicleComponent {

  vehicles: Vehicle[] = [];


  ngOnInit(): void {
    // this.getAllMeasurementErrors();
  }

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
