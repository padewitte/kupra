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

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;

/**
 * Custom processor used to propagate Custom HttpHeaders.
 * @author Pierre-Alban DEWITTE
 */
public class MRCOutProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {
		Message in = exchange.getIn();
		// use Restlet API to create the response
		Response response = exchange.getIn().getHeader(
				RestletConstants.RESTLET_RESPONSE, Response.class);

		String inBody = in.getBody(String.class);
		
		//Send a 204 and no no content in payload where nothing found
		if (inBody == null || "".equals(inBody) || "[]".equals(inBody)) {
			response.setStatus(Status.SUCCESS_NO_CONTENT);
		} else {
			response.setStatus(Status.SUCCESS_OK);
			response.setEntity(inBody, MediaType.APPLICATION_JSON);
		}
		
		//Copy headers
		Form responseHeaders = (Form) response.getAttributes().get(
				"org.restlet.http.headers");
		if (responseHeaders == null) {
			responseHeaders = new Form();
			response.getAttributes().put("org.restlet.http.headers",
					responseHeaders);
		}
		responseHeaders.add("ResultTotalSize",
				in.getHeader("CamelMongoDbResultTotalSize", String.class));
		responseHeaders.add("ResultPageSize",
				in.getHeader("CamelMongoDbResultPageSize", String.class));

		exchange.getOut().setBody(response);
	}
}
