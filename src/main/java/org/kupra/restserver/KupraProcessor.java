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
import com.mongodb.util.JSON;
import org.apache.commons.lang.StringUtils;


/**
 * Make the glue between Restlet and MongoDB components.
 * 
 * @author pierrealban
 * 
 */
public class KupraProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {
		Message in = exchange.getIn();
		// exchange.getFromRouteId();
		String body = null;

        String queryHeader = in.getHeader("Query", String.class);
		String idHeader = in.getHeader("Id", String.class);
        String aggregateHeader = in.getHeader("Aggregate", String.class);
        String cmdHeader = in.getHeader("Cmd", String.class);
        String fieldFilter = in.getHeader("FieldsFilter", String.class);
        if(fieldFilter != null) {
            in.setHeader("CamelMongoDbFieldsFilter",fieldFilter);
        }

		// Case id header present
		if (idHeader != null && !"".equals(idHeader)) {

            try {
                idHeader = String.valueOf(Double.parseDouble(idHeader));
            } catch (NumberFormatException ex){
                if(!idHeader.startsWith("'") && idHeader.endsWith("'")) {
                    idHeader = "'" + idHeader + "'";
                }
            }
			// the getById mongoDb component need id in body. In case of byId
			// operation make a copy.
            String methodHeader = Exchange.HTTP_METHOD;
			if ("GET".equals(in.getHeader(methodHeader))) {
				body = idHeader;
			} else if ("DELETE".equals(in.getHeader(methodHeader))) {
				body = " { '_id' : " + idHeader + " }";
			}
		} else if (!StringUtils.isEmpty(queryHeader)){
            body = queryHeader;
        } else if (!StringUtils.isEmpty(aggregateHeader)){
            body = aggregateHeader;
        }   else if (!StringUtils.isEmpty(cmdHeader)){
            body = cmdHeader;
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
