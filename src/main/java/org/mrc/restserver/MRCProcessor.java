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

import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.restlet.data.Form;

import com.mongodb.util.JSON;

/**
 * Make the glue between Restlet and MongoDB components.
 * 
 * @author pierrealban
 * 
 */
public class MRCProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {
		Message in = exchange.getIn();

		String body = in.getHeader("query", String.class);
		String collection = in.getHeader("collection", String.class);
		String idHeader = in.getHeader("id", String.class);

		// Copy query header in body if present
		Form headers = in.getHeader("org.restlet.http.headers", Form.class);
		if (headers != null && headers.getFirst("query") != null) {
			body = headers.getFirst("query").getValue();
		}
		// Setting collection headr
		if (collection != null && !"".equals(collection)) {
			exchange.getIn().setHeader("CamelMongoDbCollection", collection);
		}

		// Copy all Http Incoming Headers into Camel headers adding CamelMongoDb
		// into it.
		Iterator<Entry<String, String>> entriesSet = headers.getValuesMap()
				.entrySet().iterator();
		while (entriesSet.hasNext()) {
			Entry<String, String> entrie = entriesSet.next();
			exchange.getIn().setHeader("CamelMongoDb" + entrie.getKey(),
					entrie.getValue());
		}

		// Case id header present
		if (idHeader != null && !"".equals(idHeader)) {
			// the getById mongoDb component need id in body. In case of byId
			// operation make a copy.
			if (exchange.getFromRouteId().startsWith("byId")
					&& "GET".equals(in.getHeader("CamelHttpMethod"))) {
				body = idHeader;
			} else if ("DELETE".equals(in.getHeader("CamelHttpMethod"))) {
				idHeader = URLDecoder.decode(idHeader, "UTF-8");
				if (!idHeader.contains("$oid")) {
					idHeader = "'" + idHeader + "'";
				}
				body = " { '_id' : " + idHeader + " }";
			}
		}

		// setting the body if set by headers
		if (body != null && !"".equals(body)) {
			exchange.getIn().setBody(JSON.parse(body));
		} else if (in.getBody() != null) {
			// Copying http workload into Camel body with a JSON parse. Needed
			// for PUT operation (this it a workaround).
			exchange.getIn().setBody(JSON.parse(in.getBody(String.class)));
		}

	}
}
