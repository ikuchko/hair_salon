# HAIR SALON

##### Epicodus friday's code review project using Java and Postgres, 01.22.2016

##### Illia Kuchko

## Description
Hair salon application, created to collect and manage data about stylists and their clients.

## Setup

Clone this repository:
```
$ cd ~/Desktop
$ git clone https://github.com/ikuchko/hair_salon.git
$ cd hair_salon
```

Open terminal and run Postgres:
```
$ postgres
```

Open a new tab in terminal and create the `hair_salon` database:
```
$ psql
$ CREATE DATABASE hair_salon;
$ psql hair_salon < hair_salon.sql
```

Navigate back to the directory where this repository has been cloned and run gradle:
```
$ gradle run
```

##Database information
In PSQL:
```
CREATE DATABASE hair_salon
CREATE TABLE stylists (id serial PRIMARY KEY, first_name varchar (20), last_name varchar (20))
CREATE TABLE clients (id serial PRIMARY KEY, first_name varchar (20), last_name varchar (20), phone_number varchar (18), stylist_id int REFERENCES stylists (id))
```

## Legal

Copyright (c) 2015 Illia Kuchko

This software is licensed under the MIT license.

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
