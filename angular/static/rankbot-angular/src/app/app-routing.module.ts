import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RateComponent } from './rate/rate.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: '/' },
  { path: 'lana-del-rey', component: RateComponent },
  { path: 'death-grips', component: RateComponent },
  { path: 'daft-punk', component: RateComponent },
  { path: 'radiohead', component: RateComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
