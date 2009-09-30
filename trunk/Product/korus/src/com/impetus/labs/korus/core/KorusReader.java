/**
 * KorusReader.java
 * 
 * Copyright 2009 Impetus Infotech India Pvt. Ltd. . All Rights Reserved.
 *
 * This software is proprietary information of Impetus Infotech, India.
 */
package com.impetus.labs.korus.core;

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Set;

public class KorusReader implements Runnable
{
	public void run()
	{
		try
		{
			CharsetDecoder decoder = charset.newDecoder();
			// Create the server socket channel
			ServerSocketChannel server = ServerSocketChannel.open();
			// nonblocking I/O
			server.configureBlocking(false);
			// host-port 7935
			server.socket().bind(
					new java.net.InetSocketAddress(InetAddress.getLocalHost()
							.getHostAddress(), KorusRuntime
							.getPort()));
			// Create the selector
			Selector selector = Selector.open();
			// Recording server to selector (type OP_ACCEPT)
			server.register(selector, SelectionKey.OP_ACCEPT);

			// Infinite server loop
			for (;;)
			{
				// Waiting for events
				selector.select();
				// Get keys
				Set keys = selector.selectedKeys();
				Iterator iter = keys.iterator();

				// For each keys...
				while (iter.hasNext())
				{
					SelectionKey key = (SelectionKey) iter.next();
					// Remove the current key
					iter.remove();
					// if isAccetable = true
					// then a Client required a connection
					if (key.isAcceptable())
					{
						// get Client socket channel
						SocketChannel client = server.accept();
						// Non Blocking I/O
						client.configureBlocking(false);
						// recording to the selector (reading)
						client.register(selector, SelectionKey.OP_READ);
						continue;
					}

					// if isReadable = true
					// then the server is ready to read					
					if (key.isReadable())
					{
						Message requestMessage = new Message();
						String keyName = null;
						String valueData = null;

						SocketChannel client = (SocketChannel) key.channel();
						int BUFFER_SIZE = 2;
						ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
						// Read byte coming from the Client
						client.read(buffer);
						buffer.flip();

						CharBuffer charBuffer = decoder.decode(buffer);
						int noOfParams = Integer.parseInt(charBuffer.toString());
						

						for (int i = 0; i < noOfParams; i++)
						{
							// KeyLength
							ByteBuffer lengthOfKey = ByteBuffer.allocate(LENGTH);
							client.read(lengthOfKey);
							lengthOfKey.flip();
							CharBuffer keyCharBuffer = decoder.decode(lengthOfKey);
							int keyLength = Integer.parseInt(keyCharBuffer
									.toString());

							// KeyName
							ByteBuffer keyNameBuffer = ByteBuffer.allocate(keyLength);
							client.read(keyNameBuffer);
							keyNameBuffer.flip();
							CharBuffer keyNameCharBuffer = decoder.decode(keyNameBuffer);
							keyName = keyNameCharBuffer.toString();

							// ValueLength
							ByteBuffer lengthOfValue = ByteBuffer.allocate(LENGTH);
							client.read(lengthOfValue);
							lengthOfValue.flip();
							CharBuffer valueCharBuffer = decoder.decode(lengthOfValue);
							int valueLength = Integer.parseInt(valueCharBuffer.toString());

							// ValueData
							ByteBuffer valueNameBuffer = ByteBuffer.allocate(valueLength);
							client.read(valueNameBuffer);
							valueNameBuffer.flip();
							CharBuffer valueNameCharBuffer = decoder.decode(valueNameBuffer);
							valueData = valueNameCharBuffer.toString();

							requestMessage.put(keyName, valueData);
							
						}
						//get the process name from the message.
						String processName = (String) requestMessage.get("action");
						//send the message to the process 
						KorusRuntime.send(processName, requestMessage);
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static final int LENGTH = 4;
	private static Charset charset = Charset.forName("ISO-8859-1");	
}