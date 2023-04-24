import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MyComponent } from './my/my.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: '/' },
  { path: 'lana-del-rey', component: MyComponent },
  { path: 'death-grips', component: MyComponent },
  { path: 'daft-punk', component: MyComponent },
  { path: 'radiohead', component: MyComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
