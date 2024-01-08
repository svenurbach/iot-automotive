import { Component, Input } from '@angular/core';
import { CommonModule, NgFor } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Picture } from '../model/picture'

@Component({
  selector: 'app-picture',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './picture.component.html',
  styleUrl: './picture.component.css'
})
export class PictureComponent {

  @Input() picture!: Picture;
  // myVar: any;
  
  ngOnInit(): void {
    this.picture = new Picture();
    this.picture.id = 12345;
    this.picture.name = "My Kitten";
  }

}

