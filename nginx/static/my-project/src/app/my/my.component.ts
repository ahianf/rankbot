import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-my',
  templateUrl: './my.component.html',
  styleUrls: ['./my.component.css']
})
export class MyComponent implements OnInit {
  backgroundColor: string = 'green'

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.url.subscribe(url => {
      if (url[0]?.path === 'lana-del-rey') {
        this.backgroundColor = 'red';
      } else if (url[0]?.path === 'death-grips') {
        this.backgroundColor = 'blue';
      } else {
        this.backgroundColor = 'white';
      }
    });
  }
}
