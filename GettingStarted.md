## OS Platform and Prerequisites ##

Korus is Tested on Windows XP and Linux Platforms. Since, it is a purely java based framework it should run on all the platforms supporting java.

JDK 1.5 and above

## Getting Started Quickly ##
This is a quick start step. Helps to understand the very basic way to get a simple program running using Korus. You can also see how the code is organized and how to write a Korus enabled program. You can easily change the code and see how Korus works. The intent is to provide a first-step-instant-learning. It is good to start with this and then go to the details as mentioned below. Download Sample.zip from [here](http://korus.googlecode.com/svn/trunk/Product/korus/example/Sample.zip)
This will contain

  1. korus.jar
  1. BuyProcess1.java
  1. BuyProcess2.java
  1. TestProcess.java
  1. sourcefiles
  1. run.bat (Windows environment)
  1. run.sh (linux/unix environment)

Execute the file _run.bat_ or _run.sh_ depending on your OS

## Getting Started in Detail ##
These are detailed set of instructions as well as internal aspects. It is recommended to go through this before writing "real" programs with Korus.

## 1. Getting Korus ##

**Korus is currently version 1.0.0**

Use this command to check out the latest **Stable** source code of the Project:
```
  $ svn checkout http://korus.googlecode.com/svn/tags/1.0.0 <your_directory_name>
```

Use this command to anonymously check out the latest project source code:

```
  $ svn checkout http://korus.googlecode.com/svn/trunk/ <your_directory_name>
```

You will also need a JDK installed on your system, version 1.5 or later required. The svn trunk consists of the following directory layout:
```

   +--- Plan
   |
   +--- Product // contains API Files
   |      |
   |      +--- korus
   |      |       |
   |      |       +--- build      // Build Scripts
   |      |       +--- dst        
   |      |       +--- jar        // Compiled Jar
   |      |       +--- javadoc    // Java Documentation
   |      |       +--- src        // Source Files
   |      |       +--- examples   // Example codes
   |      |       +--- properties // Property Files
   |      |
   |      +--- Adapter // adapter codes for other languages.
   |
   +--- Support  // Contains the supporting files
   |      |
   |      +--- ant
   |      
   +--- Test // contains Test Classes
   |      |
   |      +--- lib        // Libraries needed by Test Classes
   |      +--- report
   |      +--- src        // Source Files for Test Classes
   |      +--- files      // Files needed by the Test Classes
   |
   +--- Copyright.txt 
   |
   +--- GPL_LICENSE.txt
   |
   +--- README.txt 

```

### 1.2 Install Ant ###

Install latest version of ant from [here](http://ant.apache.org/).

_Note - Maven version is coming soon in the next stable release_


### 1.3 Building Korus ###

#### 1.3.1 Windows ####

Go to the Product --> korus Folder

```
 > cd build
 > ant clean
 > ant compile
 > ant jar
```

This will generate the jar in your Product --> korus --> jar Folder. You can then either put the JAR of Korus to your preferred directory or put the jars on the system CLASSPATH.

If you prefer to the source edition, you can find it in the Product --> korus --> src Folder.

For only using the compiled classes you need to :

```
 > cd build
 > ant clean
 > ant compile
```

This will compile the Java classes and into the Product --> korus --> bin Folder.

For compiling test classes.

```
 > cd build
 > ant test_compile
```

Compile it using the java compile commands and you can start using it in your Project.

Or else you can also use java command-line options
**Example:** java -classpath C:\korus\Test\bin;C:\korus\Product\korus\jar\korus.jar com.impetus.labs.korus.test.pipeline.PipelineTest

#### 1.3.2 Unix ####

Go to the Product --> korus Folder

```
 $ cd build
 $ ./ant.sh clean
 $ ./ant.sh compile
 $ ./ant.sh jar
```

This will generate the jar in your Product --> korus --> jar Folder. You can then either put the JAR of Korus to your preferred directory or put the jars on the system CLASSPATH.

If you prefer to the source edition, you can find it in the Product --> korus --> src Folder.

For only using the compiled classes you need to :

```
 $ cd build
 $ ./ant.sh clean
 $ ./ant.sh compile
```

This will compile the Java classes and into the Product --> korus --> bin Folder.

For compiling test classes.

```
 > cd build
 > ./ant.sh test_compile
```

Compile it using the java compile commands and you can start using it in your Project.

Or else you can also use java command-line options
**Example:** java -cp '/home/user/svn\_repo/korus/Test/bin:/home/user/svn\_repo/korus/Product/korus/jar/korus.jar' com.impetus.labs.korus.test.pipeline.PipelineTest

_**More detailed inline documentation of java classes can be found in Product --> korus --> javadoc or [here](http://korus.googlecode.com/svn/trunk/Product/korus/javadoc/index.html)**_

---


## 2. Configuring Korus ##

**Step 1.** Set the enviorment variable KORUS\_HOME pointing to the Korus installation directory.
Example: KORUS\_HOME=Product/korus

**Step 2. (Optional)** In the KORUS\_HOME/properties edit the config file korus.properties. Change the properties to fine tune the Korus runtime as per the system configuration

**If the properties file is not configured Korus runs with default properties.**

---


## 3. Using Korus ##
This section explains various ways in which the Korus framework can be used. All of them are explained by means of examples.

  1. **Korus core API's**
> > Details can be found [here](http://code.google.com/p/korus/wiki/AsynchronousDistributedProgrammingMode)<br />
  1. **Parallel programming constructs.**<br />
    * `parallel_for` : A parallel version of a for loop, more details can be found [here](http://code.google.com/p/korus/wiki/ParallelFor)<br />
    * `pipeline` : It allows you to model your sequential independent task in parallel, more details can be found [here](http://code.google.com/p/korus/wiki/Pipeline)<br /><br />
  1. **Cross language adapter. Calling Korus from another language environment**
> > Details can be found [here](http://code.google.com/p/korus/wiki/CrossLanguageAdapter)<br />
  1. **How to create a web application using korus**
> > Details can be found [here](http://code.google.com/p/korus/wiki/WebApplicationExecution)


---


## Case Study ##
We are working to write a complex case study from requirements to implementation code on how Korus can be used in real world complex scenarios.
The details will be updated here soon.