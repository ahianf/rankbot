import {Component, OnInit, Renderer2} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ResourceService} from "../../services/resource.service";
import {TokenService} from "../../services/token.service";

@Component({
  selector: 'app-rank',
  templateUrl: './ranking.component.html',
  styleUrls: ['./ranking.component.css']
})
export class RankingComponent implements OnInit {

  artista: string | null = '';
  backgroundColor: string = 'green'
  backgroundImage: string = ''
  fontFamily: string = ''
  fontType = 'sans';
  rankingsData: any[] = [];
  showGlobalRankings: boolean = false;
  isLogged: boolean;

  constructor(private route: ActivatedRoute, private renderer: Renderer2, private resourceService: ResourceService, private tokenService: TokenService,) {
  }

  ngOnInit(): void {
    this.getLogged();
    if (this.isLogged === false){
      this.showGlobalRankings = true;
    }

    this.artista = this.route.snapshot.paramMap.get('artist');
    const link = this.renderer.createElement('link');
    if (this.artista === 'lana-del-rey') {
      this.fontType = 'serif';
      this.backgroundColor = '#9a162d';
      this.renderer.setAttribute(link, 'rel', 'stylesheet');
      this.renderer.setAttribute(link, 'href', 'https://fonts.googleapis.com/css2?family=Abril+Fatface&display=swap');
      this.renderer.appendChild(document.head, link);
      this.fontFamily = "'Abril Fatface', serif"

    } else if (this.artista === 'death-grips') {
      this.backgroundColor = 'rgb(16, 15, 17)';
      this.renderer.setAttribute(link, 'rel', 'stylesheet');
      this.renderer.setAttribute(link, 'href', 'https://fonts.googleapis.com/css2?family=Courier+Prime&display=swap');
      this.renderer.appendChild(document.head, link);
      this.fontFamily = "'Courier Prime', monospace"

    } else if (this.artista === 'daft-punk') {
      this.fontType = 'sans';
      this.backgroundColor = 'purple';
      this.backgroundImage = 'url("/assets/images/daft-punk/back.png")'
      this.renderer.setAttribute(link, 'rel', 'stylesheet');
      this.renderer.setAttribute(link, 'href', 'https://fonts.googleapis.com/css2?family=Orbitron&display=swap');
      this.renderer.appendChild(document.head, link);
      this.fontFamily = "'Orbitron', sans-serif";

    } else if (this.artista === 'radiohead') {
      this.fontType = 'sans';
      this.backgroundColor = 'rgb(0,0,0)';
      this.backgroundImage = 'url("/assets/images/radiohead/back.png")'
      this.renderer.setAttribute(link, 'rel', 'stylesheet');
      this.renderer.setAttribute(link, 'href', 'https://fonts.googleapis.com/css2?family=Didact+Gothic&display=swap');
      this.renderer.appendChild(document.head, link);
      this.fontFamily = "'Didact Gothic', sans-serif";

    } else if (this.artista === 'taylor-swift') {
      this.fontType = 'serif';
      this.backgroundColor = 'rgb(238,141,141)';
      this.renderer.setAttribute(link, 'rel', 'stylesheet');
      this.renderer.setAttribute(link, 'href', 'https://fonts.googleapis.com/css2?family=Source+Serif+Pro&display=swap');
      this.renderer.appendChild(document.head, link);
      this.fontFamily = "'Source Serif Pro', serif";


    } else if (this.artista === 'wilco') {
      this.fontType = 'sans';
      this.backgroundColor = '#618bae';
      this.renderer.setAttribute(link, 'rel', 'stylesheet');
      this.renderer.setAttribute(link, 'href', 'https://fonts.googleapis.com/css2?family=Raleway&display=swap');
      this.renderer.appendChild(document.head, link);
      this.fontFamily = "'Raleway', sans-serif";

    } else {
      this.backgroundColor = 'rgba(0,0,0,0.5)';
    }

    this.loadRankingsData(this.artista);
  }

  loadRankingsData(artista: string) {
    if (this.showGlobalRankings) {
      this.resourceService.globalRanking(artista, this.isLogged).subscribe(data => {
        this.rankingsData = data;
      });
    } else {
      this.resourceService.ranking(artista).subscribe(data => {
        this.rankingsData = data;
      });
    }
  }

  getLogged(): void {
    this.isLogged = this.tokenService.isLogged();
  }

}
