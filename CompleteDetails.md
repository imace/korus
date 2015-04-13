### What is Korus ###
Korus is a parallel and distributed programming framework written in core Java. Traditionally such frameworks/platforms are heavy as well as complex in nature. When we started with the concept, one of the things we wanted to have was a very light weight, programmer intuitive and simple model. We also wanted to make sure to have a extremely scalable and very high performance framework in place.

Thus we started with this project with an aim to have the best of the simple core Java world and the best of the "conceptual models" in the programming world of Erlang/Haskel/Scala. For more details of the background of Korus please see [here](#Background.md).

**Korus is currently version 1.0.0**

  1. Korus is very simple to use , very simple to deploy (one single jar file), and very simple to understand.<br /><br />
  1. **No need to understand thread programming to use Korus. Korus provides thread-less concurrency by way of code-context-switching. Programmers only write Java functions.**<br /><br />
  1. In one line Korus is a set of functions calling each other asynchronously and passing messages to each other. Simply break up your problem in a set of functions and code it to the Korus specifications.<br /><br />
  1. In the world of Korus these functions are called as "Korus-processes" or simply "processes". One Korus process is one Java function in terms of coding. Korus process is nothing to do with operating system process. They are simply Java functions, but since they behave independent of each other we decided to call them "processes". In all the documentation for Korus, wherever you see the term process, it means a Korus process. <br /><br />
  1. Korus is based on Actor Design Pattern. One of first “pure” actor pattern frameworks in Java. For more details of Actor pattern see [here](http://en.wikipedia.org/wiki/Actor_model). <br /><br />

**Features**<br />
Korus basically has two broad category of features.
  * The core set of features which include developing application using Korus processes.
  * Korus-Addons,these include
    1. Parallel constructs like `parallel_for` loop, `pipeline` etc.<br />
    1. Connectivity to Korus from other programming languages.

### Getting Started ###
_The best way to start is by having a look at the [Getting started page](GettingStarted.md). if you want to have a very quick feel go to the Getting Started Quickly section in guide. It will take only 5 minutes to run the sample program if you have a JDK environment._

_You can also download the Korus binaries from the download section. It consists of sample programs, Java docs and korus.jar file_

### Background ###
While we were debating on the best way to approach this, we realized we already had knowledge of such a "conceptual model". We have been working quite a lot on various upcoming and non traditional programming languages who have their core designed in a way which makes them good candidate for parallel/distributed computing. e.g. Erlang, Scala, Haskel etc. One of the fundamental problems with these languages is that they are very difficult to program and extremely complex in nature. Another major hurdle is the industry acceptability to use these. Though there has been an increase in usage lately, but the overall percentage is very less. Lastly it is very difficult to find expertise in these language, hence a significant barrier to entry.

One of the biggest challenges the industry is facing today is achieving Performance, Scalability and Availability. We need to get optimal returns from the hardware we invest in. We need ways to effectively utilize and take advantage of the commodity and multicore available to us. We need a software stack which can make life easier too develop as well as to deploy and run in production.

The journey of Korus starts with this background.

### Technical Overview ###
For more details have a look [here](http://code.google.com/p/korus/wiki/TechnicalOverview)

### What can I do with Korus ###
  * Simply putting it, Korus is very useful if you are planning to write applications which need very high performance & scalability. The application can either be stand alone or distributed or parallel in nature. <br /><br />
  * Additionally, you can use the `parallel_for` and `pipeline` constructs to ease development .<br /><br />
  * You can write your own Parallel constructs similar to the `parallel_for` and `pipeline` by using the basic Korus API.<br /><br />
  * You can call Korus from other language environments.

### Guidelines and Best Practices ###
Guidelines and best practices to use Korus in optimal manner can be found [here](http://code.google.com/p/korus/wiki/GuidelinesAndBestPractices)

### Performance Benchmarks ###
We have evaluated Korus in various test scenarios and the results can be found [here](http://code.google.com/p/korus/wiki/PerformanceBenchmark)

### Short Term Roadmap ###

  * Implement the Concept of [Context Store](http://code.google.com/p/korus/wiki/ContextStore)
  * Have a maven based build mechanism
  * Provide out of the box Distributed & Grid based deployment
  * Provide support for connectivity with multiple language environments.Currently only Erlang is supported.

### Contributing To Korus ###
If you are interested in contributing to Korus, please contact or send the patches to the project owners. Since we have just started, we are keeping it this way. Once the momentum grows we will be going the complete open source model where anyone can send a patch and the checkins will be moderated.

### About Us ###
We are Impetus Labs or iLabs as we call it. iLabs is a R&D consulting division of Impetus Technologies. [(http://www.impetus.com)](http://www.impetus.com). iLabs focuses on innovations with next generation technologies and creates practice areas and new products around them. iLabs is actively involved working on High Performance computing technologies, ranging from distributed/parallel computing, GPU based software, Hadoop and related technologies. iLabs is also working on various other Open Source initiatives.