Here's a brief overview on the usage and definition of `ParallelFor` construct

## Introduction ##
`ParallelFor` is a parallel version of a for loop which executes the loop parallely and tries to utilize more number of cores for lower turnaround time. A `ParallelFor` loop takes in a range which determines the number of times the for loop needs to run and the grainsize (which determines the number of threads Korus will allocate).

## Example : Factorial ##
Suppose we want to calculate a factorial of a very big number. Using `ParallelFor` will not be as simple as to just replacing the for construct by a `parallelFor`. But its not that difficult too. Just by tweak the program a little bit here and there, we will have `parallelFor` working. A simple program calculate a factorial could go like this :

```
  public class SerialFactorial 
  {
      public static void main(String[] args) 
      {
          for (i=1; i<=10000; i++) 
          {
             // write the logic to calculate factorial
             ...
          }
      }
  }
```

If we add the logic inside the loop and the necessary imports. The code will look like this:

```
import java.math.BigInteger;

public class SerialFactorial 
{
   public static void main(String[] args) 
   {
      // Set the fact variable to 1 initially.
      BigInteger result = BigInteger.ONE;
      int i = 1;

      for (i=1; i<=10000; i++) 
      {
         // write the logic to calculate factorial
         result = result.multiply(BigInteger.valueOf(i)); 
      }
   }
}
```

This program will run sequentially and only a single core will be utilized no matter how many core machine we put in to improve the performance . Suppose we want to run this program so that it utilize all the cores, we need to perform certain changes.

  1. Create a class containing this logic which:
  * extends `ParallelTask`,
  * implement the constructor
  * overrides a service() and summarize() methods
  * iterate the loop from begin of the range through end of the range and set the result

```
public class ParallelFactorial extends ParallelTask {
   // implement the constructor.
   public ParallelFactorial(Parallel parallel, BlockedRange range)
	{
		super(parallel, range);
	}

   // implement a service method passing the BlockedRange
   public void service(BlockedRange range) 
   {
      // iterate the loop from begin to end over a given range 
      for (int i = range.getBegin(); i <= range.getEnd(); i++) 
      {
         // write the logic to calculate factorial
         ...
      }

      // set the intermediate result in a variable to be returned 
      // for each iteration
      getParallel().setResult(this,fact);
   }

   // implement a summarize method.
   public Object summarize(List<Object> intermediateResults)
   {

      // iterate over the intermediate results and summarize the results 
      for (Iterator iter = intermediateResults.iterator(); iter.hasNext();) 
      {
         // logic to calculate the final result
         ...
      }

      // return the final result
      return finalfact;
   }
} 

```
If we add the logic inside the loop and the necessary imports. The code will look like this:

```
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

import com.impetus.labs.korus.constructs.parallelFor.Parallel;
import com.impetus.labs.korus.constructs.parallelFor.ParallelTask;
import com.impetus.labs.korus.util.BlockedRange;

public class ParallelFactorial extends ParallelTask
{
	private static BigInteger finalfact = BigInteger.ONE;
	
	public ParallelFactorial(Parallel parallel, BlockedRange range)
	{
		super(parallel, range);
	}

	// implement a service method.
	public void service(BlockedRange range)
	{
		BigInteger fact = BigInteger.ONE;

		// Iterate the loop from begin to end over a given range 
		for (int i = range.getBegin(); i <= range.getEnd(); i++)
		{
			fact = fact.multiply(BigInteger.valueOf(i));
		}
		
		getParallel().setResult(this,fact);

	}
	
	// implement a summarize method.
	public Object summarize(List<Object> intermediateResults)
	{
		BigInteger factorial = BigInteger.ONE;
		
		// Iterate over the intermediate results and summarize the results
		for (Iterator iter = intermediateResults.iterator(); iter.hasNext();)
		{
			factorial = (BigInteger) iter.next();
			finalfact = finalfact.multiply(factorial);
		}
		// Return the final result
		return finalfact;
	}

}
```
Create a class which will use this class and to use it we add the following code.
```
// Create an object of the construct to be used
Parallel parallel = new Parallel();

// specify begin, end and grainSize according to the machine
BlockedRange range= new BlockedRange(1,10000,1250);

// Create an object of a class extending ParallelTask Class
ParallelFactorial pf = new ParallelFactorial(parallel, range);   

// pass the object to the parallelFor construct
parallel.parallelFor(pf);

//Get the finalResult of execution
Object obj = parallel.getResult();
```

If we add the logic inside the loop and the necessary imports. The code will look like this:

```
import com.impetus.labs.korus.constructs.parallelFor.Parallel;
import com.impetus.labs.korus.util.BlockedRange;

public class ParallelFactorialTest 
{
   // Simple demo to demonstrate ParallelFor construct 

   public static void main(String[] args) 
   {
      // Create an object of the construct to be used
      Parallel parallel = new Parallel();

      // specify begin, end and grainSize according to the machine
      BlockedRange range= new BlockedRange(1,10000,1250);

      // Create an object of a class extending ParallelTask Class
     ParallelFactorial pf = new ParallelFactorial(parallel, range);

     // pass the object to the parallelFor construct
     parallel.parallelFor(pf);
     
     //Get the finalResult of execution
     Object obj = parallel.getResult();
     
   }
} 
```

_**See the performance benchmark of the test [here](http://code.google.com/p/korus/wiki/PerformanceBenchmark#ParallelFor)**_