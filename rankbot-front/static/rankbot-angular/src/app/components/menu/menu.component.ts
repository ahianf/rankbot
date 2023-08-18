import {Component, OnInit} from '@angular/core';
import {environment} from 'src/environments/environment';
import {HttpParams} from '@angular/common/http';
import * as CryptoJS from 'crypto-js';
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  isLogged: boolean;

  artista: string;
  routerEvents: any;

  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.getLogged();
    this.routerEvents = this.router.events.subscribe(
      (event: any) => {
        if (event instanceof NavigationEnd) {
          this.artista = event.url.split("/")[2]
        }
      }
    )
  }

  getLogged(): void {
    this.isLogged = false;
  }

  getCurrentComponent(): string {
    let currentRoute = this.route;
    while (currentRoute.firstChild) {
      currentRoute = currentRoute.firstChild;
    }
    return currentRoute.component?.name || 'Unknown Component';
  }

  ngOnDestroy(): void {
    this.routerEvents.unsubscribe();
    // Unsubscribe to avoid memory leak
  }
}
