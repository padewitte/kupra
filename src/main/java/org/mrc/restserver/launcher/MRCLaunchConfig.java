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

import com.beust.jcommander.Parameter;

public class MRCLaunchConfig {

	@Parameter(names = { "-a", "--bindingAdress" }, description = "Adress binded by REST server.")
	private String bindingAdress = "0.0.0.0";

	@Parameter(names = { "-c", "--bindingContext" }, description = "Context of REST server.")
	private String bindingContext = "mrc";

	@Parameter(names = { "-p", "--bindingPort" }, description = "Listen port of REST server.")
	private Integer bindingPort = 8667;

	@Parameter(names = { "-f", "--config" }, description = "Load configuration from file. With this option, MongoDbUri in command line is not needed nor used. Needed to start mrc binding on multiple databases.")
	private String config = null;

	@Parameter(names = { "-cont", "--content" }, description = "Start an additional HTTP content server to serve static files. Not started by default")
	private Boolean content = false;

	@Parameter(names = { "-contFolder", "--contentFolder" }, description = "System folder place to read the static content.")
	private String contentFolder = "content";

	@Parameter(names = { "-contPort", "--contentPort" }, description = "Listen port for static content HTTP server. Default value : listenPort +2")
	private Integer contentPort = null;

	@Parameter(names = "-debug", description = "Debug mode", hidden = true)
	private boolean debug = false;

	@Parameter(names = { "-doc", "--documentation" }, description = "If set to true start the documentation server on listenPort +1. Not started by default.")
	private Boolean documentation = false;

	@Parameter(names = { "-docPort", "--documentationPort" }, description = "Listen port for documentation server. Default value : listenPort +1")
	private Integer documentationPort = null;

	@Parameter(names = { "-h", "--help" }, description = "Show usage on standard output", help = true)
	private boolean help;

	@Parameter(description = "mongoDbUri\n\tFormat is : [mongodb://][username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database][?options]]\n")
	private List<String> mongoDbUri = null;

	@Parameter(names = { "-r", "--rewrite" }, description = "If set, start a URL rewrite mode on listen port - 1. /defaultContext requests are routed to MongoDB urls, /docs requests are routed to documentation server and others path are routed. Not started by default.")
	private Boolean rewrite = false;

	@Parameter(names = { "--multiBindingContext" }, variableArity = true , description = "Specify the context for each additional database binded at launch. bindingContext option is useless in this case.")
	private List<String> multiBindingContext = new ArrayList<String>(); 
	
	@Parameter(names = {"--mongoDbUri"}, hidden = true,  description = "Parameter allowing to specify mongDbUri in parameters for file launch")
	private List<String> mongoDbUriFiles = null;
	
	@Parameter(names = { "-log", "-verbose" }, description = "Level of verbosity", hidden = true)
	private Integer verbose = 1;

	@Parameter(names = { "-v", "--version" }, description = "Print version number", hidden = true)
	private Boolean version = false;

	public String getBindingAdress() {
		return bindingAdress;
	}

	public String getBindingContext() {
		return bindingContext;
	}

	public Integer getBindingPort() {
		return bindingPort;
	}

	public String getConfig() {
		return config;
	}

	public Boolean getContent() {
		return content;
	}

	public String getContentFolder() {
		return contentFolder;
	}

	public Integer getContentPort() {
		return contentPort;
	}

	public Boolean getDocumentation() {
		return documentation;
	}

	public Integer getDocumentationPort() {
		return documentationPort;
	}

	public List<String> getMongoDbUri() {
		return this.mongoDbUri;
	}

	public List<String> getMultiBindingContext() {
		return multiBindingContext;
	}

	// Commons parameters
	public Boolean getRewrite() {
		return rewrite;
	}

	public Integer getVerbose() {
		return verbose;
	}

	public Boolean getVersion() {
		return version;
	}

	public boolean isDebug() {
		return debug;
	}

	public boolean isHelp() {
		return help;
	}

	public void setBindingAdress(String bindingAdress) {
		this.bindingAdress = bindingAdress;
	}

	public void setBindingContext(String bindingContext) {
		this.bindingContext = bindingContext;
	}

	public void setBindingPort(Integer bindingPort) {
		this.bindingPort = bindingPort;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public void setContent(Boolean content) {
		this.content = content;
	}

	public void setContentFolder(String contentFolder) {
		this.contentFolder = contentFolder;
	}

	public void setContentPort(Integer contentPort) {
		this.contentPort = contentPort;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public void setDocumentation(Boolean documentation) {
		this.documentation = documentation;
	}

	public void setDocumentationPort(Integer documentationPort) {
		this.documentationPort = documentationPort;
	}

	public void setHelp(boolean help) {
		this.help = help;
	}

	public void setMongoDbUri(List<String> mongoDbUri) {
		this.mongoDbUri = mongoDbUri;
	}

	public void setMultiBindingContext(List<String> multiBindingContext) {
		this.multiBindingContext = multiBindingContext;
	}

	public void setRewrite(Boolean rewrite) {
		this.rewrite = rewrite;
	}

	public void setVerbose(Integer verbose) {
		this.verbose = verbose;
	}

	public void setVersion(Boolean version) {
		this.version = version;
	}

	public List<String> getMongoDbUriFiles() {
		return mongoDbUriFiles;
	}

	public void setMongoDbUriFiles(List<String> mongoDbUriFiles) {
		this.mongoDbUriFiles = mongoDbUriFiles;
	}

}
