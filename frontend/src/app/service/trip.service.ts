import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {catchError, map, tap} from 'rxjs/operators';
import {Trip} from '../model/trip.model';
import {FormControl} from "@angular/forms";
import {Measurement} from "../model/measurement.model";
import {Vehicle} from "../model/vehicle.model";

@Injectable({
  providedIn: 'root',
})
export class TripService {
  private url = 'http://localhost:8080/api/trips'; // URL to web api

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'}),
  };

  constructor(private http: HttpClient) {
  }

  // getTrips(vehicleSelection: number, datePickerStart: Date | null, datePickerEnd: Date | null): Observable<Trip[]> {
  //   var queryString = `/findAll/${vehicleSelection}`;
  //   if (datePickerStart && datePickerEnd) {
  //     queryString += `?startTime=${datePickerStart.toISOString()}&endTime=${datePickerEnd.toISOString()}`
  //   }
  //   console.log("dateParams", queryString)
  //   return this.http.get<Trip[]>(this.url + queryString).pipe(
  //     tap((_) => this.log('fetched trips')),
  //     catchError(this.handleError<Trip[]>('getTrips', []))
  //   );
  // }

  getTrips(vehicleIds: number[], datePickerStart: Date | null, datePickerEnd: Date | null): Observable<Trip[]> {
    var queryString = `/findAllByVehicleIds?`;
    vehicleIds.forEach(function (vehicleId: number) {
      queryString += `vehicleIds=${vehicleId}&`
    })
    if (datePickerStart && datePickerEnd) {
      queryString += `startTime=${datePickerStart.toISOString()}&endTime=${datePickerEnd.toISOString()}`
    }
    return this.http.get<Trip[]>(this.url + queryString).pipe(
      tap((_) => this.log('fetched trips')),
      catchError(this.handleError<Trip[]>('getTrips', []))
    );
  }

  getTrip(id: number): Observable<Trip> {
    const url = `${this.url}/${id}`;
    return this.http.get<Trip>(url).pipe(
      tap((_) => this.log(`fetched trip id=${id}`)),
      catchError(this.handleError<Trip>(`getTrip id=${id}`))
    );
  }

  getVehicle(tripId: number): Observable<Vehicle> {
    return this.http.get<Vehicle>(`${this.url}/findVehiclebyTripId/${tripId}`)
  }

  getTotalDistanceForTrip(id: number): Observable<number> {
    return this.http.get<number>(`${this.url}/${id}/total-distance`);
  }

  getAverageSpeedForTrip(id: number): Observable<number> {
    return this.http.get<number>(`${this.url}/${id}/average-speed`);
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  private log(message: string) {
    // this.messageService.add(`TripService: ${message}`);
    console.log(`TripService: ${message}`);
  }

  getAddressFromCoordinates(latitude: number, longitude: number): Observable<Object> {
    const google_api_key = "AIzaSyCU4bQpfJ7gExmklVSYXjCo6rv0Kxq7oV8";
    https://maps.googleapis.com/maps/api/geocode/json?latlng=52.52389395505191,13.337066155575759&key=AIzaSyCU4bQpfJ7gExmklVSYXjCo6rv0Kxq7oV8
      return this.http.get<Object>(`https://maps.googleapis.com/maps/api/geocode/json?latlng=${latitude},${longitude}&key=${google_api_key}`).pipe(
        tap((_) => console.log(`fetched address`)),
        catchError(this.handleError<Object>(`fetching address`))
      );
    ;
  }
}
