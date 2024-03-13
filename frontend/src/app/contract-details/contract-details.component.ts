import { Component, Input } from '@angular/core'
import { SimpleChanges } from '@angular/core';
import { Insurance } from '../model/insurance.model';
import { InsuranceService } from '../service/insurance.service';
import {ActivatedRoute} from '@angular/router';
import { DatePipe } from '@angular/common';


@Component({
  selector: 'app-contract-details',
  standalone: true,
  imports: [DatePipe],
  templateUrl: './contract-details.component.html',
  styleUrl: './contract-details.component.css'
})
export class ContractDetailsComponent {

  contract: Insurance = {} as Insurance;

  constructor(private route: ActivatedRoute, private insuranceService: InsuranceService) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      const id = params['id'];
      this.getContract(id);
    });
  }

  getContract(id: number): void {
    this.insuranceService.getInsurance(id)
      .subscribe((data) => {
        this.contract = data;
        console.log(data)
        console.log(this.contract)
      });
  }
}
