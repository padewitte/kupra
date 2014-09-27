/**
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013 Pierre-Alban DEWITTE
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.mrc.restserver;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mrc.restserver.launcher.MRCLaunchConfig;
import org.mrc.restserver.launcher.MRCLauncher;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.util.JSON;

/**
 * Very basic tests.
 * 
 * @author pierrealban
 * 
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestRestEnpoints extends CamelTestSupport {

	// public static MongoClientURI MONGO_URI = new MongoClientURI(
	// "mongodb://test:test@linus.mongohq.com:10040/mrc");
	public static MongoClientURI MONGO_URI;

	private static Thread runnable;

	/**
	 * Checks whether Mongo is running using the connection URI defined in the
	 * mongodb.test.properties file
	 * 
	 * @throws IOException
	 */
	@BeforeClass
	public static void checkMongoRunning() throws IOException {
		try {

			if (System.getenv("MONGODB_URL") == null) {
				MONGO_URI = new MongoClientURI(
						"mongodb://test:test@127.0.0.1/mrc");
			} else {
				MONGO_URI = new MongoClientURI(System.getenv("MONGODB_URL"));
			}

			new MongoClient(MONGO_URI).getDB("mrc").getCollectionNames();
			final MRCLaunchConfig jct = new MRCLaunchConfig();
			jct.setBindingAdress("8667");
			jct.setBindingContext("mrc");
			jct.setBindingAdress("0.0.0.0");
			ArrayList<String> listUri = new ArrayList<String>();
			listUri.add(MONGO_URI.getURI());
			jct.setMongoDbUri(listUri);
			runnable = new Thread() {
				public void run() {
					try {
						new MRCLauncher(jct).launch();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			runnable.start();
		} catch (Throwable e) {
			System.err.println("MRC test database @" + MONGO_URI.getURI()
					+ " not reachable");
		}
	}

	@Before
	public void setUp() throws Exception {
		Assume.assumeNotNull(runnable);
		super.setUp();
	}

	@AfterClass
	public static void stopClass() {
		if (runnable != null) {
			runnable.interrupt();
		}
	}

	@Test
	public void aAgetDbStats() throws Exception {
		Exchange exchange = template.request(SERVER_PREFIX + "/", null);
		System.out.println(exchange.getOut().getBody(String.class));
		checkHttpCode200(exchange);
	}

	private static final String SERVER_PREFIX = "http4://127.0.0.1:8667/mrc";

	private void checkHttpCode200(Exchange exchange) {
		int responseCode = exchange.getOut().getHeader(
				Exchange.HTTP_RESPONSE_CODE, Integer.class);
		assertEquals("HTTP CODE 200 expected", 200, responseCode);
	}

	private void checkHttpCode204(Exchange exchange) {
		int responseCode = exchange.getOut().getHeader(
				Exchange.HTTP_RESPONSE_CODE, Integer.class);
		assertEquals("HTTP CODE 204 expected", 204, responseCode);
	}

	private void checkPutPost(Exchange exchange) {
		String body = exchange.getOut().getBody(String.class);
		assertNotNull("Body exist", body);
		BasicDBObject test = (BasicDBObject) JSON.parse(body);
		assertEquals("Assert update/insert is OK", 1, test.get("ok"));
	}

	@Test
	/**
	 * First we empty the collection.
	 * @throws Exception
	 */
	public void aTestDeleteAllItems() throws Exception {
		Exchange exchange = template.request("direct:DELETE",
				new HeaderTestingProcessor("{'name' : {$exists : true}}"));
		System.out.println(exchange.getOut().getBody(String.class));
		checkHttpCode200(exchange);

	}

	@Test
	/**
	 * We test that the collection is empty with no element.
	 * @throws Exception
	 */
	public void bTestFirstEmptyGet() throws Exception {
		Exchange exchange = template.request("direct:GET", null);
		System.out.println(exchange.getOut().getBody(String.class));
		checkHttpCode204(exchange);
	}

	@Test
	/**
	 * We add a first document.
	 * @throws Exception
	 */
	public void cTestAddFirstdocuments() throws Exception {
		Exchange exchange = template.request("direct:POST",
				new HeaderTestingProcessor(
						"{'_id' : '1', 'name' : 'Sylvain CHAVANEL'}", true));
		checkHttpCode200(exchange);
		checkPutPost(exchange);
		exchange = template.request("direct:POST", new HeaderTestingProcessor(
				"{'_id' : 2, 'name' : 'Thomas VOECKLER'}", true));
		checkHttpCode200(exchange);
		checkPutPost(exchange);
	}

	@Test
	/**
	 * We Get one item in the collection.
	 * @throws Exception
	 */
	public void dTestGetOneElementNoFilter() throws Exception {
		Exchange exchange = template.request("direct:GET", null);
		checkHttpCode200(exchange);
		String body = exchange.getOut().getBody(String.class);
		assertNotNull("Body exist", body);
		BasicDBList test = (BasicDBList) JSON.parse(body);
		assertEquals("One item read", 2, test.size());
		assertEquals("Assert Id is 1", "1", ((DBObject) test.get(0)).get("_id"));
	}

	@Test
	/**
	 * We Get two items in the collection.
	 * @throws Exception
	 */
	public void eTestGetOneElementById() throws Exception {
		Exchange exchange = template.request("direct:GET_BY_ID", null);
		checkHttpCode200(exchange);
		String body = exchange.getOut().getBody(String.class);
		assertNotNull("Body exist", body);
		BasicDBObject test = (BasicDBObject) JSON.parse(body);
		assertEquals("Assert Id is 2", 2, test.get("_id"));
		assertTrue("This should be Thomas Voeckler", test.getString("name")
				.contains("Thomas VOECKLER"));
	}

	@Test
	/**
	 * We count items in collection with GET reqest and a header
	 * @throws Exception
	 */
	public void fTestCount() throws Exception {
		Exchange exchange = template.request("direct:GET",
				new HeaderTestingProcessor(null, false, "count"));
		checkHttpCode200(exchange);
		String body = exchange.getOut().getBody(String.class);
		assertNotNull("Body exist", body);
		Integer nbItems = (Integer) JSON.parse(body);
		assertEquals("One item read", new Integer(2), nbItems);
	}

	@Test
	/**
	 * Get with use of filtering
	 * @throws Exception
	 */
	public void gTestGetWithFilter() throws Exception {
		Exchange exchange = template.request("direct:GET",
				new HeaderTestingProcessor("{'name' : 'Sylvain CHAVANEL'}"));
		checkHttpCode200(exchange);
		String body = exchange.getOut().getBody(String.class);
		assertNotNull("Body exist", body);
		BasicDBList test = (BasicDBList) JSON.parse(body);
		assertEquals("One item read", 1, test.size());
		assertEquals("Assert Id is 1", "1", ((DBObject) test.get(0)).get("_id"));
	}

	@Test
	/**
	 * Get with use of filtering
	 * @throws Exception
	 */
	public void hTestPutWithFilter() throws Exception {
		Exchange exchange = template.request("direct:PUT",
				new HeaderTestingProcessor(
						"[{name : {$exists : true}}, {$set: {update : true}}]",
						true));
		checkHttpCode200(exchange);
		checkPutPost(exchange);
	}

	@Test
	/**
	 * Get with use of filtering
	 * @throws Exception
	 */
	public void iTestDeleteWithFilter() throws Exception {
		Exchange exchange = template.request("direct:DELETE",
				new HeaderTestingProcessor("{'_id' : '1'}"));
		checkHttpCode200(exchange);
		checkPutPost(exchange);
	}

	@Test
	/**
	 * We count items in collection with GET reqest and a header
	 * @throws Exception
	 */
	public void jTestGetColStats() throws Exception {
		Exchange exchange = template.request("direct:GET",
				new HeaderTestingProcessor(null, false, "GetColStats"));
		checkHttpCode200(exchange);
		String body = exchange.getOut().getBody(String.class);
		assertNotNull("Body exist", body);
        BasicDBObject test = (BasicDBObject) JSON.parse(body);
		assertEquals("Stats read", 1, test.get("count"));
	}

	@Override
	protected RouteBuilder createRouteBuilder() {
		return new RouteBuilder() {
			public void configure() {

				// Basic REST operations
				from("direct:DELETE")
						.setHeader(
								Exchange.HTTP_METHOD,
								constant(org.apache.camel.component.http4.HttpMethods.DELETE))
						.to(SERVER_PREFIX
								+ "/test?throwExceptionOnFailure=false")
						.to("mock:DELETE");

				from("direct:GET")
						.setHeader(
								Exchange.HTTP_METHOD,
								constant(org.apache.camel.component.http4.HttpMethods.GET))
						.to(SERVER_PREFIX
								+ "/test?throwExceptionOnFailure=false")
						.to("mock:GET");
				from("direct:POST")
						.setHeader(
								Exchange.HTTP_METHOD,
								constant(org.apache.camel.component.http4.HttpMethods.POST))
						.to(SERVER_PREFIX
								+ "/test?throwExceptionOnFailure=false")
						.to("mock:POST");
				from("direct:PUT")
						.setHeader(
								Exchange.HTTP_METHOD,
								constant(org.apache.camel.component.http4.HttpMethods.PUT))
						.to(SERVER_PREFIX
								+ "/test?throwExceptionOnFailure=false")
						.to("mock:PUT");
				// operations by ID
				from("direct:GET_BY_ID")
						.setHeader(
								Exchange.HTTP_METHOD,
								constant(org.apache.camel.component.http4.HttpMethods.GET))
						.to(SERVER_PREFIX
								+ "/test/2?throwExceptionOnFailure=false")
						.to("mock:GET");
			}
		};
	}

	class HeaderTestingProcessor implements Processor {

		private String bodyContent;
		private boolean body = false;
		private String customFlag;

		public HeaderTestingProcessor(String bodyContent) {
			this.bodyContent = bodyContent;
		}

		public HeaderTestingProcessor(String bodyContent, boolean body) {
			this.bodyContent = bodyContent;
			this.body = body;
		}

		public HeaderTestingProcessor(String bodyContent, boolean body,
				String customFlag) {
			this.bodyContent = bodyContent;
			this.body = body;
			this.customFlag = customFlag;
		}

		public void process(Exchange exchange) throws Exception {
			exchange.getIn()
					.setHeader(
							Exchange.CONTENT_TYPE,
							org.apache.camel.component.http4.HttpConstants.CONTENT_TYPE_WWW_FORM_URLENCODED);
			if (customFlag != null) {
				exchange.getIn().setHeader(customFlag, true);
			}
			if (body) {
				exchange.getIn().setBody(bodyContent, String.class);
			} else {
				exchange.getIn().setHeader("query", bodyContent);
			}
		}
	};
}
