import {ResourceInterceptor} from './interceptors/resource.interceptor';
import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './components/home/home.component';
import {AuthorizedComponent} from './components/authorized/authorized.component';
import {MenuComponent} from './components/menu/menu.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {RateComponent} from './components/rate/rate.component';
import {LogoutComponent} from './components/logout/logout.component';
import {RankingComponent} from "./components/ranking/ranking.component";
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AuthorizedComponent,
    MenuComponent,
    RateComponent,
    LogoutComponent,
    RankingComponent
  ],
    imports: [
        BrowserModule,
        HttpClientModule,
        AppRoutingModule,
        FormsModule
    ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: ResourceInterceptor, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule {
}
