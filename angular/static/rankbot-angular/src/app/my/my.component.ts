import {Component, Inject, OnInit, Renderer2} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import { DOCUMENT } from '@angular/common';

@Component({
  selector: 'app-my',
  templateUrl: './my.component.html',
  styleUrls: ['./my.component.css']
})
export class MyComponent implements OnInit {
  backgroundColor: string = 'green'
  backgroundImage: string = ''
  fontFamily: string = ''
  fontType = 'sans';
  constructor(private route: ActivatedRoute, private renderer: Renderer2, @Inject(DOCUMENT) private document: Document) {
  }

  ngOnInit(): void {

    const head = this.renderer.selectRootElement('head', true);
    const link = this.renderer.createElement('link');


    this.route.url.subscribe(url => {
      if (url[0]?.path === 'lana-del-rey') {
        this.fontType = 'serif';
        this.backgroundColor = '#9a162d';
        this.renderer.setAttribute(link, 'rel', 'stylesheet');
        this.renderer.setAttribute(link, 'href', 'https://fonts.googleapis.com/css2?family=Abril+Fatface&display=swap');
        this.renderer.appendChild(document.head, link);
        this.fontFamily = "'Abril Fatface', serif"

      } else if (url[0]?.path === 'death-grips') {
        this.backgroundColor = 'rgb(0,0,0)';
        this.backgroundImage = 'url("/assets/images/death-grips/back.png")'
        this.renderer.setAttribute(link, 'rel', 'stylesheet');
        this.renderer.setAttribute(link, 'href', 'https://fonts.googleapis.com/css2?family=Courier+Prime&display=swap');
        this.renderer.appendChild(document.head, link);
        this.fontFamily = "'Courier Prime', monospace"

      } else if (url[0]?.path === 'daft-punk') {
        this.fontType = 'sans';
        this.backgroundColor = 'purple';
        this.backgroundImage = 'url("/assets/images/daft-punk/back.png")'
        this.renderer.setAttribute(link, 'rel', 'stylesheet');
        this.renderer.setAttribute(link, 'href', 'https://fonts.googleapis.com/css2?family=Orbitron&display=swap');
        this.renderer.appendChild(document.head, link);
        this.fontFamily = "'Orbitron', sans-serif";

      }else if (url[0]?.path === 'radiohead') {
        this.fontType = 'sans';
        this.backgroundColor = '#9a162d';
        this.backgroundImage = 'url("/assets/images/radiohead/back.png")'
        this.renderer.setAttribute(link, 'rel', 'stylesheet');
        this.renderer.setAttribute(link, 'href', 'https://fonts.googleapis.com/css2?family=Didact+Gothic&display=swap');
        this.renderer.appendChild(document.head, link);
        this.fontFamily = "'Didact Gothic', sans-serif";


      }else {
        this.backgroundColor = 'rgba(0,0,0,0.5)';
      }
    });

    const body = this.renderer.selectRootElement('body', true);
    const script = this.renderer.createElement('script');
    script.type = 'text/javascript';
    script.src = 'assets/script.js'
    this.renderer.appendChild(body, script);

  }
}
