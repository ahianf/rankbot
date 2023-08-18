import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {environment} from 'src/environments/environment';
import {Song, SongData} from "../model/song";
import {Injectable} from "@angular/core";


@Injectable({
  providedIn: 'root'
})
export class ResourceService {

  resourceUrl = environment.resource_url;

  constructor(private httpClient: HttpClient) {
  }

  public match(artist: string, uuid: string): Observable<any> {


    return this.httpClient.get<Song>(`${this.resourceUrl}match/${artist}?uuid=${uuid}`);
  }

  public vote(token: number, vote: number): Observable<any> {
    const payload = {
      token: token,
      vote: vote
    };

    return this.httpClient.post(`${this.resourceUrl}match`, payload);

  }

  public ranking(artist: string, uuid: string): Observable<any> {
    return this.httpClient.get<SongData>(`${this.resourceUrl}results/${artist}?uuid=${uuid}`);
  }

  public globalRanking(artist: string, uuid: string): Observable<any> {
    return this.httpClient.get<SongData>(`${this.resourceUrl}results/${artist}?global=true&uuid=${uuid}`);
  }
}
