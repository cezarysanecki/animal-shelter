import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AnimalShortInfo} from "../types/animals.type";

@Injectable({
  providedIn: 'root'
})
export class AnimalsHttpService {

  constructor(
    private httpClient: HttpClient
  ) {}

  getAnimals() {
    return this.httpClient.get<AnimalShortInfo[]>('/shelter/animals');
  }
}
