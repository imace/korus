Here's a brief introduction to the concept, usage and functionality of Pipeline construct.

## Introduction ##
Pipeline allows you to model your sequential independent task parallely. For example, a file transformation job can be modeled as 3 tasks of reading, transforming and writing the file. The tasks communicate to each other via one-way intermediate queues.

## Example : FileTransformationPipeline ##

Pipelining is very useful when the output of one task serves as an input to the succeeding task. Lets, understand this with help of an example. Suppose, we want to:

  1. Read a file form the disk.
  1. Transform the data in the file (Say, convert all the characters to uppercase).
  1. Write the file back to the disk .

In normal circumstances this will be performed sequentially. Lets understand this better with the help of a graphics.

http://korus.googlecode.com/svn/trunk/Support/images/pipeline_seq.JPG

As we can see that at any given time only one core is utilized properly and rest of the CPUs are idle. The performance of such programs can be substantially improved by using a Pipeline. The functionality of a Pipeline is illustrated below.

http://korus.googlecode.com/svn/trunk/Support/images/pipeline_parallel.JPG

Each pipelined operation is termed as a Task. A task is nothing but performing the entire operation into chunks. For example, instead of reading a complete file and then applying transformation to it. We will read a chunk of data and then apply transformation to that chunk and similarly write that chunk back to the disk. Once a chunk is been processed, its placed into an intermediate queue so that it can be now accessed by the consecutive tasks.

For achieving the above functionality, we need to define three Tasks extending PipelineTask.

#### 1. ReadTask ####
```
// Extend the PipelineTask
public class ReadTask extends PipelineTask {

   // Used to specify the end condition of execution
   public static final String END_OF_TASK = "~_~END_OF_Line~_~";

   // Override the service method present in PipelineTask
   public void service() 
   {
      // Point the file to be read 
      File f = new File("../files/testFile.txt");
     
      // Since this is the first Task to be executed, it will just have
      // one outputQueue and no inputQueue.
      PipelineQueue<Object> outputQ = this.getOutputQueue();
      try 
      {
         BufferedReader reader = new BufferedReader(new FileReader(f));
         String line = null;
         while ((line = reader.readLine()) != null) 
         {
            // read lines and add it to the Queue
            outputQ.add(line);
         }
         // to specify end condition of the execution.
         outputQ.add(END_OF_TASK);
      } catch (FileNotFoundException e) 
      {
         e.printStackTrace();
      } catch (IOException e) 
      {
         e.printStackTrace();
      }
   }
}
```
#### 2. TransformTask ####
```
public class TransformTask extends PipelineTask {

   // Used to specify the end condition of execution
   public static final String END_OF_TASK = "~_~END_OF_Line~_~";
   
   // Override the service method present in PipelineTask
   public void service() 
   {
      // This task has both a preceding task and a succeeding task hence
      // it will have an inputQueue as well as an outputQueue. InputQueue
      // of this task is outPutQueue of the preceding task and outputQueue
      // of this task is the inputQueue for the succeeding
      PipelineQueue<Object> inputQ = this.getInputQueue();
      PipelineQueue<Object> outputQ = this.getOutputQueue();
      
      try 
      {
         // remove the element from the Queue
         String str = (String)inputQ.take();
         do
         {
           // Do the transformation
           str = str.toUpperCase();				
           // Put it back in the outputQueue
           outputQ.put(str);
           // remove the element from the Queue again
           str = (String)inputQ.take();
          
         }while(str!=END_OF_TASK)
         
      } 
      catch (Exception e) 
      {
         e.printStackTrace();
      }
      // to specify end condition of the execution.
      outputQ.add(END_OF_TASK);
   }
}
```
#### 3. WriteTask ####
```
public class WriteTask extends PipelineTask {

	public static final String END_OF_TASK = "~_~END_OF_Line~_~";

	// Override the service method present in PipelineTask
	public void service() {

		// get the input and output Queues
		PipelineQueue<Object> inputQ = this.getInputQueue();
		PipelineQueue<Object> outputQ = this.getOutputQueue();

		BufferedWriter out = null;

		try {

			// output file
		        out = new BufferedWriter(new FileWriter("../files/writeFile.txt"));
                        // get data from the queue.
			String str = (String) inputQ.take();
			do {
                               //write the data to file.
				out.write(str + "\r" + "\n");
				str = (String) inputQ.take();
			} while (str != END_OF_TASK);

			// Put the End Result in the Queue (if Any)
			outputQ.put("Execution Result");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
```

And finally invoke these Tasks one by one:

```
// Create a Pipeline
Pipeline pipeline = new Pipeline("Pipeline Test");

// Create objects Tasks which extend PipelineTasks
ReadTask readTask = new ReadTask();
TransformTask transformTask = new TransformTask();
WriteTask writeTask = new WriteTask();

// Add these tasks to the pipeline
pipeline.add("readTask", readTask);
pipeline.add("transformTask", transformTask);
pipeline.add("writeTask", writeTask);

// Join these tasks in order to know the order of execution of the tasks
pipeline.join(readTask, transformTask);
pipeline.join(transformTask, writeTask);

// Execute the Pipeline
pipeline.execute();

// To wait till execution lasts. Skip this line if you do not want to wait for the execution of pipeline to finish.
pipeline.getResult();

```

Note: In the above example we have used some sample files to read and transform which are read from the _files_ folder. The result is also written in the same folder.

_**See the performance benchmark of the test [here](http://code.google.com/p/korus/wiki/PerformanceBenchmark#Pipeline)**_