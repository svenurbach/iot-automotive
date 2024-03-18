import { Component } from '@angular/core';
import {Vehicle} from "../model/vehicle.model";
import {VehicleService} from "../service/vehicle.service";
import {RouterLink} from "@angular/router";
import {OsmViewComponent} from "../osm-view/osm-view.component";
import {AppComponent} from "../app.component";
import {BehaviorSubject, filter, Observable} from "rxjs";


@Component({
  selector: 'app-vehicle',
  standalone: true,
  imports: [RouterLink, OsmViewComponent],
  templateUrl: './vehicle.component.html',
  styleUrl: './vehicle.component.css'
})
export class VehicleComponent {
  vehicles: Vehicle[] = [];
  policyholderSelection: BehaviorSubject<number> = new BehaviorSubject<number>(0);

  ngOnInit(): void {
    // this.getAllMeasurementErrors();
  }

  constructor(private vehicleService: VehicleService, private appComponent: AppComponent) {
    this.policyholderSelection = this.appComponent._policyholderSelection;
    this.appComponent._policyholderSelection.subscribe((personId: number) => {
      if (personId == 0) { 
        this.getVehicles();
      } else {
        this.getCarsByPerson(personId);
      }
    });
  }

  getVehicles(): void {
    this.vehicleService.getVehicles()
      .subscribe((data) => {
        this.vehicles = data;
        console.log(data)
        console.log(this.vehicles)
      });
  }

  getCarsByPerson(personId: number): void {
    this.vehicleService.getCarsByPerson(personId)
      .subscribe((data) => {
        this.vehicles = data;
        console.log(data)
        console.log(this.vehicles)
      });
  }



}
