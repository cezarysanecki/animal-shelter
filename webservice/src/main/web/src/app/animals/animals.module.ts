import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AnimalsBrowserModule } from "@animals/views/animals-browser";

@NgModule({
  declarations: [],
  exports: [
    AnimalsBrowserModule
  ],
  imports: [
    CommonModule,
    AnimalsBrowserModule
  ]
})
export class AnimalsModule { }
