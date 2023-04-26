import {Component, OnInit, Renderer2} from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-rank',
  templateUrl: './rank.component.html',
  styleUrls: ['./rank.component.css']
})
export class RankComponent implements OnInit {

  artista: string | null = '';
  url: string = '';
  backgroundColor: string = 'green'
  backgroundImage: string = ''
  fontFamily: string = ''
  fontType = 'sans';

  constructor(private route: ActivatedRoute, private renderer: Renderer2) { }

  ngOnInit(): void {
    this.url = this.route.snapshot.url[1].path;

    this.artista = this.route.snapshot.paramMap.get('artist');
    const head = this.renderer.selectRootElement('head', true);
    const link = this.renderer.createElement('link');
    if (this.artista === 'lana-del-rey') {
      this.fontType = 'serif';
      this.backgroundColor = '#9a162d';
      this.renderer.setAttribute(link, 'rel', 'stylesheet');
      this.renderer.setAttribute(link, 'href', 'https://fonts.googleapis.com/css2?family=Abril+Fatface&display=swap');
      this.renderer.appendChild(document.head, link);
      this.fontFamily = "'Abril Fatface', serif"

    } else if (this.artista === 'death-grips') {
      this.backgroundColor = 'rgb(16, 15, 17';
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

    } else if (this.artista === 'wilco') {
      this.fontType = 'sans';
      this.backgroundColor = '#618bae';
      this.renderer.setAttribute(link, 'rel', 'stylesheet');
      this.renderer.setAttribute(link, 'href', 'https://fonts.googleapis.com/css2?family=Raleway&display=swap');
      this.renderer.appendChild(document.head, link);
      this.fontFamily = "'Raleway', sans-serif";

    }else {
      this.backgroundColor = 'rgba(0,0,0,0.5)';
    }



    const body = this.renderer.selectRootElement('body', true);
    const script = this.renderer.createElement('script');
    script.type = 'text/javascript';
    script.src = 'assets/ranking.js'
    this.renderer.appendChild(body, script);
  }

}
