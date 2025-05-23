# Introduction to mutation testing

This repository contains the materials for my 'Introduction to mutation testing' workshop in Java.

### Requirements

* Java 21
* Maven
* An IDE of your choice

### About this workshop

This workshop uses [PITest](https://pitest.org) as the mutation testing tool of choice. 

### Running the tests

Before you start a mutation testing run, make sure the tests themselves pass by running

`mvn clean test`

When they pass, run mutation tests using

`mvn org.pitest:pitest-maven:mutationCoverage`

and inspect the results in `/target/pit-reports`.

### Delivery

By all means, experiment with the materials in this repository yourself. If you'd like me to run a mutation testing workshop in your company or at your event, send me an email at bas@ontestautomation.com and we'll work it out.

The workshop takes approximately 3-4 hours, but can be extended or shortened to fit your availability.