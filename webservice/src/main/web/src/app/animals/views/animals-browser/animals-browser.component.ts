import { Component, OnInit } from '@angular/core';
import {AnimalShortInfo} from "../../types/animals.type";

@Component({
  selector: 'app-animals-browser',
  templateUrl: './animals-browser.component.html'
})
export class AnimalsBrowserComponent implements OnInit {

  animals: AnimalShortInfo[] = [];

  constructor() { }

  ngOnInit() { }

}
