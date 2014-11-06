package org.kupra.restserver;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.component.mongodb.CamelMongoDbException;


import com.mongodb.CommandFailureException;
import com.mongodb.MongoException;
import com.mongodb.WriteConcernException;
import com.mongodb.util.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KupraErrorProcessor implements Processor {

    private final Logger log = LoggerFactory.getLogger(getClass());

	public void process(Exchange exchange) throws Exception {

        Message in = exchange.getIn();
        in.setHeader(Exchange.HTTP_RESPONSE_CODE, 500);

		Throwable caused = exchange.getProperty(Exchange.EXCEPTION_CAUGHT,
				Throwable.class);
		if (caused.getCause() instanceof WriteConcernException) {
			in.setBody(JSON.serialize(((WriteConcernException) caused.getCause()).getCommandResult()));
		} else if (caused.getCause() instanceof CommandFailureException) {
			in.setBody(JSON.serialize(((CommandFailureException) caused
					.getCause()).getCommandResult()));

		} else if (caused.getCause() instanceof MongoException) {
			MongoException rootCause = (MongoException) caused.getCause();
			in.setBody(
					"{\"code\" : \"" + rootCause.getCode() + "\" ,\"err\" : \""
							+ rootCause.getMessage().replace("\"", "\'")
							+ "\" }");
		} else if (caused.getCause() instanceof CamelMongoDbException) {
			in.setBody("{ \"err\" : \""
					+ caused.getCause().getMessage().replace("\"", "\'")
					+ "\" }");
		} else {
			String ret = "\"err\" : \"" + caused.getClass().getName() + " => "
					+ caused.getMessage().replace("\"", "\'") + "\"";
			if (caused.getCause() != null) {
				ret = ret + ", \"cause\" : \""
						+ caused.getCause().getClass().getName() + " => " + caused.getCause().getMessage().replace("\"", "\'")
						+ "\"";
			}

			in.setBody("{" + ret.replace("\n", "") + "}");
            log.info("User error",caused);
		}
		
	}
}
