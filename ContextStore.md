# Context Store #

The Context Store functionality is still at a conceptual level. Here, is a quick overview of it.

A ContextStore will let you store key value pairs of string objects that will be accessible across different nodes. It will provide methods that will bind string objects to there context and retrieving them using the name specified. If a String object of the same name is already bound to the context, the object will be replaced.

The process is illustrated with the help of a diagram below where a key value pair is being accessed across various nodes.

**Fig: Key Value Pair accessed across three nodes**<br /> <br />

![http://korus.googlecode.com/svn/trunk/Support/images/Context_Store.jpg](http://korus.googlecode.com/svn/trunk/Support/images/Context_Store.jpg)