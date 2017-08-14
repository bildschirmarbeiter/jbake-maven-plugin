package de.bildschirmarbeiter.jbake.maven.plugin;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.jbake.app.Oven;

@Mojo(
    name = "bake",
    defaultPhase = LifecyclePhase.PROCESS_RESOURCES
)
public class BakeMojo extends AbstractMojo {

    @Parameter(
        property = "jbake.source",
        defaultValue = "src/main/jbake"
    )
    protected File source;

    @Parameter(
        property = "jbake.destination",
        defaultValue = "target/jbake"
    )
    protected File destination;

    @Parameter(
        property = "jbake.clearCache",
        defaultValue = "true"
    )
    protected boolean clearCache;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Executing Bake Mojo");
        try {
            bake();
        } catch (Exception e) {
            throw new MojoExecutionException("Baking failed", e);
        }
    }

    protected void bake() throws Exception {
        getLog().info(String.format("Baking from %s to %s (clearing cache: %s)", source, destination, clearCache));
        final Oven oven = new Oven(source, destination, clearCache);
        oven.setupPaths();
        oven.bake();
    }

}
