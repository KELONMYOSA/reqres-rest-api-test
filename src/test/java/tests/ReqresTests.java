package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.get;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.assertj.core.api.Assertions.assertThat;

public class ReqresTests {

    @Test
    void usersListTest() {
        get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .body("data.id", hasItems(7, 8, 9, 10, 11, 12));
    }

    @Test
    void successfulRegisterTest() {

        String registrationData = "{ \"email\": \"eve.holt@reqres.in\", " +
                "\"password\": \"pistol\" }";

        given()
                .body(registrationData)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void successfulLoginTest() {

        String authorizedData = "{\"email\": \"eve.holt@reqres.in\", " +
                "\"password\": \"cityslicka\"}";

        given()
                .body(authorizedData)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void createUserTest() {

        String userData = "{ \"name\": \"morpheus\", " +
                "\"job\": \"leader\" }";

        Response response = given()
                .body(userData)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .extract().response();

        String name = response.path("name");
        String job = response.path("job");

        assertThat(name).isEqualTo("morpheus");
        assertThat(job).isEqualTo("leader");
    }

    @Test
    void updateDataTest() {

        String updateData = "{ \"name\": \"morpheus\", " +
                "\"job\": \"zion resident\" }";

        Response response = given()
                .body(updateData)
                .contentType(JSON)
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .extract().response();

        String name = response.path("name");
        String job = response.path("job");

        assertThat(name).isEqualTo("morpheus");
        assertThat(job).isEqualTo("zion resident");
    }
}
