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
    let uuid: string = uuidv4();
    this.setUUID(uuid);
    return uuid;
  }
}
