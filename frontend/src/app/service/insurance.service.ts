import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { Insurance } from '../model/insurance.model';

@Injectable({
  providedIn: 'root'
})
export class InsuranceService {

    private url = 'http://localhost:8080/api/insurance';  // URL to web api

    httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };

    constructor(private http: HttpClient) { }

    getInsurances(): Observable<Insurance[]> {
        return this.http.get<Insurance[]>(this.url + '/findAll')
        .pipe(
            tap(_ => this.log('fetched Insurance')),
            catchError(this.handleError<Insurance[]>('getInsurances', []))
          );
    }

    getInsurance(id: number): Observable<Insurance> {
        const url = `${this.url}/find/${id}`;
        return this.http.get<Insurance>(url).pipe(
          tap(_ => this.log(`fetched Insurance id=${id}`)),
          catchError(this.handleError<Insurance>(`getInsurance id=${id}`))
        );
    }

    getInsurancesByPerson(id: number): Observable<Insurance[]> {
        const url = `${this.url}/findByPerson/${id}`;
        return this.http.get<Insurance[]>(url).pipe(
          tap(_ => this.log(`fetched Insurances by person with id=${id}`)),
          catchError(this.handleError<Insurance[]>(`getInsurancesByPerson id=${id}`))
        );
    }

    getInsuranceByCar(id: number): Observable<Insurance> {
        const url = `${this.url}/findByCar/${id}`;
        return this.http.get<Insurance>(url).pipe(
          tap(_ => this.log(`fetched Insurance by car with id=${id}`)),
          catchError(this.handleError<Insurance>(`getInsuranceByCar id=${id}`))
        );
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
}
