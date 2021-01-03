import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './navbar/navbar.component';
import {CONFIG, Config} from "./config/config";

const config: Config = {
  apiUrl: 'http://localhost:8080/shelter'
}

@NgModule({
  declarations: [NavbarComponent],
  imports: [
    CommonModule
  ],
  providers: [
    { provide: CONFIG, useValue: config }
  ],
  exports: [
    NavbarComponent
  ]
})
export class CoreModule { }
