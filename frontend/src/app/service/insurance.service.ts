import { Injectable } from '@angular/core';

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
