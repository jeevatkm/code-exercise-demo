Demo Application
----------------

Demo application using Spring Boot framework. Since it's one of the widely recognized open source framework in the industry. 
Particularly [Sprint Boot](http://projects.spring.io/spring-boot/) provides OPS ready capability libraries. So I have given my favor to this. 
Application uses following libraries-

<pre></code>compile('org.springframework.boot:spring-boot-starter-data-mongodb',
        'org.springframework.boot:spring-boot-starter-security',
        'org.springframework.boot:spring-boot-starter-actuator',
        'org.springframework.boot:spring-boot-starter-web')</code></pre>

REST Endpoints
--------------
Appliation implementated with following endpoints. Supports only JSON message format.

**Token**

HTTP Verb | Endpoint URI
--------- | ------------
POST | http://localhost:8080/token

**Account**

Supports filter option on profession, city, zipCode attributes and Pagination (page, count, sort, order). 
Should be passed as query param. Group by option is not implemented.

HTTP Verb | Endpoint URI
--------- | ------------
GET | http://localhost:8080/accounts
POST | http://localhost:8080/accounts
GET | http://localhost:8080/accounts/{id}
UPDATE | http://localhost:8080/accounts/{id}
DELETE | http://localhost:8080/accounts/{id}
GET | http://localhost:8080/accounts?filter=true&city=los%angeles&profession=software%20engineer
GET | http://localhost:8080/accounts?page=2&count=1&sort=username&order=DESC
GET | http://localhost:8080/accounts?filter=true&city=los%angeles&profession=software%20engineer&page=0&count=1&sort=profession

**File System**

HTTP Verb | Endpoint URI
--------- | ------------
GET | http://localhost:8080/fs
GET | http://localhost:8080/fs?rp=build

**App Health**

Implemented via built-in capabilities of spring boot actuator libraries. Management endpoint can be access via `localhost:9001`

HTTP Verb | Endpoint URI
--------- | ------------
GET | http://localhost:9001/health

Enpoint Versioning
------------------

In the industry various implementation concept floating around. From my point view nothing is wrong, I would its trade-off by organization/team.

* Content Negotiation via Header {Accept, Content-Type}
* Version info in endpoint path

For REST API versioning, there are many debates around the internet, I personally perfer `Content Negotiation` approach.

Test Case
---------

Demo application has a Integration test case. Test report is at -

<pre><code>&lt;PROJECT_DIR>/build/reports/tests/classes/com.demo.exercise.it.DemoApplicationIntegrationTest.html</code></pre>
