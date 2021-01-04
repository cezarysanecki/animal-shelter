import { Injectable } from '@angular/core';
import { AnimalsHttpService } from "@animals/services/animals-http.service";

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
}
