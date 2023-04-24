import {Component, OnInit, Renderer2} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-my',
  templateUrl: './my.component.html',
  styleUrls: ['./my.component.css']
})
export class MyComponent implements OnInit {
  backgroundColor: string = 'green'
  backgroundImage: string = ''
  fontFamily: string = ''
  constructor(private route: ActivatedRoute, private renderer: Renderer2) {
  }

  ngOnInit(): void {

    const head = this.renderer.selectRootElement('head', true);


    this.route.url.subscribe(url => {
      if (url[0]?.path === 'lana-del-rey') {
        this.backgroundColor = '#9a162d';
        const link = this.renderer.createElement('link');
        this.renderer.setAttribute(link, 'rel', 'stylesheet');
        this.renderer.setAttribute(link, 'href', 'https://fonts.googleapis.com/css2?family=Abril+Fatface&display=swap');
        this.renderer.appendChild(document.head, link);
        this.fontFamily = "'Abril Fatface', serif"

      } else if (url[0]?.path === 'death-grips') {
        this.backgroundColor = 'rgb(0,0,0)';
        this.backgroundImage = 'url("/assets/images/death-grips/back.png")'
      } else if (url[0]?.path === 'daft-punk') {
        this.backgroundColor = 'rgba(0,0,0,0.5)';
        this.backgroundImage = 'url("/assets/images/daft-punk/back.png")'
      } else {
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
