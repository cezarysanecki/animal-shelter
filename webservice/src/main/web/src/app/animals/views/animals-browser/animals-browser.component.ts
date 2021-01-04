import { Component, OnInit } from '@angular/core';
import { AnimalShortInfo } from "@animals/types/animals.type";
import { AnimalsBrowserService } from "./animals-browser.service";

@Component({
  selector: 'app-animals-browser',
  templateUrl: './animals-browser.component.html'
})
export class AnimalsBrowserComponent implements OnInit {

  animals: AnimalShortInfo[] = [];

  constructor(
    private animalBrowserService: AnimalsBrowserService
  ) { }

  ngOnInit() {
    this.animalBrowserService.getAnimals().subscribe(data => {
      this.animals = data.animals;
    });
  }

}
