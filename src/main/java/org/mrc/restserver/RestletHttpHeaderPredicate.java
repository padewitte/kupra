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
import org.apache.camel.Predicate;
import org.restlet.data.Form;
import org.restlet.util.Series;

/**
 * Predicate used to route with a Http header.
 * 
 * @author Pierre-Alban DEWITTE
 * 
 */
public class RestletHttpHeaderPredicate implements Predicate {

	private String headerToCheck;
	private boolean onlyTestExist = false;

	public RestletHttpHeaderPredicate(String headerToCheck) {
		this.headerToCheck = headerToCheck;
	}

	public RestletHttpHeaderPredicate(String headerToCheck,
			boolean onlyTestExist) {
		this.headerToCheck = headerToCheck;
		this.onlyTestExist = onlyTestExist;
	}

	public boolean matches(Exchange exchange) {
        Series headers = exchange.getIn().getHeader("org.restlet.http.headers",
                Series.class);
		boolean ret = false;
		if (headers != null && headers.getFirst(headerToCheck, true) != null) {
			if (onlyTestExist) {
				ret = true;
			} else {
				ret = "true".equalsIgnoreCase(headers.getFirstValue(headerToCheck, true));
			}
		}
		return ret;
	}

}
