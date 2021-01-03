import { InjectionToken } from '@angular/core';

export type Config = {
  apiUrl: string;
}

export const CONFIG = new InjectionToken<Config>('app.config');
