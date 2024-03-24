import { Component } from '@angular/core';
import { Insurance } from '../model/insurance.model';
import { InsuranceService } from '../service/insurance.service';
import {ContractDetailsComponent} from '../contract-details/contract-details.component';
import {RouterLink} from "@angular/router";
import {AppComponent} from "../app.component";
import {BehaviorSubject, filter, Observable} from "rxjs";

@Component({
  selector: 'app-contract-overview',
  standalone: true,
  imports: [
    ContractDetailsComponent,
    RouterLink
  ],
  templateUrl: './contract-overview.component.html',
  styleUrl: './contract-overview.component.css'
})
export class ContractOverviewComponent {
  contracts: Insurance[] = [];
  currentItem = 1;
  policyholderSelection: BehaviorSubject<number> = new BehaviorSubject<number>(0);

  constructor(private insuranceService: InsuranceService, private appComponent: AppComponent) {
    this.policyholderSelection = this.appComponent._policyholderSelection;
    this.appComponent._policyholderSelection.subscribe((personId: number) => {
      if (personId == 0) { 
        console.log("PersonId is 0")
        this.getContracts();
      } else {
        this.getInsurancesByPerson(personId);
      }
    });
  }

  getInsurancesByPerson(personId: number): void {
    this.insuranceService.getInsurancesByPerson(personId)
      .subscribe((data) => {
        this.contracts = data;
        console.log(data)
        console.log(this.contracts)
      });
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
