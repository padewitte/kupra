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

public class MRCServerBean {

	public MRCServerBean(String bindingAdress, Integer listenPort,
			String defaultContext) {
		this.bindingAdress = bindingAdress;
		this.listenPort = listenPort;
		this.defaultContext = defaultContext;
	}
	
	public MRCServerBean(String bindingAdress, Integer listenPort,
			String defaultContext, String defaultDatabase, String mongoDbBean, String mongoDbUri) {
		this.bindingAdress = bindingAdress;
		this.listenPort = listenPort;
		this.defaultContext = defaultContext;
		this.defaultDatabase = defaultDatabase;
		this.mongoDbBean = mongoDbBean;
		this.mongoDbUri = mongoDbUri;
	}

	private String bindingAdress;
	private String defaultContext;
	private String defaultDatabase;
	private Integer listenPort;
	private String mongoDbBean;
	private String mongoDbUri;

	public String getMongoDbUri() {
		return mongoDbUri;
	}

	public void setMongoDbUri(String mongoDbUri) {
		this.mongoDbUri = mongoDbUri;
	}

	public String getMongoDbBean() {
		return mongoDbBean;
	}

	public void setMongoDbBean(String mongoDbBean) {
		this.mongoDbBean = mongoDbBean;
	}

	public String getBindingAdress() {
		return bindingAdress;
	}

	public String getDefaultContext() {
		return defaultContext;
	}

	public String getDefaultDatabase() {
		return defaultDatabase;
	}

	public Integer getListenPort() {
		return listenPort;
	}

	public void setBindingAdress(String bindingAdress) {
		this.bindingAdress = bindingAdress;
	}

	public void setDefaultContext(String defaultContext) {
		this.defaultContext = defaultContext;
	}

	public void setDefaultDatabase(String defaultDatabase) {
		this.defaultDatabase = defaultDatabase;
	}

	public void setListenPort(Integer listenPort) {
		this.listenPort = listenPort;
	}
}
