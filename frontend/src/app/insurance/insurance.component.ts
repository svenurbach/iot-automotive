import { Component } from '@angular/core';
import { Insurance } from '../model/insurance.model';
import { InsuranceService } from '../service/insurance.service';

@Component({
  selector: 'app-insurance',
  standalone: true,
  imports: [],
  templateUrl: './insurance.component.html',
  styleUrl: './insurance.component.css'
})
export class InsuranceComponent {

  contracts: Insurance[] = [];

  constructor(private insuranceService: InsuranceService) {
    this.getContracts();
  }

  getContracts(): void {
    this.insuranceService.getInsurances()
      .subscribe((data) => {
        this.contracts = data;
        console.log(data)
        console.log(this.contracts)
      });
  }

}
