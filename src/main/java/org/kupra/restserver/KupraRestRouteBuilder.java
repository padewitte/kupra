package org.kupra.restserver;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mongodb.CamelMongoDbException;
import org.apache.camel.component.mongodb.MongoDbOperation;
import org.apache.camel.model.rest.RestBindingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pierrealban on 19/10/14.
 */
public class KupraRestRouteBuilder extends RouteBuilder {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void configure() {
        //TODO Create my own component to factorize

        log.info("MRCRestRouteBuilder Init - Starting");
        //getContext().getRegistry().lookupByName("myServerBean")
        onException(CamelMongoDbException.class).handled(true).process(
                new KupraErrorProcessor());
        errorHandler(loggingErrorHandler(log).level(LoggingLevel.ERROR));
        // configure we want to use servlet as the component for the rest DSL
        // and we enable json binding mode
        restConfiguration().component("servlet").bindingMode(RestBindingMode.off)
                // and output using pretty print
                .dataFormatProperty("prettyPrint", "true")
                        // setup context path and port number that Apache Tomcat will deploy
                        // this application with, as we use the servlet component, then we
                        // need to aid Camel to tell it these details so Camel knows the url
                        // to the REST services.
                        // Notice: This is optional, but needed if the RestRegistry should
                        // enlist accurate information. You can access the RestRegistry
                        // from JMX at runtime
                .contextPath("kupra/rest").port(8080);

        final String mongoDBRoute = "mongodb:serverMongoDB?database=test&collection=test&operation=%s&dynamicity=true";

        rest("/{CamelMongoDbDatabase}").description("Database operations").consumes("exchange").produces("application/json")
                .get("/").description("Get database stats")
                    .route().process(new KupraProcessor()).to(String.format(mongoDBRoute, MongoDbOperation.getDbStats)).process(new KupraOutProcessor()).endRest()
                .get("/{CamelMongoDbCollection}").description("Endpoit to perfom a find, aggregate, count or getColStat on a collection")
                    .route().process(new KupraProcessor()).choice()
                        .when(header("Count").isNotNull()).to(String.format(mongoDBRoute, MongoDbOperation.count))
                        .when(header("Aggregate").isNotNull()).to(String.format(mongoDBRoute, MongoDbOperation.aggregate))
                        .when(header("Colstats").isNotNull()).to(String.format(mongoDBRoute, MongoDbOperation.getColStats))
                        .otherwise().to(String.format(mongoDBRoute, MongoDbOperation.findAll))
                    .endChoice().process(new KupraOutProcessor()).endRest()
                .get("/{CamelMongoDbCollection}/{id}").description("Find by id on given collection")
                    .route().process(new KupraProcessor()).to(String.format(mongoDBRoute, MongoDbOperation.findById)).process(new KupraOutProcessor()).endRest()
                .post("/{CamelMongoDbCollection}").description("Content of body is insert in collection.")
                    .route().process(new KupraProcessor()).to(String.format(mongoDBRoute, MongoDbOperation.insert)).process(new KupraOutProcessor()).onException(CamelMongoDbException.class).handled(true).process(
                new KupraErrorProcessor()).endRest()
                .put("/{CamelMongoDbCollection}").description("Update operations. Body should contain an array with the find critera and the update operation.")
                    .route().process(new KupraProcessor()).to(String.format(mongoDBRoute, MongoDbOperation.update)).process(new KupraOutProcessor()).endRest()
                .put("/{CamelMongoDbCollection}/{id}").description("Replace a document. Request body should contain the new doc.").route()
                    .process(new KupraProcessor()).to(String.format(mongoDBRoute, MongoDbOperation.save)).process(new KupraOutProcessor()).endRest()
                .delete("/{CamelMongoDbCollection}").description("Perform a delete on collection")
                    .route().process(new KupraProcessor()).to(String.format(mongoDBRoute, MongoDbOperation.remove)).process(new KupraOutProcessor()).endRest()
                .delete("/{CamelMongoDbCollection}/{id}").description("Delete a document by his id")
                    .route().process(new KupraProcessor()).to(String.format(mongoDBRoute, MongoDbOperation.remove)).process(new KupraOutProcessor()).endRest()

        ;
        log.info("MRCRestRouteBuilder Init - Done");

    }


}
