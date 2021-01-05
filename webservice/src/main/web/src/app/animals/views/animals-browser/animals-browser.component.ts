import { Component, OnInit } from '@angular/core';
import { AnimalDetails, AnimalShortInfo } from "@animals/types";
import { AnimalsBrowserService } from "@animals/views/animals-browser/animals-browser.service";
import { withLoader } from "@core/decorators";

@Component({
  selector: 'app-animals-browser',
  templateUrl: './animals-browser.component.html'
})
export class AnimalsBrowserComponent implements OnInit {

  currentAnimal: AnimalDetails = null;

  animals: AnimalShortInfo[] = [];

  constructor(
    private animalBrowserService: AnimalsBrowserService
  ) { }

  ngOnInit() {
    withLoader(
      this.animalBrowserService.getAnimals()
    ).subscribe(data => {
      this.animals = data.animals;
    });
  }

  handleOpenDetails(animalId: number) {
    withLoader(
      this.animalBrowserService.getAnimalDetails(animalId)
    ).subscribe(data => {
      this.currentAnimal = data;
    });
  }

  handleCloseDetails() {
    this.currentAnimal = null;
  }
}
