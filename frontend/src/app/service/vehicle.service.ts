import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {Insurance} from "../model/insurance.model";
import {catchError, tap} from "rxjs/operators";
import {VehicleModel} from "../model/vehicle-model.model";
import {Vehicle} from "../model/vehicle.model";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class VehicleService{

  private url = 'http://localhost:8080/api/vehicle';


  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  getVehicles(): Observable<Vehicle[]> {
    return this.http.get<Vehicle[]>(this.url + '/findAll')
      .pipe(
        tap(_ => console.log('fetched Vehicle')),
        catchError(this.handleError<Vehicle[]>('getInsurances', []))
      );
  }

  getVehicle(id: number): Observable<Vehicle>{
    const url = `${this.url}/vehicle/${id}`;
    return this.http.get<Vehicle>(url).pipe(
      tap(_ => console.log(`fetched Insurance by car with id=${id}`)),
      catchError(this.handleError<Vehicle>(`getInsuranceByCar id=${id}`))
    );
  }

  getVehicleById(id: number): Observable<Vehicle> {
    const url = `${this.url}/${id}`;
    return this.http.get<Vehicle>(url);
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      console.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
