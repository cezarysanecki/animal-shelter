import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AnimalsResult} from "../types/animals.type";

@Injectable({
  providedIn: 'root'
})
export class AnimalsHttpService {

  constructor(
    private httpClient: HttpClient
  ) {}

  getAnimals() {
    return this.httpClient.get<AnimalsResult>('/shelter/animals');
  }
}
