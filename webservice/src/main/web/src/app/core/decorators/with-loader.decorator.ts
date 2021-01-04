import { Observable } from "rxjs";
import { finalize } from "rxjs/operators";
import { AppLoaderService } from "@shared/components/app-loader/app-loader.service";

export function withLoader<T>(observable: Observable<T>) {
  AppLoaderService.onSetSubjectLoader(true);
  return observable.pipe(finalize(() => AppLoaderService.onSetSubjectLoader(false)))
}
