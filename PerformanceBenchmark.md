**Beyond a quick overview of the numbers below, it is recommended to first have a look at the Getting Started Guide before for a more deeper understanding**

Describes the performance benchmarks below for the tests executed using Korus in Web Application Execution Environment and using Korus Constructs i.e. `parallel_for` & `pipeline`.


  * #### [Korus in Web Application Execution Environment](http://code.google.com/p/korus/wiki/PerformanceBenchmark#Korus_in_Web_Application_Execution_Environment) ####
  * #### [Korus Constructs](http://code.google.com/p/korus/wiki/PerformanceBenchmark#Korus_Constructs) ####
    * #### [ParallelFor](http://code.google.com/p/korus/wiki/PerformanceBenchmark#ParallelFor) ####
      * #### [Factorial Program](http://code.google.com/p/korus/wiki/PerformanceBenchmark#Factorial_Program) ####
      * #### [Area Under Curve](http://code.google.com/p/korus/wiki/PerformanceBenchmark#Area_Under_Curve) ####
      * #### [Spearman's rank correlation coefficient](http://code.google.com/p/korus/wiki/PerformanceBenchmark#Spearman's_rank_correlation_coefficient) ####
    * #### [Pipeline](http://code.google.com/p/korus/wiki/PerformanceBenchmark#Pipeline) ####
      * #### [Parallel Text Transformer Program](http://code.google.com/p/korus/wiki/PerformanceBenchmark#Parallel_Text_Transformer_Program) ####



## Korus in Web Application Execution Environment ##
_Please have a look at the How to Use section in the Getting Started Guide for more details of the Web Application Execution. The source code for these is currently **not** provided with the svn trunk. It will be added soon._

### 1. Traditional Application (3 node Tomcat cluster) ###

|Users|	Number of Requests|	Average Response Time (in milliseconds)|
|:----|:------------------|:---------------------------------------|
|400|	100000|	1073|
|800|	94000|	2201|

### 2. Using Korus (custom Korus grid deployment) ###

|Users|	Number of Requests|	Average Response Time (in milliseconds)|
|:----|:------------------|:---------------------------------------|
|400|	127000|	651 |
|800|	130000|	1108 |
|1200|	133000|	1312 |
|1600|	131000|	1512 |
|2000|	134000|	1744 |



---


## Korus Constructs ##

_Please have a look at the How to Use section in the Getting Started Guide for more details of Korus constructs. The source code for these are provided with the svn trunk._


# `ParallelFor` #

### Factorial Program ###
Java code to calculate factorial of a large number (40000!). Compared the serial as well as parallel version.

### 1. Testing `ParallelFor` Construct Performance on Dual Core Machine ###

#### 1.1 Program to Calculate Factorial Program using standard java `for` loop ####

|S.No.|Time in milliseconds|
|:----|:-------------------|
|1.|13050|

#### 1.2 Program to Calculate Parallel Factorial Program using `parallel_for` construct of Korus Library ####

|S.No.|Grain Size|Time in milliseconds|
|:----|:---------|:-------------------|
|1.|40000|13019|
|2.|20000|7690|
|3.|10000|5126|
|4.|5000|4059|
|5.|2500|3626|
|6.|1000|3423|

### 2. Testing `ParallelFor` Construct Performance on Quad Core Machine ###

#### 2.1 Program to Calculate Factorial Program using standard java `for` loop ####

|S.No.|Time in milliseconds|
|:----|:-------------------|
|1.|8688|

#### 2.2 Program to Calculate Parallel Factorial Program using `parallel_for` construct of Korus Library ####

|S.No.|Grain Size|Time in milliseconds|
|:----|:---------|:-------------------|
|1.|40000|8688|
|2.|20000|4765|
|3.|10000|3101|
|4.|5000|2797|
|5.|2500|2578|
|6.|1000|2516|



---


### Area Under Curve ###

The trapezoidal rule is a numerical integration method to be used to approximate the integral or the area under a curve. This is often useful when an exact integral does not exist, can not easily be obtained, or is mathematically too time consuming for repetitious automated calculations.

One approach to obtain a numerical solution of an integral is to approximate the function with an nth order polynomial, since these are relatively simple to integrate. The choice of the order of the polynomial depends on the required accuracy (higher order generally results in a higher precision), and the number data points over the selected interval (higher order requires more data points).

Consider the function f(x). We want to calculate the area under the curve over the interval a ≤ x ≤ b:
```
         b
     I = ∫ y(x)dx
         a
```

