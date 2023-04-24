import {Component, OnInit, Renderer2} from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-rank',
  templateUrl: './rank.component.html',
  styleUrls: ['./rank.component.css']
})
export class RankComponent implements OnInit {

  artist: string | null = '';
  url: string = '';


  constructor(private route: ActivatedRoute, private renderer: Renderer2) { }

  ngOnInit(): void {
    this.url = this.route.snapshot.url[1].path;
    console.log(this.url)
    this.artist = this.route.snapshot.paramMap.get('artist');
    const body = this.renderer.selectRootElement('body', true);
    const script = this.renderer.createElement('script');
    script.type = 'text/javascript';
    script.src = 'assets/ranking.js'
    this.renderer.appendChild(body, script);
  }

}
