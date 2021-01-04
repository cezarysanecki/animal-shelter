import {Component, Input, OnDestroy} from '@angular/core';
import {Subscription} from "rxjs";
import {AppLoaderService} from "./app-loader.service";

@Component({
  selector: 'app-app-loader',
  templateUrl: './app-loader.component.html',
  styleUrls: ['./app-loader.component.css']
})
export class AppLoaderComponent implements OnDestroy {

  @Input()
  isLoading: boolean;

  onSubscriptionLoader: Subscription;

  constructor() {
    this.isLoading = false;
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
