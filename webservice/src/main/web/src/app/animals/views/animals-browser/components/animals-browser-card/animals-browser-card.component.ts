import { Component, EventEmitter, Input, Output } from '@angular/core';
import { AnimalShortInfo } from "@animals/types";

@Component({
  selector: 'app-animals-browser-card',
  templateUrl: './animals-browser-card.component.html',
  styleUrls: ['./animals-browser-card.component.css']
})
export class AnimalsBrowserCardComponent {

  @Input()
  animal: AnimalShortInfo;

  @Output()
  openDetails: EventEmitter<any> = new EventEmitter();

  constructor() { }

  openAnimalDetails() {
    this.openDetails.emit(this.animal.id);
  }
}
