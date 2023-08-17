import {LogoutComponent} from './components/logout/logout.component';
import {RateComponent} from './components/rate/rate.component';
import {AuthorizedComponent} from './components/authorized/authorized.component';
import {HomeComponent} from './components/home/home.component';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {RankingComponent} from "./components/ranking/ranking.component";

const routes: Routes = [
  {path: 'rate/lana-del-rey', component: RateComponent},
  {path: 'rate/death-grips', component: RateComponent},
  {path: 'rate/daft-punk', component: RateComponent},
  {path: 'rate/radiohead', component: RateComponent},
  {path: 'rate/taylor-swift', component: RateComponent},
  {path: 'rate/wilco', component: RateComponent},
  {path: '', component: HomeComponent},
  {path: 'authorized', component: AuthorizedComponent},
  {path: 'logout', component: LogoutComponent},
  {path: 'ranking/:artist', component: RankingComponent},
  {path: '**', redirectTo: '', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
