import { Component, EventEmitter, Input, Output } from '@angular/core';
import { AnimalDetails } from "@animals/types";
import { animate, style, transition, trigger } from "@angular/animations";

@Component({
  selector: 'app-animal-details',
  templateUrl: './animal-details.component.html',
  styleUrls: ['./animal-details.component.css'],
  animations: [
    trigger('dialog', [
      transition(':enter', [
        style({ opacity: '0' }),
        animate(300)
      ]),
      transition(':leave', [
        animate(300, style({ opacity: '0' }))
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
