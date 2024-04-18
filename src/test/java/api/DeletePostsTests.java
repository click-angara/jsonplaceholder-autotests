package api;

import io.restassured.response.Response;
import org.example.api.services.PostsApiService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.example.api.constant.StatusCode.OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DeletePostsTests {

    private static PostsApiService postsApiService;

    @BeforeAll
    static void beforeAll() {
        postsApiService = new PostsApiService();
    }

    @Test
    public void existDelete() {
        Response response = postsApiService.deletePosts(1L);

        assertThat(response.getStatusCode(), equalTo(OK));
    }

    @Test
    public void notExistDelete() {
        Response response = postsApiService.deletePosts(11234521L);

        assertThat(response.getStatusCode(), equalTo(OK));
    }

}
