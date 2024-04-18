package api;

import io.restassured.response.Response;
import org.example.api.services.PostsApiService;
import org.example.model.PostModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.example.api.constant.StatusCode.CREATED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UpdatePostsTests {
    private static PostsApiService postsApiService;

    @BeforeAll
    static void beforeAll() {
        postsApiService = new PostsApiService();
    }

    @Test
    public void successUpdatePosts() {
        PostModel request = PostModel.builder()
                .id(2L)
                .userId(101L)
                .title("foo")
                .body("bar")
                .build();

        Response response = postsApiService.postPosts(request);
        PostModel responseBody = response.getBody().as(PostModel.class);

        Assertions.assertAll(
                () -> assertThat(response.getStatusCode(), equalTo(CREATED)),
                () -> assertThat(responseBody, equalTo(request)));
    }
}
