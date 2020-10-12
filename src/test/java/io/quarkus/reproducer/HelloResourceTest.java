package io.quarkus.reproducer;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class HelloResourceTest {

    @Test
    void log4jLoggerShouldSucceed() {
        given()
          .when().get("/hello/log4j")
          .then()
             .statusCode(200)
             .body(is("log4j"));
    }

    @Test
    void jbossLoggerShouldSucceed() {
        given()
          .when().get("/hello/jboss")
          .then()
             .statusCode(200)
             .body(is("jboss"));
    }

}