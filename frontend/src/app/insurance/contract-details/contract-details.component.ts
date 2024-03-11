import { Component, Input } from '@angular/core'
import { SimpleChanges } from '@angular/core';
import { Insurance } from '../../model/insurance.model';
import { InsuranceService } from '../../service/insurance.service';

@Component({
  selector: 'app-contract-details',
  standalone: true,
  imports: [],
  templateUrl: './contract-details.component.html',
  styleUrl: './contract-details.component.css'
})
export class ContractDetailsComponent {

  @Input() selectedContractId!: string;
  contract: Insurance = {} as Insurance;

  constructor(private insuranceService: InsuranceService) {}

  ngOnChanges(changes: SimpleChanges): void {
    this.getContract(Number(this.selectedContractId));
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
