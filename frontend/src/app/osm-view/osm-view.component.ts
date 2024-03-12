import { Component, Input } from '@angular/core'
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import * as Leaflet from 'leaflet';

@Component({
  selector: 'app-osm-view',
  standalone: true,
  imports: [
    LeafletModule,
  ],
  templateUrl: './osm-view.component.html',
  styleUrl: './osm-view.component.css'
})
export class OsmViewComponent {
//   @Input() location!: number[];


  options: Leaflet.MapOptions = {
    layers: getLayers(),
    zoom: 17,
    attributionControl: false,
    center: new Leaflet.LatLng(52.5104208, 13.4699791)
  };
}

export const getLayers = (): Leaflet.Layer[] => {
  return [
    new Leaflet.TileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: 'Strassenname'
    } as Leaflet.TileLayerOptions),
    ...getMarkers(),
  ] as Leaflet.Layer[];
};

export const getMarkers = (): Leaflet.CircleMarker[] => {
  return [
    new Leaflet.CircleMarker(new Leaflet.LatLng(52.5104208, 13.4699791), {
      radius: 5,
      color: 'red',
      fillOpacity: 1,
    } as Leaflet.CircleMarkerOptions),
  ] as Leaflet.CircleMarker[];
};
