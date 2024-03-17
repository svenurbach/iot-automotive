import {Component} from '@angular/core';
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
  policyholderSelection: number = 0;
  policyholders: number[] = [1, 2, 3, 4,]


  onPolicyholderSelected(event: any) {
    this.policyholderSelection = event.value;
  }
}
