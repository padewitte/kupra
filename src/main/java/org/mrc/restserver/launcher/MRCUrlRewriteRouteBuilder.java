package org.mrc.restserver.launcher;

import org.apache.camel.builder.RouteBuilder;

/**
 * Camel route when URL rewrite is acivated.
 * @author Pierre-Alban DEWITTE.
 *
 */
public class MRCUrlRewriteRouteBuilder extends RouteBuilder {

	private MRCServerBean rootServer;
	private int listenPort;
	
	public MRCUrlRewriteRouteBuilder(MRCServerBean rootServer, int listenPort) {
		this.rootServer = rootServer;
		this.listenPort = listenPort;
	}

	@Override
	public void configure() throws Exception {
		from("jetty:http://"+ rootServer.getBindingAdress() + ":" + (listenPort) + "?matchOnUriPrefix=true")
		.routeId("LB")
		.to("jetty:http://"+ rootServer.getBindingAdress() + ":" + (rootServer.getListenPort())  + "?bridgeEndpoint=true&throwExceptionOnFailure=false&urlRewrite=#urlRewriteCusto");
	}
	

	
	
}
