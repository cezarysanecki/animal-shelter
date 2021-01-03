import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AnimalsBrowserComponent } from './animals-browser/animals-browser.component';

@NgModule({
  declarations: [AnimalsBrowserComponent],
  exports: [
    AnimalsBrowserComponent
  ],
  imports: [
    CommonModule
  ]
})
export class AnimalsModule { }
