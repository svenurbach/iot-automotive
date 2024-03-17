import {Measurement} from "./measurement.model";
import {Vehicle} from "./vehicle.model";

export interface Trip {
  id: number;
  trip_start: Date;
  trip_end: Date;
  average_speed: number;
  start_latitude: number;
  end_latitude: number;
  start_longitude: number;
  end_longitude: number;
  distance: number;
  state: string;
  measurements: Measurement[];
  trip_path: Object[];
}
