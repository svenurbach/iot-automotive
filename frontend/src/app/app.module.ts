import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { TripComponent } from './trip/trip.component';
import { TripService } from './service/Trip.service';
import { CommonModule } from '@angular/common';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

@NgModule({
declarations: [
    // AppComponent,
    // PictureComponent

  ],
  imports: [
    CommonModule,
  ]
  providers: [
    TripService,
    // {
    //     provide: HTTP_INTERCEPTORS,
    //     useClass: JsonDateInterceptor,
    //     multi: true
    //   }
  ],
})
export class AppModule { }




