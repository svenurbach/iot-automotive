import { Component } from '@angular/core';
import { Insurance } from '../../model/insurance.model';
import { InsuranceService } from '../../service/insurance.service';
import {FormsModule} from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import {MatFormFieldModule} from '@angular/material/form-field';
import {ContractDetailsComponent} from '../contract-details/contract-details.component';
import {OsmViewComponent} from '../../osm-view/osm-view.component';

@Component({
  selector: 'app-contract-overview',
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    FormsModule,
    ContractDetailsComponent,
    OsmViewComponent
  ],
  templateUrl: './contract-overview.component.html',
  styleUrl: './contract-overview.component.css'
})
export class ContractOverviewComponent {

  contracts: Insurance[] = [];
  selectedContractId = "0";

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

  onContractSelected(event: any) {
    this.selectedContractId = event.value;
  }

}
