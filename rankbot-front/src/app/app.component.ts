import { Router, RouterEvent, NavigationEnd } from '@angular/router';
import { MenuComponent } from './components/menu/menu.component';
import { Component, ViewChild, OnInit } from '@angular/core';
import { filter} from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'rankbot-front';

  @ViewChild('menu') menu: MenuComponent;

  constructor(
    private router: Router
  ) {}

  ngOnInit(): void {
    this.router.events.pipe(filter((event) => event instanceof NavigationEnd)).subscribe(()=> {
      this.menu.getLogged();
    });
  }
}
