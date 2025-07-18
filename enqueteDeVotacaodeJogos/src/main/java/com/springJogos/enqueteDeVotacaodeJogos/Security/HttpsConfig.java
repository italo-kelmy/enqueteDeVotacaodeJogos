package com.springJogos.enqueteDeVotacaodeJogos.Security;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpsConfig {

    @Bean
    public ServletWebServerFactory serverFactory() {
        TomcatServletWebServerFactory tomCat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint constraint = new SecurityConstraint();
                constraint.setUserConstraint("CONFIDENTIAL");

                SecurityCollection collection = new SecurityCollection();
                collection.findPattern("/*");
                constraint.addCollection(collection);
                context.addConstraint(constraint);
            }
        };

        tomCat.addAdditionalTomcatConnectors(httpsConnection());
        return tomCat;
    }

    @Bean
    public Connector httpsConnection() {
        Connector connector = new Connector();
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setSecure(false);
        connector.setRedirectPort(8443);

        return connector;
    }

}
