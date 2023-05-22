import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import { useServerStore } from '@/store/serverstore'
import { useAppIsReadyStore } from '@/store/appisreadystore';

export function connectWebSocket() {

  const serverStore = useServerStore();
  const appIsReadyStore = useAppIsReadyStore();

  const stompClient = new Client({
    webSocketFactory: () => new SockJS('http://localhost:9797/ws')
  });

  stompClient.onConnect = () => {
    console.log("Stomp client connected");
    appIsReadyStore.setIsLoading(false);

    stompClient.subscribe('/topic/server/console', message => {
      serverStore.addLine(message.body);
    })

    // todo: possibly remove?
    stompClient.subscribe('/topic/managercraft/console', message => {
      serverStore.addLine(message.body);
    })
  };

  stompClient.onStompError = (frame) => {
    console.log('Broker reported error: ' + frame.headers['message']);
    console.log('Additional details: ' + frame.body);
  };

  stompClient.onWebSocketClose = () => {
    if (!appIsReadyStore.isLoading) {
      appIsReadyStore.setIsLoading(true);
    }
  };

  stompClient.activate();
}
