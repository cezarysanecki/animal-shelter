import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AnimalsBrowserComponent } from './views/animals-browser/animals-browser.component';
import {AnimalsBrowserCardComponent} from "./views/animals-browser/components/animals-browser-card/animals-browser-card.component";

@NgModule({
  declarations: [AnimalsBrowserComponent, AnimalsBrowserCardComponent],
  exports: [
    AnimalsBrowserComponent
  ],
  imports: [
    CommonModule
  ]
})
export class AnimalsModule { }
