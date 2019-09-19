# Meetup 05 - Software Testing

[![Join the chat at https://gitter.im/devstaff-crete/meetup05-Testing](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/devstaff-crete/meetup05-Testing?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Here you can find the slides of the presenation in different formats plus the implementation of the code kata.

## The String Calculator Code Kata

The repository contains the implementation of the code kata until the 2nd bullet.

Practicing TDD on the [string calculator code kata](http://osherove.com/tdd-kata-1/).

### Rules and specs

Before you start:

* Try not to read ahead.
* Do one task at a time. The trick is to learn to work incrementally.
* Make sure you only test for correct inputs. there is no need to test for invalid inputs for this kata

#### String Calculator Spec
* Create a simple String calculator with a method int Add(string numbers)
    *  The method can take 0, 1 or 2 numbers, and will return their sum (for an empty string it will return 0) for example ```""``` or ```"1"``` or ```"1,2"```
    *  Start with the simplest test case of an empty string and move to 1 and two numbers
    *  Remember to solve things as simply as possible so that you force yourself to write tests you did not think about
    *  Remember to refactor after each passing test
* Allow the Add method to handle an unknown amount of numbers
* Allow the Add method to handle new lines between numbers (instead of commas).
    *  the following input is ok:  ```"1\n2,3"```  (will equal 6)
    *  the following input is NOT ok:  ```"1,\n"``` (not need to prove it - just clarifying)
* Support different delimiters
    *  to change a delimiter, the beginning of the string will contain a separate line that looks like this: ```"//[delimiter]\n[numbers…]"``` for example ```"//;\n1;2"``` should return three where the default delimiter is ';' .
    *  the first line is optional. all existing scenarios should still be supported
* Calling Add with a negative number will throw an exception "negatives not allowed" - and the negative that was passed.if there are multiple negatives, show all of them in the exception message.

Stop here if you are a beginner. Continue if you can finish the steps so far in less than 30 minutes.

* Numbers bigger than 1000 should be ignored, so adding 2 + 1001  = 2
* Delimiters can be of any length with the following format:  ```"//[delimiter]\n"``` for example: ```"//[***]\n1***2***3"``` should return 6
* Allow multiple delimiters like this:  ```"//[delim1][delim2]\n"``` for example ```"//[*][%]\n1*2%3"``` should return 6.
* make sure you can also handle multiple delimiters with length longer than one char

## Getting Started

You need an installation of node.js to run the project. I suggest to install one using [nvm](https://github.com/creationix/nvm).
After the installation of node, running the following command while on the project root directory should get you started.
```javascript
npm install
```

Grunt is used for automation and the following tasks are present:
* `grunt jshint`: Static analysis of the project code with [jshint](https://github.com/jshint/jshint). In order to run this step you must do a ```npm install -g jshint``` first.
* `grunt jscs`: Style check of the project code with [jscs](https://github.com/jscs-dev/node-jscs). It uses the [google code style](https://google-styleguide.googlecode.com/svn/trunk/javascriptguide.xml) as a preset.
* `grunt mochaTest`: Execute the test suite of the project.
* `grunt`: Run all of the above. When one of the tools returns an error, it will stop.

## License
Copyright (c) 2015 devstaff-crete

Licensed under the GPLv3 license.
