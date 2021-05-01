const WebSocket = require("ws");

const host = "127.0.0.1";
const port = 8080;

const wss = new WebSocket.Server({ host: host, port: port });

wss.on("connection", function connection(ws) {
  ws.on("connection", () => {
    console.log("User connected");
  });

  ws.on("message", function incoming(message) {
    console.log("Received: %s", message);
    ws.send(message);
  });

  ws.on("close", () => {
    console.log("User disconnected");
  });
});

console.log(`Started websocket server on ${host}:${port}`);
