package org.kupra.restserver;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.apache.camel.component.metrics.routepolicy.MetricsRoutePolicyFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;
import java.util.Set;

/**
 * Created by pierrealban on 24/10/14.
 */
@Configuration
@ComponentScan(basePackages = "org.kupra.restserver")
public class KupraConfiguration implements InitializingBean {

    @Bean(name = "serverMongoDB")
    public Mongo getMongoDb() {
        try {
            String mongoDBUrl = System.getenv("MONGODB_URL");
            if(mongoDBUrl == null) {
                mongoDBUrl = System.getProperty("MONGODB_URL");
                if(mongoDBUrl == null) {
                    mongoDBUrl = "mongodb://127.0.0.1/test";
                }
            }
            System.out.println("Kupra is running with url :"+mongoDBUrl);
            MongoClient ret = new MongoClient(new MongoClientURI(mongoDBUrl));
            Set<String> collections = ret.getDB("test").getCollectionNames();
            return ret;
        } catch (UnknownHostException e) {
            //FIXME Totaly ugly
            e.printStackTrace();
            return null;
        }
    }



    @Bean(name = "userRoutes")
    public KupraRestRouteBuilder getUserRoutes() {
        return new KupraRestRouteBuilder();
    }

    @Bean
    public MetricsRoutePolicyFactory getMetricsRoutePolicyFactory(){
        return new MetricsRoutePolicyFactory();
    }

    public void afterPropertiesSet() throws Exception {

    }
}
