package de.bildschirmarbeiter.jbake.maven.plugin;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.vfs2.FileChangeEvent;
import org.apache.commons.vfs2.FileListener;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.impl.DefaultFileMonitor;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(
    name = "watch"
)
public class WatchMojo extends BakeMojo {

    @Parameter(
        property = "jbake.cleanDestination",
        defaultValue = "true"
    )
    protected boolean cleanDestination;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Executing Watch Mojo");
        try {
            bake();
            watch();
        } catch (Exception e) {
            throw new MojoExecutionException("Watching failed", e);
        }
    }

    protected void watch() {
        getLog().info(String.format("Watching in %s for file changes", source));
        try {
            final FileSystemManager fileSystemManager = VFS.getManager();
            final FileObject source = fileSystemManager.resolveFile(this.source.getAbsolutePath());
            final DefaultFileMonitor fileMonitor = new DefaultFileMonitor(new SourceFileListener());
            fileMonitor.setRecursive(true);
            fileMonitor.addFile(source);
            fileMonitor.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private final class SourceFileListener implements FileListener {

        @Override
        public void fileCreated(final FileChangeEvent fileChangeEvent) throws Exception {
            getLog().info(String.format("File created: %s", fileChangeEvent.getFile().getName()));
            onFileChange();
        }

        @Override
        public void fileDeleted(final FileChangeEvent fileChangeEvent) throws Exception {
            getLog().info(String.format("File deleted: %s", fileChangeEvent.getFile().getName()));
            onFileChange();
        }

        @Override
        public void fileChanged(final FileChangeEvent fileChangeEvent) throws Exception {
            getLog().info(String.format("File changed: %s", fileChangeEvent.getFile().getName()));
            onFileChange();
        }

    }

    private void onFileChange() throws Exception {
        if (cleanDestination) {
            cleanDestination();
        }
        bake();
    }

    private void cleanDestination() throws IOException {
        getLog().info(String.format("Cleaning destination %s", destination));
        final long start = System.currentTimeMillis();
        FileUtils.cleanDirectory(destination);
        final long end = System.currentTimeMillis();
        getLog().info(String.format("Cleaned destination %s in %sms", destination, end - start));
    }

}
