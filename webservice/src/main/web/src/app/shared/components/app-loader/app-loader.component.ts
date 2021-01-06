import { Component, OnDestroy } from '@angular/core';
import { Subscription } from "rxjs";
import { AppLoaderService } from "./app-loader.service";
import { animate, style, transition, trigger } from "@angular/animations";

@Component({
  selector: 'app-app-loader',
  templateUrl: './app-loader.component.html',
  styleUrls: ['./app-loader.component.css'],
  animations: [
    trigger('loader', [
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
export class AppLoaderComponent implements OnDestroy {

  isLoading = false;

  onSubscriptionLoader: Subscription;

  constructor() {
    this.onSubscriptionLoader = AppLoaderService.onGetSubjectLoader().subscribe((isLoading: boolean) => {
      this.isLoading = isLoading;
    });
  }

  ngOnDestroy() {
    if (this.onSubscriptionLoader) {
      this.onSubscriptionLoader.unsubscribe();
    }
  }
}
