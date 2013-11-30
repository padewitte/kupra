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

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.Manifest;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.beust.jcommander.JCommander;

/**
 * Main class.
 * 
 * @author pierrealban
 * 
 */
public class MRCMain {

	private static String programName;

	// TODO manage exception
	public static void main(String[] arguments) throws Exception {
		// Local var init
		MRCLaunchConfig jct = new MRCLaunchConfig();
		JCommander jcommand = new JCommander(jct);
		// Case insensitive params
		jcommand.setCaseSensitiveOptions(false);

		// Set programm name depending on OS
		if (System.getProperty("os.name").toLowerCase().contains("win")) {
			programName = "mrc.bat";
		} else {
			programName = "mrc.ksh";
		}
		jcommand.setProgramName(programName);

		// Parse starting arguments
		jcommand.parse(arguments);

		// In case of a file launch MongoDbUri could be declared in file with
		// --mongoDbUri
		if (jct.getMongoDbUriFiles() != null
				&& jct.getMongoDbUriFiles().size() > 0) {
			jct.setMongoDbUri(jct.getMongoDbUriFiles());
		}

		if (jct.isHelp()) {
			printHelp(jcommand);
		} else if (jct.getVersion()) {
			// Properties prop = new Properties();
			Enumeration<URL> resources = MRCMain.class.getClassLoader()
					.getResources("META-INF/MANIFEST.MF");
			while (resources.hasMoreElements()) {
				try {
					Manifest manifest = new Manifest(resources.nextElement()
							.openStream());
					// check that this is your manifest and do what you need or
					// get the next one

					if ("org.mrc.restserver.launcher.MRCMain".equals(manifest
							.getMainAttributes().getValue("Main-Class"))) {
						System.out.println("Version : "
								+ manifest.getMainAttributes().getValue(
										"Version"));
						System.out.println("Build : "
								+ manifest.getMainAttributes().getValue(
										"SCM-Revision"));
					}
				} catch (IOException E) {
					// handle
				}
			}
			// System.out.println("Version : "+prop.getProperty("Version"));
			// System.out.println("Build : "+prop.getProperty("SCM-Revision"));
		} else if (jct.getConfig() != null) {
			System.out
					.println("WARNING : Launching from config file "
							+ jct.getConfig()
							+ " . All other options will be ignored.");
			// TODO File launch
		} else if (jct.getMongoDbUri() == null) {
			// MongoDBURI is mandatory if config param is not used
			printHelp(jcommand);
		} else if (jct.getMultiBindingContext() != null
				&& jct.getMultiBindingContext().size() > 1
				&& jct.getMongoDbUri().size() != jct.getMultiBindingContext()
						.size()) {
			// Check multiBindingContext and MongoDbUri coherence
			System.out
					.println("ERROR : The number of database binded is not equal to the number of contexts declared.");
			System.out.println("mongoDbUri : " + jct.getMongoDbUri().size()
					+ " elements");
			System.out.println("--multiBindingContext : "
					+ jct.getMultiBindingContext().size() + " elements");
		} else {
			// End of blocking options
			if (jct.isDebug()) {
				printDebug(jct);
			}

			new MRCLauncher(jct).launch();
		}
	}

	/**
	 * Print help on command line.
	 * 
	 * @param jcommand
	 */
	private static void printHelp(JCommander jcommand) {
		System.out
				.println("Mongo REST Camel - A simple http REST front end for MongoDB");
		System.out
				.println("@see http://padewitte.github.io/mrc/ for full documentation");
		System.out.println("");
		System.out
				.println("For a quick test assuming a MongoDB server is running localy just launch :");
		System.out.println("\t" + programName + " -doc 127.0.0.0:27017/mrc");
		System.out
				.println("Then open http://127.0.0.1:8669/ with a modern browser");
		System.out.println("");
		jcommand.usage();
		System.out.println("");
		// TODO Add launc examples
		// System.out.println("Examples :\n");
		// System.out.println(programName+" -p 8090");
	}

	private static void printDebug(MRCLaunchConfig jct) {
		System.out.println("Starts arguments are");
		System.out.println(ReflectionToStringBuilder.toString(jct));
	}

	private static void readVersion() throws IOException {
		Enumeration<URL> resources = MRCMain.class.getClassLoader()
				.getResources("META-INF/MANIFEST.MF");
		while (resources.hasMoreElements()) {
			try {
				Manifest manifest = new Manifest(resources.nextElement()
						.openStream());
				// check that this is your manifest and do what you need or get
				// the next one
				if ("org.mrc.restserver.launcher.MRCMain".equals(manifest
						.getAttributes("Main-Class"))) {
					System.out.println(manifest.getAttributes("Main-Class"));
				}
			} catch (IOException E) {
				// handle
			}
		}
	}

}
