
import { Injectable, NgModule } from '@angular/core';
import { HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { Picture } from '../model/picture';
import { BrowserModule } from '@angular/platform-browser';

// @NgModule({
//     imports: [
//     BrowserModule,
//     HttpClientModule,
//     ],
// })
@Injectable({ providedIn: 'root' })
export class PictureService {

    private pictureUrl = 'http://localhost:8080/api/picture';  // URL to web api

    httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };

    constructor(private http: HttpClient) { }


    getPictures(): Observable<Picture[]> {
        return this.http.get<Picture[]>(this.pictureUrl + '/findAll')
        .pipe(
            tap(_ => this.log('fetched pictures')),
            catchError(this.handleError<Picture[]>('getPictures', []))
          );
    }


    getPicture(id: number): Observable<Picture> {
        const url = `${this.pictureUrl}/find/${id}`;
        return this.http.get<Picture>(url).pipe(
          tap(_ => this.log(`fetched picture id=${id}`)),
          catchError(this.handleError<Picture>(`getPicture id=${id}`))
        );
      }

     deletePicture(id: number): Observable<Picture> {
        const url = `${this.pictureUrl}/${id}`;
        return this.http.delete<Picture>(url).pipe(
          tap(_ => this.log(`deleted picture id=${id}`)),
          catchError(this.handleError<Picture>(`deletePicture id=${id}`))
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
        // this.messageService.add(`PictureService: ${message}`);
        console.log(`PictureService: ${message}`);
    }
}