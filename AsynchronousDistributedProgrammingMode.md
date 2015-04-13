Korus can be used for Asynchronous & Distributed Programming as below
### Code Structure ###

The structure below shows, how a use case will look when it is broken up into processes. Suppose we have a Buy UseCase in which a number of tasks are performed one after the other serially. Typically, we will use methods to maintain modularity. These methods can very well be candidates for processes. Therfore, a big UseCase now becomes a set of smaller processes. Here, we can see BuyProcess1, BuyProcess2 and BuyProcess3 which are nothing but pieces of the bigger BuyProcess.

http://korus.googlecode.com/svn/trunk/Support/images/korus_code_structure.JPG

### Sample Implementation ###

```

import com.impetus.labs.korus.core.KorusRuntime;
import com.impetus.labs.korus.core.message.Message;
import com.impetus.labs.korus.exception.ProcessAlreadyExistsException;

/**
 * Tests message Passing Between Processes
 *
 */
public class TestProcess {
	
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
	
	Message msg = new Message();
	String bookName = "Five Point Someone";
	msg.put("bookName", bookName);
	KorusRuntime.send("buyProcess1", msg);
	
	}
}

```

Create Two Processes Called BuyProcess1 and BuyProcess2. Below, is the Code for BuyProcess1:

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

		// Send Message Asynchronously to the next Process on Remote Node
		// Set only String Objects in the Message in case of Remote Calls
		// KorusRuntime.send("IP-Address", "buyProcess2", requestMessage)
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

Code for BuyProcess2:
```
import com.impetus.labs.korus.core.message.Message;
import com.impetus.labs.korus.core.process.Process;

// Import other necessary Business Logic Packages and Classes

/**
 * BuyProcess2 Extends Abstract Process Class
 */
public class BuyProcess2 extends Process
{

	// Overide the service Method
	public void service(Message requestMessage)
	{

		System.out.println("Inside Process2");
		// Get book and author name from the Message Object
		String bookName = requestMessage.get("bookName");
		String authorName = requestMessage.get("author");
		// Rest of the Business Logic
		String bookRating = rateBook(bookName, authorName);

		System.out.println("Rating of an Book: " + bookRating);

		// Forcefully Shutting the Korus Runtime if not needed
		System.exit(0);

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
			bookRating = "India's best seller";
		else
			bookRating = "not known";

		return bookRating;

	}
}

```