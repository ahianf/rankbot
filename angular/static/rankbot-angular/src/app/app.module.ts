import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module'; // Importamos el módulo de rutas
import { AppComponent } from './app.component';
import { RateComponent } from './rate/rate.component';
import { HomeComponent } from './home/home.component';
import { RankComponent } from './rank/rank.component';

@NgModule({
  declarations: [
    AppComponent,
    RateComponent,
    HomeComponent,
    RankComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule // Agregamos el módulo de rutas
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
