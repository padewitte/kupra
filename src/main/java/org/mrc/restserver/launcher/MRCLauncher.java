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
package org.mrc.restserver.launcher;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.main.Main;
import org.mrc.restserver.MRCRouteBuilder;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MRCLauncher {
	
	private MRCLaunchConfig serverConfig;
	
	public MRCLauncher(MRCLaunchConfig serverConfig){
		this.serverConfig = serverConfig;
	}
	
	public void launch() throws Exception {

		// Main makes it easier to run a Spring application
		Main main = new Main();
		List<MRCServerBean> serversList = new ArrayList<MRCServerBean>();
		// enable hangup support allows Camel to detect when the JVM is
		// terminated
		main.enableHangupSupport();

		for (int i = 0; i < serverConfig.getMongoDbUri().size(); i++) {
			

			//Correct URI if needed
			String mongoDbUri = serverConfig.getMongoDbUri().get(i);
			if (!mongoDbUri.startsWith("mongodb://")) {
				mongoDbUri = "mongodb://" + mongoDbUri;
				//XXX check URI form to throw a more user friendly message
			}
			
			//evaluate the database name
			String defaultDatabase = "mrc";
			if (mongoDbUri.lastIndexOf('/') != -1) {
				defaultDatabase = mongoDbUri.substring(
						mongoDbUri.lastIndexOf('/') + 1);
			}
			
			String context = serverConfig.getBindingContext();
			// Determine the right context
			if (serverConfig.getMultiBindingContext() != null
					&& serverConfig.getMultiBindingContext().size() > 0) {
				context = serverConfig.getMultiBindingContext().get(i);
			} else if (serverConfig.getMongoDbUri().size() > 1) {
				context = serverConfig.getBindingContext() + "/" + defaultDatabase;
			}

			//Build the server bean
			MRCServerBean mainMrcBeanServer = new MRCServerBean(serverConfig.getBindingAdress(),
					serverConfig.getBindingPort(),
					context, defaultDatabase, "myDb"
							+ i, mongoDbUri);
			
			//Bind camel beans to the camelContext
			main.bind(mainMrcBeanServer.getMongoDbBean(), new MongoClient(
					new MongoClientURI(mainMrcBeanServer.getMongoDbUri())));
			main.addRouteBuilder(new MRCRouteBuilder(mainMrcBeanServer));
			
			serversList.add(mainMrcBeanServer);
		}

		//Run documentation server
		MRCServerBean documentationServer = getDocServer(serverConfig);
		if (documentationServer != null) {
			serversList.add(documentationServer);
		}
		
		//Run content server
		MRCServerBean contentServer = getContentServer(serverConfig);

		// Manage the rewrite rules
		if (serverConfig.getRewrite()) {
			main.bind("urlRewriteCusto", new MRCUrlRewrite(contentServer,
					serversList));
			main.addRouteBuilder(new MRCUrlRewriteRouteBuilder(contentServer));
		}

		// Come get some !
		main.run();
	}

	/**
	 * @param configServer
	 * @return
	 * @throws Exception
	 */
	public MRCServerBean getDocServer(MRCLaunchConfig configServer)
			throws Exception {
		MRCServerBean ret = null;
		if (configServer.getDocumentation()) {
			Integer listenPort = configServer.getDocumentationPort();
			if (listenPort == null) {
				listenPort = configServer.getBindingPort() + 1;
			}
			MRCJettyServer.runJettyServer(listenPort, "documentation");
			ret = new MRCServerBean(configServer.getBindingAdress(),
					listenPort, "documentation");
		}
		return ret;
	}

	/**
	 * @param configServer
	 * @return
	 * @throws Exception
	 */
	public MRCServerBean getContentServer(MRCLaunchConfig configServer)
			throws Exception {
		MRCServerBean ret = null;
		if (configServer.getContent()) {
			Integer listenPort = configServer.getContentPort();
			if (listenPort == null) {
				listenPort = configServer.getBindingPort() + 2;
			}
			MRCJettyServer.runJettyServer(listenPort,
					configServer.getContentFolder());
			ret = new MRCServerBean(configServer.getBindingAdress(),
					listenPort, "content");
		}
		return ret;
	}
}
