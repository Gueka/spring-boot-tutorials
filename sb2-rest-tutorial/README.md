# Spring boot 2 REST service tutorial
A tutorial repository to show how to integrate docker with an spring boot for a REST service

Use this project as a base template to build and customize it as it suits you. Also, I’m assuming it’s not your first time with java, but you can ask me whatever you want.

If you want more information [take a look into this post](https://medium.com/devopslinks/starting-point-learning-rest-service-63a4aa70fa7b) where I go into detail on each part of this project.

## Introduction
We are in a practical journey towards a microservice architecture. [In the previous post](https://medium.com/devopslinks/first-stop-a-soap-service-dd3498ad338d), I talked about SOAP service and how to create one with spring boot 2. Today we will learn how we can move towards microservices architecture by using a lighter, fast to parse JSON that will allow more services to talk to each other introducing REST. Also, for our business layer (promotion decisions) we will implement and explore a rule engine library called easy rule and learn how we move our previous business logic to this rule engine to enable extensibility for future rules.

## So, What is REST?
Web services are a standardized way to communicate through messages on the internet as mailing is a way to communicate through letters in the real world, as the mailman is the communication protocol that delivers letters to the specific address (URL). In a previous post, we learn that SOAP service is pre-defined letters, like a legal notice of claim where it specifies how data that should be structured and sent in an XML file format (“eXtensible Markup Language). While in the other hand we have REST that is more like a normal letter with instructions where the envelope type represent the operation needed to be executed in a JSON file format (“JavaScript Object Notation”). REST stands for “REpresentational State Transfer” as a set of operations commonly used with web transactions

## What you will learn here
* How to use Spring boot 2 to create a REST application.
* REST principles as we already cover
* How to make your unit and integration tests
* Use of JUnit 5 with parameterized tests.
* Package your service for delivery with Docker.
* How to handle properties production ready.

## Pre-requirements
* Get Java 8
* Have Gradle 4+
* Git installed
* Docker up and running
* Lastly, an IDE of your choice; I’m currently using Visual Studio Code
