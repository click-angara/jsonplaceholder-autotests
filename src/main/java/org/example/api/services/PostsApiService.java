package org.example.api.services;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.model.PostModel;

import static io.restassured.RestAssured.given;


public class PostsApiService {

    private static final String baseUrl = "https://jsonplaceholder.typicode.com";

    public Response getPosts() {
        RestAssured.baseURI = baseUrl;
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("/posts")
                .then()
                .extract().response();
    }

    public Response getPostsById(Long id) {
        RestAssured.baseURI = baseUrl;
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("/posts/" + id)
                .then()
                .extract().response();
    }

    public Response getPostsCommentsById(Long id) {
        RestAssured.baseURI = baseUrl;
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("/posts/" + id + "/comments")
                .then()
                .extract().response();
    }

    public Response postPosts(PostModel postModel) {
        RestAssured.baseURI = baseUrl;
        return given()
                .header("Content-Type", "application/json")
                .body(postModel)
                .when()
                .post("/posts")
                .then()
                .extract().response();
    }


    public Response deletePosts(Long id) {
        RestAssured.baseURI = baseUrl;
        return given()
                .header("Content-type", "application/json")
                .when()
                .delete("/posts/" + id)
                .then()
                .extract().response();
    }

}
