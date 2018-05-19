import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class AlertsService {

  options;

  constructor(
    private http: Http
  ) { }

  createAuthenticationHeaders() {
    // this.authService.loadToken();
    this.options = new RequestOptions({
      headers: new Headers({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6Ik1FWTJOelpDT0RkQk1UTkVPVFkwTlRGRU5USkVSRUUyTURVMlFUY3lNVFE0TURkRE1VWkRSQSJ9.eyJodHRwOi8veWFsZS00MDQvcm9sZXMiOlsieWFsZSJdLCJpc3MiOiJodHRwczovL2lzaXMyNTAzLTQwNC5hdXRoMC5jb20vIiwic3ViIjoiYXV0aDB8NWFmOTBlZGZlMWZlZTA2NjcwMDllMDRmIiwiYXVkIjpbInlhbGUtNDA0LmNvbSIsImh0dHBzOi8vaXNpczI1MDMtNDA0LmF1dGgwLmNvbS91c2VyaW5mbyJdLCJpYXQiOjE1MjY3NDIyMDEsImV4cCI6MTUyNjgyODYwMSwiYXpwIjoiU3ZSNmZ3eU92SUxybmhuQUV2NDhNZ2lUSXhxdUVzbTYiLCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIGVtYWlsIGFkZHJlc3MgcGhvbmUiLCJndHkiOiJwYXNzd29yZCJ9.H8yAsEnSZ9FAOKt9MnVe6mZGStjFYzCqmyYhrTSnlinUUyxnr0l842Jv-8QzxQQvkLXuU28UFsEBD7IhJ1gR6DHgw7tC9MCS2WtbkohtSBqE3ixixTVcBO9iOrLvLJrJai6FTvKlWbjtf8IV1IHMe9ei8mCVmmKTziYiLdc0mcAMJ0MWApQcAouPLwzRT4-cZ3OEJqaFqZf1uAZi6ZCxC7dWwAM9Pc8wj0x2jrkSjMze9GHfIoXbnmzPqZBOggHs2GWZI3y-uChUEVtQl6uQMNm-MFkyjmxaCwv7NV6y8X4jKeM_etr4RWxASrXHny5kapuTWG_mC3Bnhxu-EHzMVw'
      })
    });
  }

  getAlerts() {
    this.createAuthenticationHeaders();
    return this.http.get( 'http://172.24.42.74:8080/alert', this.options).map(res => res.json());
  }
}
