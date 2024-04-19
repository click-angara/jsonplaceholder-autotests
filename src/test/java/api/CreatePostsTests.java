package api;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.example.api.services.PostsApiService;
import org.example.model.PostModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.example.api.constant.StatusCode.CREATED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Jsonplaceholder API")
@Story("Create Posts Operations")
public class CreatePostsTests {
    private static PostsApiService postsApiService;

    @BeforeAll
    static void beforeAll() {
        postsApiService = new PostsApiService();
    }

    @Test
    @AllureId("0001")
    @DisplayName("Check success response crating resources")
    public void successAddPosts() {
        PostModel request = PostModel.builder()
                .userId(11L)
                .title("foo")
                .body("bar")
                .build();

        Response response = postsApiService.postPosts(request);
        PostModel responseBody = response.getBody().as(PostModel.class);

        Allure.step("Check response POST: /posts", () -> {
            Assertions.assertAll(
                    () -> assertThat(response.getStatusCode(), equalTo(CREATED)),
                    () -> assertThat(responseBody.getUserId(), equalTo(request.getUserId())),
                    () -> assertThat(responseBody.getTitle(), equalTo(request.getTitle())),
                    () -> assertThat(responseBody.getBody(), equalTo(request.getBody())));
        });

    }

}
