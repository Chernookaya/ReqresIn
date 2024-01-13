package tests;


import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;

public class ReqresAPITest {

    @Test
    public void getListUsers() {
        given()
                .log().all()
        .when()
                .get("https://reqres.in/api/users?page=2")
        .then()
                .log().all()
                .body("total_pages", equalTo(2))
                .body("data[4].email", equalTo("george.edwards@reqres.in"))
                .statusCode(200);

    }

    @Test
    public void getSingleUser() {
        given()
                .log().all()
        .when()
                .get("https://reqres.in/api/users/2")
        .then()
                .log().all()
                .body("data.first_name", equalTo("Janet"))
                .body("data.last_name", equalTo("Weaver"))
                .statusCode(200);
    }

    @Test
    public void getSingleUserNotFound() {
        given()
                .log().all()
        .when()
                .get("https://reqres.in/api/users/23")
        .then()
                .log().all()
                .statusCode(404);

    }

    @Test
    public void getListResourse() {
        given()
                .log().all()
        .when()
                .get("https://reqres.in/api/unknown")
        .then()
                .log().all()
                .body("data[2].color", equalTo("#BF1932"))
                .body("data[4].name", equalTo("tigerlily"))
                .statusCode(200);
    }

    @Test
    public void getSingleResourse() {
        given()
                .log().all()
        .when()
                .get("https://reqres.in/api/unknown/2")
        .then()
                .log().all()
                .body("data.id", equalTo(2))
                .body("support.url", equalTo("https://reqres.in/#support-heading"))
                .statusCode(200);
    }

    @Test
    public void getSingleResourseNotFound() {
        given()
                .log().all()
        .when()
                .get("https://reqres.in/api/unknown/23")
        .then()
                .log().all()
                .statusCode(404);
    }

    @Test
    public void create() {
        given()
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}")
        .when()
                .post("https://reqres.in/api/users")
        .then()
                .log().all()
                .statusCode(201);
    }

    @Test//
    public void putUpdate() {
        String response = given()
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}")
        .when()
                .put("https://reqres.in/api/users/2")
        .then()
                .log().all()
                //.body("name", equalTo("morpheus"))
                .statusCode(200)
                .extract()
                .body().asString();
        System.out.println(response);
    }

    @Test
    public void patchUpdate() {
        String response = given()
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
        .when()
                .patch("https://reqres.in/api/users/2")
        .then()
                .log().all()
                .statusCode(200)
                .extract()
                .body().asString();
        System.out.println(response);
    }

    @Test
    public void delete() {
        given()
        .when()
                .delete("https://reqres.in/api/users/2")
        .then()
                .log().all()
                .statusCode(204);
    }

    @Test
    public void postRegisterSuccessful() {
        given()
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"pistol\"\n" +
                        "}")
                .header("Content-Type", "application/json")
        .when()
                .post("https://reqres.in/api/register")
        .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void postRegisterUnsuccessful() {
        given()
                .body("{\n" +
                        "    \"email\": \"sydney@fife\"\n" +
                        "}")
        .when()
                .post("https://reqres.in/api/register")
        .then()
                .log().all()
                .statusCode(400);
    }

    @Test
    public void postLoginSuccessful() {
        given()
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"cityslicka\"\n" +
                        "}")
                .header("Content-Type", "application/json")
        .when()
                .post("https://reqres.in/api/login")
        .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void postLoginUnsuccessful() {
        given()
                .body("{\n" +
                        "    \"email\": \"peter@klaven\"\n" +
                        "}")
        .when()
                .post("https://reqres.in/api/login")
        .then()
                .log().all()
                .statusCode(400);
    }

    @Test
    public void getDelayedResponse() {
        given()
        .when()
                .get("https://reqres.in/api/users?delay=3")
        .then()
                .log().all()
                .body("support.text", equalTo("To keep ReqRes free, contributions towards server costs are appreciated!"))
                .statusCode(200);
    }
}
