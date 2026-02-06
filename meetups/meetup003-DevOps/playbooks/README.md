# Meetup 03 - DevOps: from concept to movement to implementation

[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/devstaff-crete/meetup03-DevOps#?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

## Material - playbooks

1. playbook initialise
2. playbokk python pre reqs
3. playbook git
4. playbook directory structure
5. playbook nginx

# how to run

Best way to start is to alter the hosts file included, by using the ip of your server

### How to run a playbook

```
$ ansible-playbook initialise/initialise.yml -i hosts  --extra-vars "target=test"
```

## Contributing

Please submit all pull requests against the master branch. Thanks!

## Authors

**Thanassis Zografos**

+ http://ultraweb4u.com
+ tzografos@ultraweb4u.com


## Copyright and license

    The MIT License

    Copyright (c) 2014-2015 Thanassis Zografos

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
    THE SOFTWARE.

