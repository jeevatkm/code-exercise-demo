Build & Deployment Instructions
-------------------------------

Please refer README.md file, before you begin here. Demo application configured with default settings.

Clone Source Code
-----------------

Clone source code via command line or using your favorite Git client.

<pre><code>SSH URI:
git clone git@github.com:jeevatkm/code-exercise-demo.git

HTTPS URI:
git clone https://github.com/jeevatkm/code-exercise-demo.git
</code></pre>


Configure MongoDB URI
---------------------

In application.properties or supply it via JVM agrs. Default is `localhost:27017`

<pre><code>#spring.data.mongodb.uri: mongodb://localhost:27017</code></pre>

Build
-----
Fire a `./gradlew` with appropriate commands

<pre><code>$ cd &lt;PROJECT_DIR>
$ ./gradle clean build
</code></pre>

Deployment
----------

Spring Boot comes with embedded app server/servlet container, for this project Tomcat 8.x. 
So we can extract artifact of `code-exercise-demo.zip` OR `code-exercise-demo.tar` from `<PROJECT_DIR>/build/distributions` into
deployment box/local machine.

<pre><code>&lt;EXTRACTED_PATH>/code-exercise-demo/bin/code-exercise-demo</code></pre>

OR we can run it right there, like 

<pre><code>./gradlew clean build && java -jar build/libs/code-exercise-demo-0.0.1-SNAPSHOT.jar</pre></code>


Endpoints
---------

Refer README.md
