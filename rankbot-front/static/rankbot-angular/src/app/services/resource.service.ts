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
  apiv1 = environment.api_v1;

  constructor(private httpClient: HttpClient) {
  }

  public match(artist: string, isLogged: boolean): Observable<any> {

    if (isLogged) {
      return this.httpClient.get<Song>(`${this.resourceUrl}match/${artist}`);
    } else {
      return this.httpClient.get<Song>(`${this.apiv1}match/${artist}`);
    }
  }

  public vote(token: number, vote: number, isLogged: boolean): Observable<any> {
    const payload = {
      token: token,
      vote: vote
    };

    if (isLogged) {
      return this.httpClient.post(`${this.resourceUrl}match`, payload);
    } else {
      return this.httpClient.post(`${this.apiv1}match`, payload);
    }
  }

  public ranking(artist: string): Observable<any> {
    return this.httpClient.get<SongData>(`${this.resourceUrl}results/${artist}`);
  }

  public globalRanking(artist: string, isLogged: boolean): Observable<any> {
    if (isLogged) {
      return this.httpClient.get<SongData>(`${this.resourceUrl}results/${artist}?global=true`);
    } else {
      return this.httpClient.get<SongData>(`${this.apiv1}results/${artist}`);

    }
  }
}
