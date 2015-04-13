Describes the technical overview of the Korus below
## Thread less Concurrency ##

![http://korus.googlecode.com/svn/trunk/Support/images/korus.jpg](http://korus.googlecode.com/svn/trunk/Support/images/korus.jpg)

**Figure: Comparison between Standard (multi-threaded) and Korus Request Execution**

The figure above is divided into two parts. The first part shows the Standard multi-threaded execution and the second part shows a Korus execution. Let us first take the first part and see how a standard request is being typically served.


### 1. Standard Execution ###

Whenever a request is being received it is served using a thread (most probably from a Threadpool). A sequence of tasks are being performed to serve a complete request, usually in the same worker thread and response is generated. The tasks are generally sequential in nature and every time the task is performed probably a new thread  will take the control.


### 2. Korus Execution ###

Korus-process - Single Unit of Code Execution which is stateless in nature. Extremely light weight as compared to threads.

The sub-tasks of a Standard Execution can be transformed into Korus Processes which can now execute independently. Here, we have one(or very few) threads serving the requests. Context switching happens between different pieces of code instead of Thread context switching. We all are familiar that context switching threads is a heavy operation and this overhead is completely eliminated in this case.


**Table: Comparison between Traditional Thread Model and Korus - Code Context Switching**

| **Traditional Thread Model** | **Korus - Code Context Switching** |
|:-----------------------------|:-----------------------------------|
| One Thread manages one request | Very few  (1-3) execution threads |
| Threads are allocated from a server-managed thread pool | Korus does the context switching between pieces of Code |
| Memory consumption increases with the number of connections | Reduces the context switching overhead to near zero|
| Problems with deadlock, starvation, race condition etc.| No threads, No deadlocks and thread related issues |

<br />

---

<br /><br />
## Logical View ##


![http://korus.googlecode.com/svn/trunk/Support/images/logical.jpg](http://korus.googlecode.com/svn/trunk/Support/images/logical.jpg)

**Figure: Displaying logical view of Korus**

In the Figure above, you can see two Korus Nodes each of which is having an instance of a JVM and a Korus Runtime. Each run-time can execute Processes asynchronously. On top of Korus nodes we have a grid manager to manage a Korus grid. We can also create customized constructs as per our requirements using Korus.

**Note - The GridManager is under construction right now. Not a part of the current stable build 1.0.0**

Korus is built in Share Nothing Logical & Deployment Architecture. Inter-process communication is done through asynchronous message passing. Process can be created on any node used for the application deployment. Messages are passed using message queues of processes.