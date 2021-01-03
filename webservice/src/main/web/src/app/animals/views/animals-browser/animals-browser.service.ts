import { Component } from '@angular/core';
import {AnimalsHttpService} from "../../services/animals-http.service";

@Component({
  selector: 'app-animals-browser',
  templateUrl: './animals-browser.component.html'
})
export class AnimalsBrowserService {

  constructor(
    private animalsHttpService: AnimalsHttpService
  ) { }
}
