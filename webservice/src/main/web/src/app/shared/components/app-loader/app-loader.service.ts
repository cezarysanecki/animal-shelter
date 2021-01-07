import { Injectable } from '@angular/core';
import { Observable, Subject } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AppLoaderService {

  private static onSubjectLoader = new Subject<boolean>();

  static onSetSubjectLoader(isLoading: boolean) {
    AppLoaderService.onSubjectLoader.next(isLoading);
  }

  static onGetSubjectLoader(): Observable<boolean> {
    return AppLoaderService.onSubjectLoader.asObservable();
  }
}
