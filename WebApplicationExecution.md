## Korus Deployment in Web Application Execution Environment ##

We have experimented the concept to use Korus as an execution engine for a web application and the performance benchmarks can be found [here](http://code.google.com/p/korus/wiki/PerformanceBenchmark#Performance_Benchmark_of_Korus_in_Web_Application_Execution_Environment). Right now its not in stage to share. We are working on it and will be updating more details very soon.


![http://korus.googlecode.com/svn/trunk/Support/images/customDeployment.jpg](http://korus.googlecode.com/svn/trunk/Support/images/customDeployment.jpg)

**Figure: Korus Custom deployment in web application scenario.**

Here a Sample Scenario has been shown of how Korus can be used in an HTTP environment.
Korus Processes are Replicated and Deployed on three nodes which are managed by a Distribution Manager. A Web Server is placed in front of the Distribution Manager to accept HTTP Requests.

Thus, whenever a request is received by a Webserver it passes it to the Distribution. The Distribution manager chooses the best available node for processing the request. The request is either processed entirely on a Node or partially in parts by different nodes and response is sent back to the Webserver.