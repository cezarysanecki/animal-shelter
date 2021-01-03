import {Inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Config, CONFIG} from "../../core/config/config";

@Injectable({
  providedIn: 'root'
})
export class AnimalsHttpService {

  constructor(
    private httpClient: HttpClient,
    @Inject(CONFIG) private config: Config
  ) {}
}
