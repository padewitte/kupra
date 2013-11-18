package org.mrc.restserver;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.mongodb.CamelMongoDbException;
import org.apache.camel.component.restlet.RestletConstants;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Status;

import com.mongodb.CommandFailureException;
import com.mongodb.MongoException;
import com.mongodb.WriteConcernException;
import com.mongodb.util.JSON;

public class MRCErrorProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {
		// the caused by exception is stored in a property on
		// the exchange
		// use Restlet API to create the response
		Response response = exchange.getIn().getHeader(
				RestletConstants.RESTLET_RESPONSE, Response.class);
		response.setStatus(Status.SERVER_ERROR_INTERNAL);

		Throwable caused = exchange.getProperty(Exchange.EXCEPTION_CAUGHT,
				Throwable.class);
		if (caused.getCause() instanceof WriteConcernException) {
			response.setEntity(JSON.serialize(((WriteConcernException) caused
					.getCause()).getCommandResult()),
					MediaType.APPLICATION_JSON);
		} else if (caused.getCause() instanceof CommandFailureException) {
			response.setEntity(JSON.serialize(((CommandFailureException) caused
					.getCause()).getCommandResult()),
					MediaType.APPLICATION_JSON);

		} else if (caused.getCause() instanceof MongoException) {
			MongoException rootCause = (MongoException) caused.getCause();
			response.setEntity(
					"{\"code\" : \"" + rootCause.getCode() + "\" ,\"err\" : \""
							+ rootCause.getMessage().replace("\"", "\'")
							+ "\" }", MediaType.APPLICATION_JSON);
		} else if (caused.getCause() instanceof CamelMongoDbException) {
			response.setEntity("{ \"err\" : \""
					+ caused.getCause().getMessage().replace("\"", "\'")
					+ "\" }", MediaType.APPLICATION_JSON);
		} else {
			String ret = "\"err\" : \"" + caused.getClass().getName() + " => "
					+ caused.getMessage().replace("\"", "\'") + "\"";
			if (caused.getCause() != null) {
				ret = ret + ", \"cause\" : \""
						+ caused.getCause().getClass().getName() + " => " + caused.getCause().getMessage().replace("\"", "\'")
						+ "\"";
			}

			response.setEntity("{" + ret.replace("\n", "") + "}", MediaType.APPLICATION_JSON);
			caused.printStackTrace();
		}
		exchange.getOut().setBody(response);
	}
}
