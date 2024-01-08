import { Component, OnInit } from '@angular/core';
import { Picture } from '../model/picture';
import { PictureService } from '../service/picture.service';
import { CommonModule } from '@angular/common';
import {NgFor, NgForOf} from "@angular/common";
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, NgFor],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

  pictures: Picture[] = [];

  constructor(private pictureService: PictureService) { }

  ngOnInit(): void {
    this.getPictures();
  }

  getPictures(): void {
    this.pictureService.getPictures()
    .subscribe(pictures => this.pictures = pictures);
  }


  delete(picture: Picture): void {
    this.pictures = this.pictures.filter(pic => pic !== picture);
    this.pictureService.deletePicture(picture.id).subscribe();
  }
}
