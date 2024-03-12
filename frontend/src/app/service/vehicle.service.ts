import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { Vehicle } from '../model/vehicle.model';
import { Measurement } from '../model/measurement.model';

@Injectable({
  providedIn: 'root'
})
export class VehicleService {

    private url = 'http://localhost:8080/api/vehicle';  // URL to web api

    httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };

    constructor(private http: HttpClient) { }

  getVehicles(): Observable<Vehicle[]> {
    return this.http.get<Vehicle[]>(this.url + '/findAll')
      .pipe(
        tap(_ => console.log('fetched Vehicle')),
        catchError(this.handleError<Vehicle[]>('getVehicles', []))
      );
  }

  getVehicle(id: number): Observable<Vehicle>{
    const url = `${this.url}/${id}`;
    return this.http.get<Vehicle>(url).pipe(
      tap(_ => console.log(`fetched vehicle by car with id=${id}`)),
      catchError(this.handleError<Vehicle>(`getVehicle id=${id}`))
    );
  }

  getCarsByPerson(id: number): Observable<Vehicle[]> {
      const url = `${this.url}/findByPerson/${id}`;
      return this.http.get<Vehicle[]>(url).pipe(
        tap(_ => this.log(`fetched cars by person with id=${id}`)),
        catchError(this.handleError<Vehicle[]>(`getCarsByPerson id=${id}`))
      );
  }

  getAllMeasurementErrors(vehicleId: number): Observable<Measurement[]>{
    return this.http.get<Measurement[]>(`${this.url}/${vehicleId}/measurementErrors`);
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
        // this.messageService.add(`InsuranceService: ${message}`);
        console.log(`InsuranceService: ${message}`);
    }
}
