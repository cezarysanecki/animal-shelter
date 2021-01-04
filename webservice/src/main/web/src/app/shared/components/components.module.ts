import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AppLoaderModule } from "@shared/components/app-loader/app-loader.module";

@NgModule({
  imports: [
    CommonModule,
    AppLoaderModule
  ],
  exports: [
    AppLoaderModule
  ]
})
export class ComponentsModule { }
