import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AnimalsBrowserModule } from "@animals/views/animals-browser";
import { AnimalDetailsComponent } from './views/animal-details/animal-details.component';

@NgModule({
  declarations: [AnimalDetailsComponent],
  exports: [
    AnimalsBrowserModule
  ],
  imports: [
    CommonModule,
    AnimalsBrowserModule
  ]
})
export class AnimalsModule { }
