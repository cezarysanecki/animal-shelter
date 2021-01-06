import { Component, EventEmitter, Input, Output } from '@angular/core';
import { AnimalDetails } from "@animals/types";
import { animate, style, transition, trigger } from "@angular/animations";

@Component({
  selector: 'app-animal-details',
  templateUrl: './animal-details.component.html',
  styleUrls: ['./animal-details.component.css'],
  animations: [
    trigger('dialog', [
      transition('void => *', [
        style({ transform: 'translate(-100%)' }),
        animate(300)
      ]),
      transition('* => void', [
        animate(300, style({ transform: 'translate(-100%)' }))
      ])
    ])
  ]
})
export class AnimalDetailsComponent {

  @Input() animal: AnimalDetails;
  @Output() animalChange: EventEmitter<void> = new EventEmitter<void>();

  constructor() { }

  close() {
    this.animal = null;
    this.animalChange.emit();
  }
}
