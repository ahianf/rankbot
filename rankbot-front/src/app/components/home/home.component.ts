import { Component, OnInit } from '@angular/core';
import {StorageService} from "../../services/storage.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private storageService: StorageService) { }

  ngOnInit(): void {
    if (this.storageService.getUUID() === null) {
      this.storageService.generateUUID();
    }
  }
}
