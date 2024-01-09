package com.example.restaurantz.RestaurantTest;

import com.example.restaurantz.RestaurantZApplication;
import com.example.restaurantz.dto.Authentication.SignInRequest;
import com.example.restaurantz.dto.Category.CategoryRequest;
import com.example.restaurantz.dto.Category.CategoryResponse;
import com.example.restaurantz.dto.Category.PaginationResponseCategory;
import com.example.restaurantz.dto.SimpleResponse;
import com.example.restaurantz.entity.Category;
import com.example.restaurantz.service.CategoryService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = RestaurantZApplication.class)
public class CategoryApiTest {

    private static final String BASE_URI = "http://localhost:8080/category";
    private static final String AUTH_BASE_URI = "http://localhost:8080/api/auth";
    private static final String ADMIN_EMAIL = "admin@gmail.com";
    private static final String ADMIN_PASSWORD = "$2a$10$CrgB8WzwXIalNNwAEjBpe.kglsSP0y06wY2qa2TvMMXS1hU3p.Bzm";

    @Mock
    private final CategoryService categoryService;

    @Autowired
    public CategoryApiTest(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Test
    public void getAllCategories() {
        int page = 1;
        int size = 5;

        when(categoryService.findAll(anyInt(), anyInt())).thenReturn(
                new PaginationResponseCategory(
                        Collections.singletonList(new CategoryResponse(1L, "Breakfast Items")),
                        1,
                        1
                )
        );

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
                .param("pageSize", size)
                .param("currentPage", page)
                .when()
                .get(BASE_URI + "/all")
                .then()
                .statusCode(200);
    }
    
    @Test
    public void saveCategory(){
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName("Make");

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
                .body(categoryRequest)
                .when()
                .post(BASE_URI + "/save")
                .then()
                .statusCode(200)
                .extract()
                .as(SimpleResponse.class);


    }

    @Test
    public void getCategoryById(){
        Long categoryId = 1L;
        CategoryResponse categoryResponse = new CategoryResponse(categoryId, "AS");

        String authToken = given()
                .contentType(ContentType.JSON)
                .body(new SignInRequest(ADMIN_EMAIL, ADMIN_PASSWORD))
                .when()
                .post(AUTH_BASE_URI + "/signIn")
                .then()
                .statusCode(200)
                .extract()
                .path("token");

        when(categoryService.findById(categoryId)).thenReturn(categoryResponse);

        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .auth().oauth2(authToken)
                .when()
                .get(BASE_URI + "/{id}", categoryId)
                .then()
                .statusCode(200);
    }

    @Test
    public void updateCategory(){
        Long updateCategoryId = 1L;

        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName("New");

        when(categoryService.update(updateCategoryId, categoryRequest)).
                thenReturn(SimpleResponse.builder().httpStatus(HttpStatus.OK).build());

        String authToken = given().contentType(ContentType.JSON)
                .body(new SignInRequest(ADMIN_EMAIL,ADMIN_PASSWORD))
                .when()
                .post(AUTH_BASE_URI + "/signIn")
                .then()
                .statusCode(200)
                .extract()
                .path("token");

        given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .auth().oauth2(authToken)
                .body(categoryRequest)
                .when()
                .put(BASE_URI + "/update/{id}", updateCategoryId)
                .then()
                .statusCode(200);
    }

    @Test
    public void deleteCategoryById() {
        Long deleteCategoryId = 28L;


        String authToken = given().contentType(ContentType.JSON)
                .body(new SignInRequest(ADMIN_EMAIL,ADMIN_PASSWORD))
                .when()
                .post(AUTH_BASE_URI + "/signIn")
                .then()
                .statusCode(200)
                .extract()
                .path("token");

        Category originalCategory = getCategoryById(deleteCategoryId, authToken);

        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .auth().oauth2(authToken)
                .when()
                .delete(BASE_URI + "/delete/{id}", deleteCategoryId)
                .then()
                .statusCode(200);

        restoreCategory(originalCategory, authToken);
    }
    private Category getCategoryById(Long categoryId, String authToken) {
        return given().accept(ContentType.JSON)
                .auth().oauth2(authToken)
                .when()
                .get(BASE_URI + "/{id}", categoryId)
                .then()
                .statusCode(200)
                .extract()
                .as(Category.class);
    }
    private void restoreCategory(Category category, String authToken) {
        given().contentType(ContentType.JSON)
                .auth().oauth2(authToken)
                .body(category)
                .when()
                .post(BASE_URI + "/save")
                .then()
                .statusCode(200);
    }
}
