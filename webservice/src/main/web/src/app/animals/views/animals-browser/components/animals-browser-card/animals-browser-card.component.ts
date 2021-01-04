import {Component, Input, OnInit} from '@angular/core';
import {AnimalShortInfo} from "@animals/types/animals.type";
import {AnimalKind} from "@animals/enums/animal-kind.enum";

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

  translateAnimalKind(kind: AnimalKind) {
    return AnimalKind[kind];
  }
}
