import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './navbar/navbar.component';
import {Config} from "./config/config";

const config: Config = {
  apiUrl: 'http://localhost:8080'
}

@NgModule({
  declarations: [NavbarComponent],
  imports: [
    CommonModule
  ],
  exports: [
    NavbarComponent
  ]
})
export class CoreModule { }
