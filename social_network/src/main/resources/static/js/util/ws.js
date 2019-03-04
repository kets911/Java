import SockJS from "sockjs-client"
import { Stomp } from "@stomp/stompjs"

let stompClient = null;
const handlers = [];

// function setConnected(connected) {
//     $("#connect").prop("disabled", connected);
//     $("#disconnect").prop("disabled", !connected);
//     if (connected) {
//         $("#conversation").show();
//     }
//     else {
//         $("#conversation").hide();
//     }
//     $("#greetings").html("");
// }

export function connect() {
    const socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.debug = () => {};
    stompClient.connect({}, frame => {
        // setConnected(true);
        // console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/activity', message => {
            console.log(message.body)
            console.log(message.id)
            handlers.forEach(handler => handler.JSON.parse(message.body));
        });
    });
}

export function addHandler(handler) {
    handlers.push(handler);
}

export function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    // setConnected(false);
    console.log("Disconnected");
}

export function sendMessage(message) {
    stompClient.send("/app/changeMessage", {}, JSON.stringify(message));
}
