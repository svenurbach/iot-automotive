import { Component, Input, SimpleChanges } from '@angular/core'
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { FindmycarService } from '../service/findmycar.service';
import * as Leaflet from 'leaflet';

@Component({
  selector: 'app-osm-view',
  standalone: true,
  imports: [
    LeafletModule
  ],
  templateUrl: './osm-view.component.html',
  styleUrl: './osm-view.component.css'
})

export class OsmViewComponent {
  @Input() carId: number = 0;
  @Input() visibleMap: boolean = false;
  location: { lat: number, lon: number } = { lat: 0, lon: 0 };
  address: any;
  options: Leaflet.MapOptions = {};
  showMap = false;


  constructor(private findmycarService: FindmycarService) {}

  ngOnInit() {
    console.log("carId:", this.carId);
    this.getParkingLocation(this.carId);
  }

  getParkingLocation(carId: number): void {
    this.findmycarService.getParkingLocation(carId)
      .subscribe((location: any) => {
        this.location.lat = location[0];
        this.location.lon = location[1];
        this.getAddress(this.location.lat, this.location.lon);

        if (this.visibleMap) {
          this.options = {
            layers: getLayers(this.location.lat, this.location.lon),
            zoom: 17,
            attributionControl: false,
            center: new Leaflet.LatLng(this.location.lat, this.location.lon)
          };
          this.showMap = true;
        }
      });
  }

  getAddress (lat: number, lon: number ): void {
    this.findmycarService.getAddress(lat, lon)
      .subscribe((address: any) => {
        this.address = address;
        console.log("getAddress", address);
        console.log(this.address);
      });
  }
}

export const getLayers = ( lat: number, lon: number ): Leaflet.Layer[] => {
  return [
    new Leaflet.TileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: 'Strassenname'
    } as Leaflet.TileLayerOptions),
    ...getMarkers(lat, lon),
  ] as Leaflet.Layer[];
};


export const getMarkers = ( lat: number, lon: number ): Leaflet.CircleMarker[] => {
  return [
    new Leaflet.CircleMarker(new Leaflet.LatLng(lat, lon), {
      radius: 5,
      color: 'red',
      fillOpacity: 1,
    } as Leaflet.CircleMarkerOptions),
  ] as Leaflet.CircleMarker[];
};
