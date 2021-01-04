import { Component, OnDestroy } from '@angular/core';
import { Subscription } from "rxjs";
import { AppLoaderService } from "./app-loader.service";

@Component({
  selector: 'app-app-loader',
  templateUrl: './app-loader.component.html',
  styleUrls: ['./app-loader.component.css']
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
