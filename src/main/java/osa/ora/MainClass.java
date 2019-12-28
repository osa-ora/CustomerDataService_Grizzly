/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osa.ora;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import java.util.Set;
import org.glassfish.jersey.server.model.Resource;
import static osa.ora.MainClass.startServer;

/**
 *
 * @author ooransa
 */
public class MainClass {

    // Base URI the Grizzly HTTP server will listen on
    // Base URI the Grizzly HTTP server will listen on

    public static String BASE_URI;
    public static String protocol;
    public static Optional<String> host;
    public static String path;
    public static Optional<String> port;
    private static String defaultPortNumber = "8085";

    static {
    }

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this
     * application.
     *
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        protocol = "http://";
        host = Optional.ofNullable(System.getenv("HOSTNAME"));
        port = Optional.ofNullable(System.getenv("PORT"));
        System.out.println("Will listen to port : " + port.orElse(defaultPortNumber));
        path = "CustomersService"; 
        BASE_URI = protocol + host.orElse("localhost") + ":" + port.orElse(defaultPortNumber) + "/" + path + "/";
        // create a resource config that scans for JAX-RS resources and providers
        // in our package
        final ResourceConfig rc = new ResourceConfig().packages("osa.ora");
        Set<Resource> resources = rc.getResources();
        for (Resource resource : resources) {
            System.out.println("Listen to:" + resource.getName());
        }
        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        System.out.println("URL:" + BASE_URI);
        System.out.println("Sample URL : "+BASE_URI+"V1/customers/all");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        if (args.length > 0) {
            defaultPortNumber = args[0];
            System.out.println("Will use port : " + defaultPortNumber);
        }
        System.out.println("Default port : " + defaultPortNumber);
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
}
