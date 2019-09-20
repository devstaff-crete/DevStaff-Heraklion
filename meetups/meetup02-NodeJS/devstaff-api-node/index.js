var express = require('express');
var bodyParser = require('body-parser');
var moment = require('moment');
var _ = require('lodash');
var app = express();

var dbCollection = [
  {id: '0', title: 'my first task', cdate: '2015-09-08T18:39:37+03:00', desc: 'I have to finish this first task'},
  {id: '1', title: 'another task', cdate: '2015-09-09T20:41:37+03:00', desc: 'I can skip this one'},
  {id: '2', title: 'get beers', cdate: '2015-09-10T08:39:37+03:00', desc: 'Do not forget the beers'}
];

app.use(bodyParser.json()); // support json encoded bodies
app.use(bodyParser.urlencoded({ extended: true })); // support encoded bodies

app.get('/', function (req, res) {
  res.send('Hello World!');
});

app.get('/tasks', function (req, res, next) {
  res.json(dbCollection);
});

app.post('/tasks', function (req, res, next) {
  var id = parseInt(_.last(dbCollection).id) + 1;
  id = id.toString();
  var task = {
    id: id,
    title: req.body.title || '',
    desc: req.body.desc || '',
    cdate: moment().format()
  };
  dbCollection.push(task);
  res.json(task);
});

app.get('/tasks/:task_id', function (req, res, next) {
  var id = req.params.task_id;
  res.json(_.find(dbCollection, {id: id}));
});

app.put('/tasks/:task_id', function (req, res, next) {
  var id = req.params.task_id;
  var task = _.find(dbCollection, {id: id});
  task.title = req.body.title || task.title;
  task.desc = req.body.desc || task.desc;   
  res.json(task);  
});

app.delete('/tasks/:task_id', function (req, res, next) {
  var id = req.params.task_id;
  var removedTask = _.pullAt(dbCollection, _.findIndex(dbCollection, {id: id}));
  res.json(removedTask);
});

var server = app.listen(3000, function () {
  var host = server.address().address;
  var port = server.address().port;

  console.log('Example app listening at http://%s:%s', host, port);
});
