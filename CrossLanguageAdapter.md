## Introduction ##
Cross Language Adapter is a communication bridge between other languages and Korus Runtime. Using Cross Language Adapters a Korus Runtime can be utilized for execution purpose from other languages also.

Currently only Erlang Adapter is supported.

## How to use ##

Below is an example to show how Erlang Adapter can be used.

### Sample Implementation ###
This example will send a request containing details of an item to BuyProcess1 of Korus Runtime listening at localhost. Korus Runtime will process the request and responds back to the erlang client.

Download complete source code of sample from [here](http://korus.googlecode.com/svn/trunk/Product/korus/example/ErlangAdapter.zip)

Here is the erlang code to use Korus Runtime for execution purpose
```
-module(test).
-export([go/0,callback/0]).

go()->
    	%% Address of the Korus Run-time node %%
        Host = "127.0.0.1",
        Pid = spawn(test,callback, []), 
        register(callback, Pid),
        %% Connect to the Korus node %%
        korusAdapter:connect(Host),

        D = dict:new(),
	D1 = dict:append("bookName", "Five Point Someone", D),
	%% Send the request to the Korus node %%
	korusAdapter:send("buyProcess1",D1),
	ok.	
	
callback()->
	receive
		{response,ResponseObj} ->
			unregister(callback),
                        %% fetch data from Hashmap returned from Korus %%
			D = dict:fetch(response,ResponseObj),
			io:format("Response from Korus Node: ~p",[D]),
                        %% Disconnect when task is complete %%
			korusAdapter:disconnect()
	end,
	callback().

```

Here is the Test code to start Korus-Runtime
```
import com.impetus.labs.korus.core.KorusRuntime;
import com.impetus.labs.korus.exception.ProcessAlreadyExistsException;

/**
 * Tests message Passing Between Processes and send response back to erlang
 * 
 */
public class TestProcess
{

	public static void main(String args[])
	{

		// Create two Processes
		BuyProcess1 buyProcess1 = new BuyProcess1();
		BuyProcess2 buyProcess2 = new BuyProcess2();

		// Register them with the Korus Runtime
		try
		{
			KorusRuntime.registerProcess("buyProcess1", buyProcess1);
			KorusRuntime.registerProcess("buyProcess2", buyProcess2);
		} catch (ProcessAlreadyExistsException e)
		{
			e.printStackTrace();
		}
	}
}


```
Sample code to create process BuyProcess1.java
```
import com.impetus.labs.korus.core.KorusRuntime;
import com.impetus.labs.korus.core.message.Message;
import com.impetus.labs.korus.core.process.Process;

// Import other necessary Business Logic Packages and Classes

/**
 * BuyProcess1 Extends Abstract Process Class
 */
public class BuyProcess1 extends Process
{

	// Overide the service Method

	public void service(Message requestMessage)
	{
		// Get item name from the Message Object
		String bookName = (String) requestMessage.get("bookName");
		System.out.println("Inside Process1");
		System.out.println("Name of the book: " + bookName);

		// Rest of the Business Logic
		String author = fetchDetails(bookName);

		// Put the intermediate result in the Message Object for next
		// process
		requestMessage.put("author", author);

		System.out.println("Passing Message to Process2...");
		// Send Message Asynchronously to the next Process on Local Node
		KorusRuntime.send("buyProcess2", requestMessage);
	}

	/**
	 * Method to Fetch Details of book. To keep it simple just returning an
	 * String authorName.
	 * 
	 * @param bookName
	 *            Name of the book
	 * @return author of the book
	 */
	private String fetchDetails(String bookName)
	{
		String authorName = null;
		// Some dummy code
		// Method Implementation ...
		if(bookName.equals("Five Point Someone"))
		{
			authorName = "Chetan Bhagat";
		}
		return authorName;

	}
}

```
Sample code to create process BuyProcess2.java
```
import com.impetus.labs.korus.core.KorusRuntime;
import com.impetus.labs.korus.core.message.Message;
import com.impetus.labs.korus.core.process.Process;

// Import other necessary Business Logic Packages and Classes

/**
 * BuyProcess2 Extends Abstract Process Class
 */
public class BuyProcess2 extends Process
{
	// Overide the service Method
	public void service(Message message)
	{

		System.out.println("Inside Process2");
		// Get book and author name from the Message Object
		String bookName = message.get("bookName");
		String authorName = message.get("author");
		// Rest of the Business Logic
		String bookRating = rateBook(bookName, authorName);

		Message msg = new Message();
		msg.put("response", bookRating);
		// Send response back to erlang-node.
		KorusRuntime.sendToErlang("127.0.0.1", "callback", msg);
	}

	/**
	 * Rates an item on basis of its cost
	 * 
	 * @param bookName
	 *            name of the book to be rated
	 * @param authorName
	 *            name of the author of the book
	 * @return Rating of an book
	 */
	private String rateBook(String bookName, String authorName)
	{

		String bookRating = "";
		if (bookName.equals("Five Point Someone")
				&& authorName.equals("Chetan Bhagat"))
			bookRating = "Best seller in India";
		else
			bookRating = "not known";

		return bookRating;
	}
}
```

