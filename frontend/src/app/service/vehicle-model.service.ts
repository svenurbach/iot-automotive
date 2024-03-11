import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {Insurance} from "../model/insurance.model";
import {catchError, tap} from "rxjs/operators";
import {VehicleModel} from "../model/vehicle-model.model";

export class VehicleModelService{

  private url = 'http://localhost:8080/api/vehicleModel';


  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  getInsurances(): Observable<VehicleModel[]> {
    return this.http.get<VehicleModel[]>(this.url + '/findAll')
      .pipe(
        tap(_ => console.log('fetched VehicleModel')),
        catchError(this.handleError<VehicleModel[]>('getInsurances', []))
      );
  }

  getVehicleDetails(id: number): Observable<VehicleModel>{
    const url = `${this.url}/vehicleModel/${id}`;
    return this.http.get<VehicleModel>(url).pipe(
      tap(_ => console.log(`fetched Insurance by car with id=${id}`)),
      catchError(this.handleError<VehicleModel>(`getInsuranceByCar id=${id}`))
    );
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
