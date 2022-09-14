import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ILoginResponse } from '../interfaces/login-response';
import { IRegisterRequest } from '../interfaces/register-request';
import { IRegisterResponse } from '../interfaces/register-response';

interface LoginRequest {
  email: string;
  password: string;
}

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient) {}

  login(email: string, password: string): Observable<ILoginResponse> {
    let url = 'http://localhost:8080/api/auth/login';

    let body: LoginRequest = { email, password };
    return this.http.post<ILoginResponse>(url, body);
  }

  register(
    email: string,
    password: string,
    name: string
  ): Observable<IRegisterResponse> {
    let url = 'http://localhost:8080/api/auth/register';

    let body: IRegisterRequest = { email, password, name };
    return this.http.post<IRegisterResponse>(url, body);
  }
}
