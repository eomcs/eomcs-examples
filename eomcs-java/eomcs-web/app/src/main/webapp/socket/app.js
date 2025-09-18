const express = require('express');
const { createServer } = require('node:http');
const { join } = require('node:path');
const { Server } = require('socket.io');

const port = 3000;
const app = express();
const server = createServer(app);
const io = new Server(server);
const rooms = new Map();

app.get('/', (req, res) => {
  res.sendFile(join(__dirname, 'index.html'));
});

io.on('connection', (socket) => {
  console.log('a user connected');
  let roomNo;

  socket.on('enter', (arg1) => {
    roomNo = arg1;

    let messages = rooms.get(roomNo);
    if (!messages) {
      messages = [];
      rooms.set(roomNo, messages);
    }

    console.log(`${roomNo} 번 방에 입장!`);
    socket.join(`${roomNo}`);
  });

  socket.on('message', (msg) => {
    let messages = rooms.get(roomNo);
    messages.push(msg);
    io.to(`${roomNo}`).emit('message', msg);
  });

  socket.on('disconnect', () => {
    console.log('user disconnected');
    let messages = rooms.get(roomNo);
    console.log(messages);
  });
});

server.listen(port, () => {
  console.log(`${port}번 포트에서 서버 시작했음!`);
});

