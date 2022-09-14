import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root',
})
export class AppCookieService {
  private ACCESS_TOKEN: string = 'ACCESS_TOKEN';

  constructor(private cookieService: CookieService, private router: Router) {}

  setAccessToken(token: string) {
    return this.cookieService.set(this.ACCESS_TOKEN, token);
  }

  getAccessToken(token: string) {
    if (this.cookieService.get(this.ACCESS_TOKEN) === undefined) {
      console.log('fff');

      this.router.navigate(['/login']);
      return;
    }
    console.log('ddd');
    return this.cookieService.get(this.ACCESS_TOKEN);
  }

  hasAccessToken(): boolean {
    return this.cookieService.check(this.ACCESS_TOKEN);
  }

  clearAccessToken(): void {
    return this.cookieService.deleteAll();
  }
}
