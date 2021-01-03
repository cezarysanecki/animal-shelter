import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {AnimalsBrowserModule} from "./views/animals-browser/animals-browser.module";

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
