import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import * as SockJS from 'sockjs-client';
import { IChatMessage } from 'src/app/interfaces/chat-message';
import { ChatService } from 'src/app/services/chat.service';
import * as Stomp from 'stompjs';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss'],
})
export class ChatComponent implements OnInit {
  private stompClient: Stomp.Client | any;
  private CHANNEL = '/topic/chat';
  private ENDPOINT = 'http://localhost:8080/socket';

  isConnected = false;
  messages: IChatMessage[] = [];

  chatFormGroup: FormGroup = new FormGroup({
    message: new FormControl('', Validators.required),
  });
  constructor(private chatService: ChatService) {}
  ngOnInit(): void {
    this.connectWebsocket();
  }

  private connectWebsocket() {
    let ws: WebSocket = new SockJS(this.ENDPOINT);
    this.stompClient = Stomp.over(ws);

    this.stompClient.connect({}, () => {
      this.isConnected = true;
      this.subscribeToGlobalChat();
    });
  }

  private subscribeToGlobalChat() {
    this.stompClient.subscribe(this.CHANNEL, (msg: any) => {
      let newMessage: IChatMessage = JSON.parse(msg.body) as IChatMessage;
      this.messages.push(newMessage);
    });
  }

  onSubmit() {
    let message = this.chatFormGroup.controls.message.value;
    // is connected?
    if (!this.isConnected) {
      alert('Please connect to Websocket');
      return;
    }

    // validate message
    this.chatService.postMessage(message).subscribe(
      (msg) => {},
      (error) => console.log(error)
    );
  }
}
