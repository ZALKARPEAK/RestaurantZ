package com.example.restaurantz.RestaurantTest;

import com.example.restaurantz.RestaurantZApplication;
import com.example.restaurantz.dto.Authentication.SignInRequest;
import com.example.restaurantz.dto.Restaurant.RestaurantRequest;
import com.example.restaurantz.dto.Restaurant.RestaurantResponse;
import com.example.restaurantz.dto.SimpleResponse;
import com.example.restaurantz.enums.RestType;
import com.example.restaurantz.service.RestaurantService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = RestaurantZApplication.class)
public class RestaurantApiTest {
    private static final String BASE_URI = "http://localhost:8080/restaurant";
    private static final String AUTH_BASE_URI = "http://localhost:8080/api/auth";
    private static final String ADMIN_EMAIL = "admin@gmail.com";
    private static final String ADMIN_PASSWORD = "$2a$10$CrgB8WzwXIalNNwAEjBpe.kglsSP0y06wY2qa2TvMMXS1hU3p.Bzm";

    @Mock
    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantApiTest(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Test
    public void testRestaurantCreationWithAuthentication() {
        String authToken = given()
                .contentType(ContentType.JSON)
                .body(new SignInRequest(ADMIN_EMAIL, ADMIN_PASSWORD))
                .when()
                .post(AUTH_BASE_URI + "/signIn")
                .then()
                .statusCode(200)
                .extract()
                .path("token");

        RestaurantRequest restaurantRequest = new RestaurantRequest("Lamd", "AS", RestType.CASUAL);

        SimpleResponse simpleResponse = given()
                .auth().oauth2(authToken)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(restaurantRequest)
                .when()
                .post(BASE_URI + "/create")
                .then()
                .statusCode(201)
                .log().all()
                .extract()
                .as(SimpleResponse.class);

        assertEquals("Restaurant created successfully", simpleResponse.getMessage());
        assertEquals(HttpStatus.CREATED, simpleResponse.getHttpStatus());
    }

    @Test
    public void getAllRestaurants(){
        String authToken = given()
                .contentType(ContentType.JSON)
                .body(new SignInRequest(ADMIN_EMAIL, ADMIN_PASSWORD))
                .when()
                .post(AUTH_BASE_URI + "/signIn")
                .then()
                .statusCode(200)
                .extract()
                .path("token");

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .auth().oauth2(authToken)
                .when()
                .get(BASE_URI + "/all")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));

    }

    @Test
    public void getRestaurantById() {
        long restaurantId = 3;
        RestaurantResponse mockedResponse = new RestaurantResponse();
        mockedResponse.setName("Kas");
        mockedResponse.setNumberOfEmployees(0);
        mockedResponse.setPosition("Kanada");
        mockedResponse.setRestType(RestType.PRESTIGIOUS);
        mockedResponse.setService(0);

        when(restaurantService.getRestaurantById(restaurantId)).thenReturn(mockedResponse);

        String authToken = given()
                .contentType(ContentType.JSON)
                .body(new SignInRequest(ADMIN_EMAIL, ADMIN_PASSWORD))
                .when()
                .post(AUTH_BASE_URI + "/signIn")
                .then()
                .statusCode(200)
                .extract()
                .path("token");

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .auth().oauth2(authToken)
                .when()
                .get(BASE_URI + "/{restaurantId}", restaurantId)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void updateRestaurant() {
        Long restaurantId = 3L;
        RestaurantRequest updatedRestaurant = new RestaurantRequest("UpdatedName", "USA", RestType.CASUAL);

        SimpleResponse mockedResponse = new SimpleResponse();
        mockedResponse.setMessage("Restaurant updated successfully");
        mockedResponse.setHttpStatus(HttpStatus.OK);

        when(restaurantService.updateRestaurant(eq(restaurantId), any(RestaurantRequest.class)))
                .thenReturn(mockedResponse);

        String authToken = given()
                .contentType(ContentType.JSON)
                .body(new SignInRequest(ADMIN_EMAIL, ADMIN_PASSWORD))
                .when()
                .post(AUTH_BASE_URI + "/signIn")
                .then()
                .statusCode(200)
                .extract()
                .path("token");

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .auth().oauth2(authToken)
                .body(updatedRestaurant)
                .when()
                .put(BASE_URI + "/update/{restaurantId}", restaurantId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo("Restaurant update successfully"));
    }

    @Test
    public void deleteRestaurant(){
        Long restaurantId = 20L;

        SimpleResponse mockedResponse = new SimpleResponse();
        mockedResponse.setMessage("Restaurant deleted successfully");
        mockedResponse.setHttpStatus(HttpStatus.OK);

        when(restaurantService.deleteRestaurant(eq(restaurantId)))
                .thenReturn(mockedResponse);

        String authToken = given()
                .contentType(ContentType.JSON)
                .body(new SignInRequest(ADMIN_EMAIL, ADMIN_PASSWORD))
                .when()
                .post(AUTH_BASE_URI + "/signIn")
                .then()
                .statusCode(200)
                .extract()
                .path("token");

        given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .auth().oauth2(authToken)
                .when()
                .delete(BASE_URI + "/delete/{restaurantId}", restaurantId)
                .then()
                .statusCode(200)
                .body("message", equalTo("Restaurant deleted successfully"));
    }
}