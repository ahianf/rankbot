import { Injectable } from '@angular/core';
import { v4 as uuidv4 } from 'uuid';
@Injectable({
  providedIn: 'root'
})
export class StorageService {
  private readonly UUID_KEY = 'uuid';

  constructor() {}

  setUUID(uuid: string) {
    localStorage.setItem(this.UUID_KEY, uuid);
  }

  getUUID() {
    return localStorage.getItem(this.UUID_KEY);
  }

  generateUUID() {
    this.setUUID(uuidv4());
  }
}
