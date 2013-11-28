package org.mrc.restserver.launcher;

import org.apache.camel.builder.RouteBuilder;

/**
 * Camel route when URL rewrite is acivated.
 * @author Pierre-Alban DEWITTE.
 *
 */
public class MRCUrlRewriteRouteBuilder extends RouteBuilder {

	private MRCServerBean rootServer;
	
	public MRCUrlRewriteRouteBuilder(MRCServerBean rootServer) {
		this.rootServer = rootServer;
	}

	@Override
	public void configure() throws Exception {
		from("jetty:http://"+ rootServer.getBindingAdress() + ":" + (rootServer.getListenPort()+1) + "?matchOnUriPrefix=true")
		.routeId("LB")
		.to("jetty:http://"+ rootServer.getBindingAdress() + ":" + (rootServer.getListenPort())  + "?bridgeEndpoint=true&throwExceptionOnFailure=false&urlRewrite=#urlRewriteCusto");
	}
	

	
	
}
