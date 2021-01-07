import { Injectable } from '@angular/core';
import { AnimalsHttpService } from "@animals/services";
import { map } from "rxjs/operators";
import { AnimalKind } from "@animals/enums";
import { GetAnimalDetails, GetAnimalsRecord } from "@animals/services/types/http-animals.type";
import { AnimalDetails, AnimalShortInfo } from "@animals/types/animals.type";

@Injectable({
  providedIn: 'root'
})
export class AnimalsBrowserService {

  constructor(
    private animalsHttpService: AnimalsHttpService
  ) { }

  getAnimals() {
    return this.animalsHttpService.getAnimals().pipe(
      map(result => result.animals),
      map(animals => animals.map(animal => AnimalsBrowserService.convertToAnimalShortInfo(animal)))
    );
  }

  getAnimalDetails(animalId: number) {
    return this.animalsHttpService.getAnimalDetails(animalId).pipe(
      map(animal => AnimalsBrowserService.convertToAnimalDetails(animal))
    );
  }

  private static convertToAnimalShortInfo(animalRecord: GetAnimalsRecord): AnimalShortInfo {
    return {
      id: animalRecord.id,
      name: animalRecord.name,
      age: animalRecord.age,
      imageUrl: `assets/images/animal-kinds/${AnimalKind[animalRecord.kind]}.jpg`,
      translocoKind: `animal-kind.${AnimalKind[animalRecord.kind]}`,
      inShelter: animalRecord.inShelter
    }
  }

  private static convertToAnimalDetails(animalDetails: GetAnimalDetails): AnimalDetails {
    return {
      id: animalDetails.id,
      name: animalDetails.name,
      age: animalDetails.age,
      imageUrl: `assets/images/animal-kinds/${AnimalKind[animalDetails.kind]}.jpg`,
      translocoKind: `animal-kind.${AnimalKind[animalDetails.kind]}`,
      admittedAt: animalDetails.admittedAt,
      adoptedAt: animalDetails.adoptedAt,
    }
  }
}
