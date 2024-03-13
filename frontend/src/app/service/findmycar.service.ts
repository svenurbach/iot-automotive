import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class FindmycarService {

 private findCarUrl = 'http://localhost:8080/api/findMyCar';  // URL to web api
 private findAdressUrl = 'https://nominatim.openstreetmap.org/reverse?';

    httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };

    constructor(private http: HttpClient) { }

    getAddress(lat: number, lon: number): Observable<Object> {
        return this.http.get<Object>(this.findAdressUrl + 'lat=' + lat + '&lon=' + lon + '&format=json').pipe(
         tap(_ => this.log(`fetched adress by location`)),
         catchError(this.handleError<Object>(`getAdress by location`))
       );
    }

    getParkingLocation(vehicleId: number): Observable<Object> {
        const url = `${this.findCarUrl}/${vehicleId}`;
        return this.http.get<Object>(url).pipe(
          tap(_ => this.log(`fetched location by car`)),
          catchError(this.handleError<Object>(`getParkingLocation by vehicleId`))
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
        // this.messageService.add(`FindmycarService: ${message}`);
        console.log(`FindmycarService: ${message}`);
    }
}
