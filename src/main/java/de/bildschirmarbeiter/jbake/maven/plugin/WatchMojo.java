package de.bildschirmarbeiter.jbake.maven.plugin;

import org.apache.commons.vfs2.FileChangeEvent;
import org.apache.commons.vfs2.FileListener;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.impl.DefaultFileMonitor;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(
    name = "watch"
)
public class WatchMojo extends BakeMojo {

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
            bake();
        }

        @Override
        public void fileDeleted(final FileChangeEvent fileChangeEvent) throws Exception {
            getLog().info(String.format("File deleted: %s", fileChangeEvent.getFile().getName()));
            bake();
        }

        @Override
        public void fileChanged(final FileChangeEvent fileChangeEvent) throws Exception {
            getLog().info(String.format("File changed: %s", fileChangeEvent.getFile().getName()));
            bake();
        }

    }

}
