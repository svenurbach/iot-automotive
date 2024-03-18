import { Component, Input } from '@angular/core'
import { Insurance } from '../model/insurance.model';
import { Vehicle } from '../model/vehicle.model';
import { InsuranceService } from '../service/insurance.service';
import { ActivatedRoute } from '@angular/router';
import { DatePipe } from '@angular/common';
import { VehicleService } from '../service/vehicle.service';

@Component({
  selector: 'app-contract-details',
  standalone: true,
  imports: [DatePipe],
  templateUrl: './contract-details.component.html',
  styleUrl: './contract-details.component.css'
})
export class ContractDetailsComponent {

  contract: Insurance = {} as Insurance;
  vehicle: Vehicle = {} as Vehicle;
  contractIsLoaded=false;
  vehicleIsLoaded=false;

  constructor(private route: ActivatedRoute, private insuranceService: InsuranceService, private vehicleService: VehicleService) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      const id = params['id'];
      this.getContract(id);
      this.getVehicle(id);
    });
  }

  getContract(id: number): void {
    this.insuranceService.getInsurance(id)
      .subscribe((data) => {
        this.contract = data;
        console.log(data)
        this.contractIsLoaded=true;
      });
  }

  getVehicle(id: number): void {
    this.vehicleService.getVehicleByContract(id)
      .subscribe((data) => {
        this.vehicle = data;
        console.log(data)
        this.vehicleIsLoaded=true;
      });
  }
}
