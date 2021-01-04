import { Component, Input, OnInit } from '@angular/core';
import {AnimalKind} from "@animals/enums";
import { AnimalShortInfo } from "@animals/types";

@Component({
  selector: 'app-animals-browser-card',
  templateUrl: './animals-browser-card.component.html',
  styleUrls: ['./animals-browser-card.component.css']
})
export class AnimalsBrowserCardComponent implements OnInit {

  @Input()
  animal: AnimalShortInfo;

  constructor() { }

  ngOnInit() { }

  decideImageToLoad(url: string) {
    return url != null ? url : `assets/images/animal-kinds/${AnimalKind[this.animal.kind]}.jpg`;
  }

  translateAnimalKind() {
    return `animal-kind.${AnimalKind[this.animal.kind]}`;
  }
}
