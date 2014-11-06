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
package org.kupra.restserver;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
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


    private static final String SERVER_PREFIX = "http4://127.0.0.1:8080/kupra/rest/db";

	// public static MongoClientURI MONGO_URI = new MongoClientURI(
	// "mongodb://test:test@linus.mongohq.com:10040/kupra");
	public static MongoClientURI MONGO_URI;

	@Before
	public void setUp() throws Exception {
		//Assume.assumeNotNull(runnable);
		super.setUp();
	}

	@AfterClass
	public static void stopClass() {
		/*/if (runnable != null) {
			runnable.interrupt();
		}*/
	}

	@Test
	public void aAgetDbStats() throws Exception {
		Exchange exchange = template.request(SERVER_PREFIX + "/", null);
		System.out.println("###"+exchange.getOut().getBody(String.class));
		checkHttpCode200(exchange);
	}



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


	/**
	 * We count items in collection with GET reqest and a header
	 * @throws Exception
	 */
	public void getColStats(String header) throws Exception {
		Exchange exchange = template.request("direct:GET",
				new HeaderTestingProcessor(null, false, header));
		checkHttpCode200(exchange);
		String body = exchange.getOut().getBody(String.class);
		assertNotNull("Body exist", body);
        BasicDBObject test = (BasicDBObject) JSON.parse(body);
		assertEquals("Stats read", 1, test.get("count"));
	}


    @Test
    /**
     * We count items in collection with GET reqest and a header
     * @throws Exception
     */
    public void jTestGetColStats() throws Exception {
        getColStats("ColStats");
    }

    @Test
    /**
     * We count items in collection with GET reqest and a header
     * @throws Exception
     */
    public void kTestGetColStats() throws Exception {
        getColStats("colStats");
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
							Exchange.CONTENT_TYPE,"application/json");
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
