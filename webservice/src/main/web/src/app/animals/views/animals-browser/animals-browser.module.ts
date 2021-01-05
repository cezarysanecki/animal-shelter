import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AnimalsBrowserComponent } from "@animals/views/animals-browser/animals-browser.component";
import { AnimalsBrowserService } from "@animals/views/animals-browser/animals-browser.service";
import { AnimalsBrowserCardComponent } from "@animals/views/animals-browser/components/animals-browser-card";
import { TranslocoRootModule } from "@transloco/transloco-root.module";
import { AnimalDetailsComponent } from "@animals/views/animals-browser/components/animal-details/animal-details.component";

@NgModule({
  declarations: [
    AnimalsBrowserComponent,
    AnimalsBrowserCardComponent,
    AnimalDetailsComponent
  ],
  exports: [
    AnimalsBrowserComponent
  ],
  providers: [
    AnimalsBrowserService
  ],
  imports: [
    CommonModule,
    TranslocoRootModule
  ]
})
export class AnimalsBrowserModule { }
