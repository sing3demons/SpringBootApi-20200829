import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IChatResponse } from '../interfaces/chat-response';

@Injectable({
  providedIn: 'root',
})
export class ChatService {
  constructor(private http: HttpClient) {}

  postMessage(message: string): Observable<IChatResponse> {
    let url: string = 'http://localhost:8080/chat/message';

    let body = {
      message,
    };
    return this.http.post<IChatResponse>(url, body);
  }
}
