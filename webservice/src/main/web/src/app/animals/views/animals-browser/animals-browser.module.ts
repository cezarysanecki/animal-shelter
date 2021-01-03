import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {AnimalsBrowserComponent} from "./animals-browser.component";
import {AnimalsBrowserCardComponent} from "./components/animals-browser-card/animals-browser-card.component";
import {AnimalsBrowserService} from "./animals-browser.service";

@NgModule({
  declarations: [AnimalsBrowserComponent, AnimalsBrowserCardComponent],
  exports: [
    AnimalsBrowserComponent
  ],
  providers: [
    AnimalsBrowserService
  ],
  imports: [
    CommonModule
  ]
})
export class AnimalsBrowserModule { }
