package de.bildschirmarbeiter.jbake.maven.plugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;

@Mojo(
    name = "serve"
)
public class ServeMojo extends WatchMojo {

    @Parameter(
        property = "jbake.port",
        defaultValue = "8080"
    )
    protected Integer port;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Executing Serve Mojo");
        try {
            bake();
            new Thread(this::watch).start();
            serve();
        } catch (Exception e) {
            throw new MojoExecutionException("Serving failed", e);
        }
    }

    protected void serve() throws Exception {
        final ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(destination.getPath());

        final HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{
            resourceHandler,
            new DefaultHandler()
        });

        final Server server = new Server(port);
        server.setHandler(handlers);
        server.start();
        getLog().info("Hit CTRL-C to stop.");
        server.join();
    }

}
