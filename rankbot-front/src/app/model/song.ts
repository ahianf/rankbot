export interface Song {
  songA: SongData;
  songB: SongData;
  token: number;
}

export interface SongData {
  id: number;
  songId: number;
  title: string;
  album: string;
  artist: string;
  artistId: number;
  elo: number;
  artUrl: string;
  enabled: boolean;
}
