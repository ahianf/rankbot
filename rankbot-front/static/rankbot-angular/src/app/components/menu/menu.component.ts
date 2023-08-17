import {TokenService} from './../../services/token.service';
import {Component, OnInit} from '@angular/core';
import {environment} from 'src/environments/environment';
import {HttpParams} from '@angular/common/http';
import * as CryptoJS from 'crypto-js';
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";

const CHARACTERS = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  authorize_uri = environment.authorize_uri;
  logout_url = environment.logout_url;

  isLogged: boolean;

  params: any = {
    client_id: environment.client_id,
    redirect_uri: environment.redirect_uri,
    scope: environment.scope,
    response_type: environment.response_type,
    response_mode: environment.response_mode,
    code_challenge_method: environment.code_challenge_method
  }
  artista: string;
  routerEvents: any;

  constructor(
    private tokenService: TokenService,
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

  onLogin(): void {
    const code_verifier = this.generateCodeVerifier();
    this.tokenService.setVerifier(code_verifier);
    this.params.code_challenge = this.generateCodeChallenge(code_verifier);
    const httpParams = new HttpParams({fromObject: this.params});
    const codeUrl = this.authorize_uri + httpParams.toString();
    location.href = codeUrl;
  }

  onLogout(): void {
    location.href = this.logout_url;
  }

  getLogged(): void {
    this.isLogged = this.tokenService.isLogged();
  }

  generateCodeVerifier(): string {
    let result = '';
    const char_length = CHARACTERS.length;
    for (let i = 0; i < 44; i++) {
      result += CHARACTERS.charAt(Math.floor(Math.random() * char_length));
    }
    return result;
  }

  generateCodeChallenge(code_verifier: string): string {
    const codeverifierHash = CryptoJS.SHA256(code_verifier).toString(CryptoJS.enc.Base64);
    const code_challenge = codeverifierHash
      .replace(/=/g, '')
      .replace(/\+/g, '-')
      .replace(/\//g, '_');
    return code_challenge;
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
