import { Component } from '@angular/core';
import { Insurance } from '../model/insurance.model';
import { InsuranceService } from '../service/insurance.service';
import {ContractDetailsComponent} from '../contract-details/contract-details.component';
import {OsmViewComponent} from '../osm-view/osm-view.component';
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-contract-overview',
  standalone: true,
  imports: [
    ContractDetailsComponent,
    OsmViewComponent,
    RouterLink
  ],
  templateUrl: './contract-overview.component.html',
  styleUrl: './contract-overview.component.css'
})
export class ContractOverviewComponent {
  contracts: Insurance[] = [];
  currentItem = 1;

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
