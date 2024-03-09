export interface Measurement {
  id: number;
  measurementType: string;
  isError: boolean;
  latitude: number;
  longitude: number;
  timestamp: Date;
}