### 1. Results on Dual Core Machine ###

#### 1.1 Program to Calculate Area under Curve using standard java `for` loop ####

|S.No| Time in milliseconds|
|:---|:--------------------|
|1.|17323|

#### 1.2 Program to Calculate Area under Curve using `parallel_for` construct of Korus Library ####

|S.No.|Grain Size|Time in milliseconds|
|:----|:---------|:-------------------|
|1.|1000000000|17363|
|2.|500000000|8870|
|3.|250000000|8074|
|4.|125000000|7861|
|5.|62500000|7736|


### 2. Results on Quad Core Machine ###

#### 2.1 Program to Calculate Area under Curve using standard java `for` loop ####

|S.No|Number of Threads| Time in milliseconds|
|:---|:----------------|:--------------------|
|1.|1 |26735|

#### 2.2 Program to Calculate Area under Curve using `parallel_for` construct of Korus Library ####

|S.No.|Grain Size|Time in milliseconds|
|:----|:---------|:-------------------|
|1.|1000000000|26728|
|2.|500000000|13562|
|3.|250000000|7266|
|4.|125000000|7172|
|5.|62500000|7125|


---


### Spearman's rank correlation coefficient ###

Spearman's rank correlation coefficient measure of the strength of the associations between two variables, without making any assumptions about the frequency distribution of the variables.

In principle, ρ is simply a special case of the Pearson product-moment coefficient in which two sets of data Xi and Yi are converted to rankings xi and yi before calculating the coefficient. But practically a simpler procedure is adopted and used to calculate ρ. Ranks are calculated based on available data, and the differences di between the ranks of each observation on the two variables are calculated.

Therefore ρ is given by:

> http://korus.googlecode.com/svn/trunk/Support/images/rho.JPG

where:

> di = xi − yi = the difference between the ranks of corresponding values Xi and Yi, and

> n = the number of values in each data set (same for both sets).

### 1. Results on Dual Core Machine ###

#### 1.1 Program to Spearman's rank correlation coefficient using standard java `for` loop (Data Size - 10000000) ####

| S.No | Time in milliseconds |
|:-----|:---------------------|
|1.|2982|

#### 1.2 Program to Spearman's rank correlation coefficient using `parallel_for` construct of Korus Library (Data Size - 10000000) ####

|S.No|Grain Size| Time in milliseconds|
|:---|:---------|:--------------------|
|1.|10000000|2985|
|2.|5000000|1626|
|3.|2500000|1579|


### 2. Results on Quad Core Machine ###

#### 2.1 Program to Spearman's rank correlation coefficient using standard java `for` loop(Data Size - 10000000) ####

|S.No| Time in milliseconds|
|:---|:--------------------|
|1.|2593|

#### 2.2 Program to Spearman's rank correlation coefficient using `parallel_for` construct of Korus Library (Data Size - 10000000) ####

|S.No|Grain Size| Time in milliseconds|
|:---|:---------|:--------------------|
|1.|10000000|2446|
|2.|5000000|1341|
|3.|2500000|798|


---


# `Pipeline` #

### Parallel Text Transformer Program ###

This involved writing an application which reads a Text file, transforms and writes it into another file. A serial as well as a parallel version were written. The parallel version utilized 3 cores for each of the reading, transforming and writing tasks. The parallel version was written using Korus.

<sup>*</sup> _**Time denoted here is the response time and not the execution time.**_

**Response Time:** The time in which the execution class gets the control back.
In Serial version of the program the execution class halts until the task is completed, while in Parallel version using Korus `pipeline` construct control gets back to the execution class with in very minimal time while the task is getting completed in back ground.

### 1. Results on Dual Core Machine ###
**Testing Pipeline Construct – File Size 9.3MB (approx)**
|S.No.|Program|Execution Time|Response Time|
|:----|:------|:-------------|:------------|
|1.|SerialReadTransformWrite|1720ms|1720ms|
|2.|ParallelReadTransformWrite|1625ms|47ms<sup>*</sup>|

### 2. Results on Quad Core Machine ###
**Testing Pipeline Construct – File Size 9.3MB (approx)**
|S.No.|Program|Execution Time|Response Time|
|:----|:------|:-------------|:------------|
|1.|SerialReadTransformWrite|1032ms|1032ms|
|2.|ParallelReadTransformWrite|1025ms|32ms<sup>*</sup>|