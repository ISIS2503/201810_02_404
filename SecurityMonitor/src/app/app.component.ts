import { Component } from '@angular/core';
import { AuthService } from './auth/auth.service';

declare var $:any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent{
  constructor(public auth: AuthService) {
    auth.handleAuthentication();
  }
}
