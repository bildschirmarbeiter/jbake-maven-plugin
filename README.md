# JBake Maven Plugin

[Maven](https://maven.apache.org) plugin for baking and serving sites with [JBake](http://jbake.org) and [Jetty](https://eclipse.org/jetty/).

Add `jbake-maven-plugin` to build section in POM:

      <build>
        <plugins>
          <plugin>
            <groupId>de.bildschirmarbeiter.jbake</groupId>
            <artifactId>jbake-maven-plugin</artifactId>
            <version>0.0.2</version>
            <executions>
              <execution>
                <goals>
                  <goal>bake</goal>
                </goals>
              </execution>
            </executions>
            <dependencies>
              <dependency>
                <groupId>org.thymeleaf</groupId>
                <artifactId>thymeleaf</artifactId>
                <version>3.0.7.RELEASE</version>
              </dependency>
            </dependencies>
          </plugin>
        </plugins>
      </build>

Baking is done during phase _process-resources_ by default.


## Goals

### Bake

Will _bake_ the site once.

    mvn jbake:bake


### Watch

Will _bake_ the site and _watch_ for changes in source, baking again on any change.

    mvn jbake:watch


### Serve

Will _bake_ the site, _watch_ for changes in source (baking again on any change) and _serve_ the site with Jetty.

    mvn jbake:serve


## Configuration

| parameter          | default          | goals            | description                                                                                                                                                                                                  |
| ------------------ | ---------------- | ---------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| `source`           | `src/main/jbake` | _all_            | The source directory where `assets`, `content` `templates` and `jbake.properties` are stored.<br>Parameter is passed to [JBake Oven](http://jbake.org/docs/2.5.1/#api).                                      |
| `destination`      | `target/jbake`   | _all_            | The destination directory where the baked site is written to.<br>Parameter is passed to [JBake Oven](http://jbake.org/docs/2.5.1/#api).                                                                      |
| `clearCache`       | `true`           | _all_            | The [local cache](http://jbake.org/docs/2.5.1/#persistent_content_store) is cleared when `true`.<br>Parameter is passed to [JBake Oven](http://jbake.org/docs/2.5.1/#api).                                   |
| `cleanDestination` | `true`           | `watch`, `serve` | All files in destination directory are removed when `true` before baking. This prevents orphaned files, but requires a full bake (see [local cache](http://jbake.org/docs/2.5.1/#persistent_content_store)). |
| `failOnError`      | `true`           | _all_            | Breaks the build when `true` and errors occur during baking in JBake Oven.                                                                                                                                   |
| `port`             | `8080`           | `serve`          | The HTTP port Jetty is listening on.<br>Parameter is passed to Jetty Server.                                                                                                                                 |

All parameters can be set on command line with prefix `jbake` as usual, e.g.:

    mvn jbake:serve -Djbake.source=src/main/resources -Djbake.destination=target/site -Djbake.port=8181

**For details see [JBake's documentation](http://jbake.org/docs/2.5.1/#usage).**
