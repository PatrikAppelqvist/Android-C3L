const express = require('express');
const bodyParser = require('body-parser');
const url = require('url');

const userRepository = require('./UserRepository.js');
const messageRepository = require('./MessageRepository.js');
const urlHelper = require('./urlHelper.js');

const app = express();

app.use(bodyParser.json());

app.get('/users', (req, res) => {
  const urlObj = url.parse(req.url);
  const queryParams = urlHelper.getQueryParamsObj(urlObj.query);
  const users = userRepository.getUsers(queryParams.q);

  res.status(200).send(users);
});

app.get('/users/:id', (req, res) => {
  const id = req.params.id;
  const user = userRepository.getUser(id);

  if(user) {
    return res.status(200).send(user);
  }
  
  res.status(404).send("No user found with that id");
});

app.post('/users', (req, res) => {
  const user = req.body;
  const addedUser = userRepository.addUser(user);

  if(addedUser) {
    res.setHeader('location', '/users/' + addedUser.id);
    return res.status(200).send(addedUser);
  }
  
  res.status(400).send("Could not add user: \n\n" + JSON.stringify(user, null, 4));
});

app.put('/users/:id', (req, res) => {
  const user = req.body;
  const id = req.params.id;
  userRepository.updateUser(id, user);

  res.status(200).send();
});

app.delete('/users/:id', (req, res) => {
  const id = req.params.id;
  userRepository.removeUser(id);

  res.status(200).send();
});

app.post('/messages', (req, res) => {
  const message = req.body;
  messageRepository.addMessage(message);

  res.status(200).send("Message added");
});

app.get('/messages', (req, res) => {
  const messages = messageRepository.getMessages();
  console.log(messages);
  res.status(200).send(messages);
});

app.listen(3000, () => {
  console.log('User crud app, listening on port 3000')
});