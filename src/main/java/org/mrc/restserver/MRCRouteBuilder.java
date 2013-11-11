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

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.util.CamelContextHelper;
import org.mrc.restserver.launcher.MRCServerBean;
import org.springframework.stereotype.Component;

@Component
public class MRCRouteBuilder extends RouteBuilder {

	private MRCServerBean beanServer;

	public MRCRouteBuilder() {
		super();
	}

	public MRCRouteBuilder(MRCServerBean beanServer) {
		this.beanServer = beanServer;
	}

	@Override
	public void configure() throws Exception {
		if (beanServer == null) {
			beanServer = CamelContextHelper.mandatoryLookup(getContext(),
					"httpServer", MRCServerBean.class);
		}
		
		from(
				"restlet:http://"
						+ beanServer.getBindingAdress()
						+ ":"
						+ beanServer.getListenPort()
						+ "/"
						+ beanServer.getDefaultContext()
						+ "/{collection}?restletMethods=post,get,put,delete")
				.routeId("defaultRoute-"+beanServer.getMongoDbBean())
				.process(new MRCProcessor())
				.choice()
				.when(header("CamelHttpMethod").isEqualTo("GET"))
				//Use count operation if count flag is set
					.choice()
						.when(new RestletHttpHeaderPredicate("count")).to("mongodb:"+beanServer.getMongoDbBean()+"?database=" + beanServer.getDefaultDatabase()
						+ "&collection=test&operation=count&dynamicity=true")
						.when(new RestletHttpHeaderPredicate("getColStats")).to("mongodb:"+beanServer.getMongoDbBean()+"?database=" + beanServer.getDefaultDatabase()
						+ "&collection=test&operation=getColStats&dynamicity=true")
						.when(new RestletHttpHeaderPredicate("aggregate")).to("mongodb:"+beanServer.getMongoDbBean()+"?database=" + beanServer.getDefaultDatabase()
								+ "&collection=test&operation=aggregate&dynamicity=true")
						.otherwise().to("mongodb:"+beanServer.getMongoDbBean()+"?database=" + beanServer.getDefaultDatabase()
						+ "&collection=test&operation=findAll&dynamicity=true").process(new MRCOutProcessor())
						.endChoice()
				.when(header("CamelHttpMethod").isEqualTo("POST"))
				.to("mongodb:"+beanServer.getMongoDbBean()+"?database=" + beanServer.getDefaultDatabase()
						+ "&collection=test&operation=insert&dynamicity=true")
				.when(header("CamelHttpMethod").isEqualTo("PUT"))
				.to("mongodb:"+beanServer.getMongoDbBean()+"?database=" + beanServer.getDefaultDatabase()
						+ "&collection=test&operation=update&dynamicity=true")
				.when(header("CamelHttpMethod").isEqualTo("DELETE"))
				.to("mongodb:"+beanServer.getMongoDbBean()+"?database=" + beanServer.getDefaultDatabase()
						+ "&collection=test&operation=remove&dynamicity=true")
		// .otherwise().end();
		;

		from(
				"restlet:http://"
						+ beanServer.getBindingAdress()
						+ ":"
						+ beanServer.getListenPort()
						+ "/"
						+ beanServer.getDefaultContext()
						+ "/{collection}/{id}?restletMethods=get,put,delete")
				.routeId("byId-"+beanServer.getMongoDbBean())
				.process(new MRCProcessor())
				.choice()
				.when(header("CamelHttpMethod").isEqualTo("GET"))
					.to("mongodb:"+beanServer.getMongoDbBean()+"?database=" + beanServer.getDefaultDatabase()
						+ "&collection=test&operation=findById&dynamicity=true").process(new MRCOutProcessor())
				.when(header("CamelHttpMethod").isEqualTo("PUT"))
					.to("mongodb:"+beanServer.getMongoDbBean()+"?database=" + beanServer.getDefaultDatabase()
						+ "&collection=test&operation=save&dynamicity=true").process(new MRCOutProcessor())
				.when(header("CamelHttpMethod").isEqualTo("DELETE"))
					.to("mongodb:"+beanServer.getMongoDbBean()+"?database=" + beanServer.getDefaultDatabase()
						+ "&collection=test&operation=remove&dynamicity=true").process(new MRCOutProcessor())
		;

	}

}
