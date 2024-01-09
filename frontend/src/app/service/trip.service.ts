import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { Trip } from '../model/trip';

@Injectable({ providedIn: 'root' })
export class TripService {

    private url = 'http://localhost:8080/api/trip';  // URL to web api

    httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };

    constructor(private http: HttpClient) { }


    getTrips(): Observable<Trip[]> {
        return this.http.get<Trip[]>(this.url + '/findAll')
        .pipe(
            tap(_ => this.log('fetched trips')),
            catchError(this.handleError<Trip[]>('getTrips', []))
          );
    }


    getTrip(id: number): Observable<Trip> {
        const url = `${this.url}/find/${id}`;
        return this.http.get<Trip>(url).pipe(
          tap(_ => this.log(`fetched trip id=${id}`)),
          catchError(this.handleError<Trip>(`getTrip id=${id}`))
        );
      }

//      deletePicture(id: number): Observable<Picture> {
//         const url = `${this.pictureUrl}/${id}`;
//         return this.http.delete<Picture>(url).pipe(
//           tap(_ => this.log(`deleted picture id=${id}`)),
//           catchError(this.handleError<Picture>(`deletePicture id=${id}`))
//         );
//       }



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
