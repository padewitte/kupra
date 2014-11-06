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
import org.apache.camel.Message;
import org.apache.camel.Processor;


/**
 * Custom processor used to propagate Custom HttpHeaders.
 * @author Pierre-Alban DEWITTE
 */
public class KupraOutProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {
		Message in = exchange.getIn();

		String inBody = in.getBody(String.class);
		
		//Send a 204 and no no content in payload where nothing found
		if (inBody == null || "".equals(inBody) || "[]".equals(inBody)) {
			in.setHeader(Exchange.HTTP_RESPONSE_CODE, 204);
			in.setBody(null);
		}
		
		if(in.getHeader("CamelMongoDbResultTotalSize", String.class) != null) {
            in.setHeader("ResultTotalSize",
                    in.getHeader("CamelMongoDbResultTotalSize", String.class));
        }
        if(in.getHeader("CamelMongoDbResultPageSize", String.class) != null) {
            in.setHeader("ResultPageSize",
                    in.getHeader("CamelMongoDbResultPageSize", String.class));
        }
        if(in.getHeader("CamelMongoDbRecordsAffected", String.class) != null) {
            in.setHeader("RecordsAffected",
                    in.getHeader("CamelMongoDbRecordsAffected", String.class));
        }
		


	}
}
