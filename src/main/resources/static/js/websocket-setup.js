let stompClient = null;

function connectWS() {
  const socket = new SockJS('/ws');
  stompClient = Stomp.over(socket);
  stompClient.connect({}, frame => {
    console.log('Connected: ' + frame);
  });
}

function subscribeToAnnouncements(courseId, callback) {
  stompClient.subscribe('/topic/announcement/' + courseId, message => {
    const data = JSON.parse(message.body);
    callback(data);
  });
}
