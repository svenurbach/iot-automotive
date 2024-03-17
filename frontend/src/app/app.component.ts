import {Component, Input} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSelectModule} from '@angular/material/select';
import {FormsModule} from "@angular/forms";
import {RouterOutlet, RouterLink, RouterLinkActive, RouterModule} from '@angular/router';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatSidenavModule} from '@angular/material/sidenav';
import {LeafletModule} from '@asymmetrik/ngx-leaflet';
import {InsuranceService} from "./service/insurance.service";
import {TripService} from "./service/trip.service";
import {VehicleService} from "./service/vehicle.service";
import {Insurance} from "./model/insurance.model";
import {BehaviorSubject, Observable} from "rxjs";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    RouterLink,
    RouterLinkActive,
    MatSidenavModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    RouterModule,
    LeafletModule,
    MatFormFieldModule, MatSelectModule, FormsModule
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  title = 'IoT Automotive';
  subtitle = 'Frontend';
  showMenu = true;
  _policyholderSelection: BehaviorSubject<number> = new BehaviorSubject<number>(0);
  policyholders: any[] = [{id: 1, name: "hallo"}, {id: 2, name: "hallo"}]

  constructor(private insuranceService: InsuranceService) {
    this.populatePolicyholderSelect()
  }

  @Input()
  set policyholderSelection(value: number) {
    this._policyholderSelection.next(value);
    console.log(value)
  }

  get policyholderSelection(): BehaviorSubject<number> {
    return this._policyholderSelection;
  }

  populatePolicyholderSelect() {
    this.policyholders = []
    this.insuranceService.getInsurances().subscribe(data => {
      data.forEach((insurance) => {
        var policyholder = {id: insurance.policyholder.id, name: insurance.policyholder.name}
        let alreadyInArray = false;
        if (this.policyholders.length > 0) {
          this.policyholders.forEach(holder => {
            if (holder.id == policyholder.id) {
              alreadyInArray = true;
            }
          });
        }
        if (!alreadyInArray) {
          this.policyholders.push(policyholder)
        }
      })
    })
  }
}

