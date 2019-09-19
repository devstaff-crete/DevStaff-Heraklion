# Devstaff Meetup 2 - An Intro to Node.js

## Goal: 
This walkthrough will get you started with the tooling required to write **server-side** Javascript code. Yes, you read that right. 

## About Node.js

Node.js is a runtime system for creating server-side applications and it's best known as a popular means for javaScript coders to build real-time Web APIs. It has become really popular among web developers and it's gaining adoption by large companies like IBM, Microsoft, Yahoo!, Walmart, Groupon, SAP, LinkedIn, PayPal and more.

We'll look closer into it during our meetup, but it's a good idea to have some stuff prepared beforehand, so you're not totally lost in case you haven't touched it before.

## Prerequisites

There are *very few* things you will need for this meetup.

1. The most important prerequisite is to have some kind of programming experience. Any kind and in any language.
2. Basic knowledge of Javascript. Really basic. We are not going to build a very complicated application, so you will only need a basic understanding of the language behind Node.js, for now. Find some examples below.
3. A console
4. A good code editor 

## Basic javascript examples

Here you can check some basic javascript examples in order to get more familiar with the language.

### Variables
```javascript
var hello = 'Hello world'
var myInt = 12
var myArray = ['lemon', 'orange', 'apple']
var myObj = {name: 'Nikos', age: 33 }
```

### Functions
```javascript
var sayHello = function() {
    console.log('Hello again');
};
sayHello();
```

This is everything you will need for now but you can read more:
* https://autotelicum.github.io/Smooth-CoffeeScript/literate/js-intro.html
* Pull request to add more :)

## Take me to the client-side

Before starting to code on the server-side, we can run some code examples client-side (in a browser) in a very similar way. One of the components Node.js was built on, is the V8 javascript engine, used by the Google Chrome web browser. You can start playing with javascript immediately using your Chrome browser following the steps below:

* Open a Chrome browser
* Open developer tools (Cmd+Opt+I or Ctr+Shift+I)
* Go to Console tab
* Start typing:
```javascript
var hello = 'Hello world';
console.log(hello);
var sayHello = function() {
	console.log('Hello again');
}
sayHello();
```

## Getting started

If you feel the Javascript language is starting to make sense, we can go on and install the very first tools necessary for our Node.js web application!!

### Node.js

In order to install Node.js visit https://nodejs.org/ and download a precompiled binary package (available for Linux / Mac OS X / Windows).

The process will install both node and npm. Npm is the Node Package Manager which facilitates installs of additional packages for node.js.

When you're done check the installation by typing `node` in the command line, which will open a node javascript session:
```bash
node
> console.log('Hello node people');
Hello node people
undefined
>
```
You can use this console pretty much in the same way you used Chrome's console.

### Alternative (cooler!) way

Node.js can also be installed through nvm. Its initials mean Node Version Manager and the project contains scripts to manage multiple node versions. Go to the [github page of nvm](https://github.com/creationix/nvm) if you want to be awesome and use nvm.

Install nvm:
```bash
curl -o- https://raw.githubusercontent.com/creationix/nvm/v0.26.1/install.sh | bash
```

Then install the version of node you want:
```bash
nvm install v0.12.7
```

or get the latest stable version with:
```bash
nvm install stable
```

The above commands should be enough to install node.js.

### Express.js

Express is a minimal and flexible Node.js web application framework that provides a robust set of features for web and mobile applications.

In order to install
```bash
mkdir devstaff-api-node
cd devstaff-api-node
npm init
```
The last command will prompt you for a number of things such as the name and version of your application. For now, you can simply hit RETURN to accept the defaults for most of them.

Now install Express in the app directory and save it in the dependencies list:
```bash
npm install express --save
```

In the devstaff-api-node directory, create a file named index.js (depends on your choices after npm init) and add the following code to it:

```javascript
var express = require('express');
var app = express();

app.get('/', function (req, res) {
  res.send('Hello World!');
});

var server = app.listen(3000, function () {
  var host = server.address().address;
  var port = server.address().port;

  console.log('Example app listening at http://%s:%s', host, port);
});
```

The app starts a server and listens on port 3000 for connection. It will respond with “Hello World!” for requests to the root URL (/). For every other path, it will respond with a 404 Not Found.

Run the app with the following command:
```bash
node index.js
```

Then, load http://localhost:3000/ in a browser to see the output.

### Useful modules
There are some modules we (almost) never work without and it's a good idea to add them to our project.
```bash
npm install --save lodash moment async
```

You can find more about them:
* https://lodash.com/docs
* http://momentjs.com/docs/
* https://github.com/caolan/async

You can also browse more modules at https://www.npmjs.com/

### Postman addon

In order to test your API easily you can install a Chrome addon. Using Postman you will be able to build and store the right requests to test your API.

Learn more and install here: https://chrome.google.com/webstore/detail/postman/fhbjgbiflinjbdggehcddcbncdddomop


## Why the fuss with Node.js

We believe this is something we should discuss during the meetup, however here are some bullet points to get your attention

* Asynchronous, event-driven. For example, a HTTP call will be done in an asynchronous fashion that will not block the main thread of execution. On completion an event will be triggered and a callback function will be executed as a consequence. This is a proven architecture which someone can use and create scalable systems.
* Powered by V8. JavaScript is not interpreted but instead it is compiled into machine code that can be optimized on the fly.
* Performance without a complex approach.
  * Fact 1. PayPal reported: double the number of requests per-second and reduced response time by 35% or 200 milliseconds.
  * Fact 2. On Black Friday the WalMart servers didn’t go over 1% CPU utilisation and the team did a deploy with 200,000,000 users online.
* Unified API: Node.js combined with a browser, a document DB (such as MongoDB or CouchDB) and JSON offers a unified JavaScript development stack. JavaScript was on the client-side. Now it is everywhere and it looks like it will be used for a long time ahead. You also have the Atwood's Law, "any application that can be written in JavaScript will eventually be written in JavaScript".
* There are many more bullet points which are off-topic for now and will be discussed at the meetup.


## Why RESTful API

Finally, the reason why we decided the project of our choice to be a simple RESTful API is to hide any complexity coming from template engines and views, html manipulation and even databases. This way we'll stay focused on the more fundamental aspects of this new way of development.


## More

If you want to learn more join us at our next meetup.

* https://nodejs.org/
* http://expressjs.com/
* Pull request to add more
