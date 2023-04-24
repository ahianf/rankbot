import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RateComponent } from './rate/rate.component';
import {HomeComponent} from "./home/home.component";
import {RankComponent} from "./rank/rank.component";

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'lana-del-rey', component: RateComponent },
  { path: 'death-grips', component: RateComponent },
  { path: 'daft-punk', component: RateComponent },
  { path: 'radiohead', component: RateComponent },
  { path: 'wilco', component: RateComponent },
  { path: 'ranking/:artist', component: RankComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
