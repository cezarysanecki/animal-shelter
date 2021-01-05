import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { AnimalDetails } from "@animals/types";

@Component({
  selector: 'app-animal-details',
  templateUrl: './animal-details.component.html',
  styleUrls: ['./animal-details.component.css']
})
export class AnimalDetailsComponent implements OnInit {

  @Input()
  animal: AnimalDetails;

  @Output()
  closeDetails: EventEmitter<any> = new EventEmitter();

  constructor() { }

  ngOnInit() { }

  close() {
    this.closeDetails.emit();
  }
}
