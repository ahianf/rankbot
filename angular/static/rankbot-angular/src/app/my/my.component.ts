import { Component, OnInit, Renderer2 } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-my',
  templateUrl: './my.component.html',
  styleUrls: ['./my.component.css']
})
export class MyComponent implements OnInit {
  backgroundColor: string = 'green'

  constructor(private route: ActivatedRoute, private renderer: Renderer2) { }

  ngOnInit(): void {
    this.route.url.subscribe(url => {
      if (url[0]?.path === 'lana-del-rey') {
        this.backgroundColor = '#9a162d';
      } else if (url[0]?.path === 'death-grips') {
        this.backgroundColor = 'blue';
      } else {
        this.backgroundColor = 'white';
      }
    });

    const body = this.renderer.selectRootElement('body', true);
    const script = this.renderer.createElement('script');
    script.type = 'text/javascript';
    script.src = 'assets/script.js'
    this.renderer.appendChild(body, script);

  }
}