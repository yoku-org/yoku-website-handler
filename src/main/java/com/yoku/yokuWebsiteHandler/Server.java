package com.yoku.yokuWebsiteHandler;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Server {

	// Base URI the Grizzly HTTP server will listen on
	public static final String BASE_URI = "http://0.0.0.0";
	public static final String APP_NAME = "yoku-website-handler";

	private static String getFullURL(String port) {
		return BASE_URI + ":" + port + "/" + APP_NAME + "/";
	}

	public static HttpServer startServer(String port) {
		// create a resource config that scans for JAX-RS resources and
		// providers
		// in package
		final ResourceConfig rc = new ResourceConfig().packages("com.yoku.yokuWebsiteHandler");

		// create and start a new instance of grizzly http server
		// exposing the Jersey application at BASE_URI
		return GrizzlyHttpServerFactory.createHttpServer(URI.create(getFullURL(port)), rc);
	}

	public static void main(String[] args) throws IOException {

		if (args.length < 2) {
			System.out.println("Usage: Server <port> <mysql_password>");
			return;
		}

		final HttpServer server = startServer(args[0]);

		new NotifyApi(args[1]);

		System.out.println(String.format(
				"Jersey app started with WADL available at " + "%sapplication.wadl\nHit enter to stop it...",
				getFullURL(args[0])));

		System.in.read();

		server.shutdown();
	}
}
