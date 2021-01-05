import { Injectable } from '@angular/core';
import {AnimalsHttpService} from "@animals/services";

@Injectable({
  providedIn: 'root'
})
export class AnimalsBrowserService {

  constructor(
    private animalsHttpService: AnimalsHttpService
  ) { }

  getAnimals() {
    return this.animalsHttpService.getAnimals();
  }

  getAnimalDetails(animalId: number) {
    return this.animalsHttpService.getAnimalDetails(animalId);
  }
}
