import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {VehicleService} from "../service/vehicle.service";
import {Vehicle} from "../model/vehicle.model";

@Component({
  selector: 'app-vehicle-details',
  standalone: true,
  imports: [],
  templateUrl: './vehicle-details.component.html',
  styleUrl: './vehicle-details.component.css'
})
export class VehicleDetailsComponent {
  vehicle!: Vehicle;

  constructor(private route: ActivatedRoute, private vehicleService: VehicleService) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      const id = params['id'];
      this.vehicleService.getVehicleById(id).subscribe(data => {
        this.vehicle = data; // Annahme: getVehicleById gibt ein Observable zurück, das die Fahrzeugdaten emittiert
      });
    });
  }

}
