# JBake Maven Plugin

[Maven](https://maven.apache.org) plugin for baking and serving sites with [JBake](http://jbake.org) and [Jetty](https://eclipse.org/jetty/).

Add `jbake-maven-plugin` to build section in POM:

      <build>
        <plugins>
          <plugin>
            <groupId>de.bildschirmarbeiter.jbake</groupId>
            <artifactId>jbake-maven-plugin</artifactId>
            <version>0.0.1-SNAPSHOT</version>
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
                <version>3.0.6.RELEASE</version>
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

| parameter     | default          | goals   |
| ------------- | ---------------- | ------- |
| `source`      | `src/main/jbake` | _all_   |
| `destination` | `target/jbake`   | _all_   |
| `clearCache`  | `true`           | _all_   |
| `port`        | `8080`           | `serve` |

All parameters can be set on command line with prefix `jbake` as usual, e.g.:

    mvn jbake:serve -Djbake.source=src/main/resources -Djbake.destination=target/site -Djbake.port=8181

**For details see [JBake's documentation](http://jbake.org/docs/2.5.1/#usage).**
