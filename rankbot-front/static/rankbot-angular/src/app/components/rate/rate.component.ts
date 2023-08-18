import {ResourceService} from './../../services/resource.service';
import {Component, Inject, OnInit, Renderer2} from '@angular/core';
import {DOCUMENT} from "@angular/common";
import {ActivatedRoute} from "@angular/router";
import {Song, SongData} from "../../model/song";
import {StorageService} from "../../services/storage.service";

@Component({
  selector: 'app-user',
  templateUrl: './rate.component.html',
  styleUrls: ['./rate.component.css']
})
export class RateComponent implements OnInit {


  backgroundColor: string = 'green'
  backgroundImage: string = ''
  fontFamily: string = ''
  fontType = 'sans';
  artista: string = ''

  song: Song;
  animacionEntrada: boolean = false;
  animacionSalida: boolean = false;
  uuid: string = this.storageService.getUUID();

  constructor(
    private resourceService: ResourceService,
    private storageService: StorageService,
    private route: ActivatedRoute,
    private renderer: Renderer2,
    @Inject(DOCUMENT) private document: Document,
  ) {
  }

  ngOnInit(): void {
    if (this.storageService.getUUID() === null) {
      this.storageService.generateUUID();
    }
    this.initEmptySong();
    const link = this.renderer.createElement('link');
    this.route.url.subscribe(url => {
      this.artista = url[1]?.path
      if (this.artista === 'lana-del-rey') {
        this.fontType = 'serif';
        this.backgroundColor = '#9a162d';
        this.renderer.setAttribute(link, 'rel', 'stylesheet');
        this.renderer.setAttribute(link, 'href', 'https://fonts.googleapis.com/css2?family=Abril+Fatface&display=swap');
        this.renderer.appendChild(document.head, link);
        this.fontFamily = "'Abril Fatface', serif"

      } else if (this.artista === 'death-grips') {
        this.backgroundColor = 'rgb(0,0,0)';
        this.backgroundImage = 'url("/assets/images/death-grips/back.png")'
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
    });
    this.obtenerMatch(this.artista);
    this.uuid = this.storageService.getUUID();
  }

  obtenerMatch(artist) {
    this.resourceService.match(artist, this.uuid).subscribe(data => {
        this.song = data;
        this.animacionSalida = false;
        this.animacionEntrada = true;
      },
      err => {
        console.log("obtenerMatch(${vote})" + err);
      });
  }

  enviarVoto(vote: number) {
    this.animacionSalida = true;
    this.resourceService.vote(this.song.token, vote).subscribe(
      (response) => {
        this.obtenerMatch(this.artista);
      },
      (error) => {
        console.error(`enviarVoto(${vote})`, error);
      }
    );
  }

  initEmptySong() {
    const songA: SongData = {
      id: 0,
      songId: 0,
      title: '',
      album: '',
      artist: '',
      artistId: 0,
      elo: 0,
      artUrl: 'assets/images/placeholder.png',
      enabled: false
    };

    const songB: SongData = {
      id: 0,
      songId: 0,
      title: '',
      album: '',
      artist: '',
      artistId: 0,
      elo: 0,
      artUrl: 'assets/images/placeholder.png',
      enabled: false
    };
    this.song = {
      songA,
      songB,
      token: 0
    };
  }
}
