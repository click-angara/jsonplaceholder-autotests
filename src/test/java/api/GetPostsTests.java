package api;

import io.restassured.response.Response;
import org.example.api.services.PostsApiService;
import org.example.model.CommentModel;
import org.example.model.ErrorModel;
import org.example.model.PostModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.example.api.constant.StatusCode.NOT_FOUND;
import static org.example.api.constant.StatusCode.OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class GetPostsTests {


    private static PostsApiService postsApiService;

    @BeforeAll
    static void beforeAll() {
        postsApiService = new PostsApiService();
    }

    @Test
    public void existPostById() {
        Response response = postsApiService.getPostsById(1L);
        PostModel responseBody = response.getBody().as(PostModel.class);

        PostModel expectedResponse = PostModel.builder()
                .userId(1L)
                .id(1L)
                .title("sunt aut facere repellat provident occaecati excepturi optio reprehenderit")
                .body("""
                        quia et suscipit
                        suscipit recusandae consequuntur expedita et cum
                        reprehenderit molestiae ut ut quas totam
                        nostrum rerum est autem sunt rem eveniet architecto""")
                .build();

        Assertions.assertAll(
                () -> assertThat(response.getStatusCode(), equalTo(OK)),
                () -> assertThat(responseBody.getUserId(), equalTo(expectedResponse.getUserId())),
                () -> assertThat(responseBody.getId(), equalTo(expectedResponse.getId())),
                () -> assertThat(responseBody.getTitle(), equalTo(expectedResponse.getTitle())),
                () -> assertThat(responseBody.getBody(), equalTo(expectedResponse.getBody())));
    }

    @Test
    public void existCommentsById() throws Exception {
        Long postId = 1L;

        Response response = postsApiService.getPostsCommentsById(postId);
        CommentModel responseBody = Stream.of(response.getBody().as(CommentModel[].class))
                .filter(comment -> comment.getId().equals(5L))
                .findAny()
                .orElseThrow(() -> new Exception(
                        String.format("Not found comment with id = %s in response", postId)));

        CommentModel expectedResponseId5 = CommentModel.builder()
                .postId(postId)
                .name("vero eaque aliquid doloribus et culpa")
                .email("Hayden@althea.biz")
                .body("""
                        harum non quasi et ratione
                        tempore iure ex voluptates in ratione
                        harum architecto fugit inventore cupiditate
                        voluptates magni quo et""")
                .build();

        Assertions.assertAll(
                () -> assertThat(response.getStatusCode(), equalTo(OK)),
                () -> assertThat(responseBody.getPostId(), equalTo(expectedResponseId5.getPostId())),
                () -> assertThat(responseBody.getName(), equalTo(expectedResponseId5.getName())),
                () -> assertThat(responseBody.getEmail(), equalTo(expectedResponseId5.getEmail())),
                () -> assertThat(responseBody.getBody(), equalTo(expectedResponseId5.getBody())));
    }

    @Test
    public void lengthCommentsById() {
        Long postId = 2L;
        int expectedLength = 5;

        Response response = postsApiService.getPostsCommentsById(postId);
        int lengthResponseBody = response.getBody().as(CommentModel[].class).length;

        Assertions.assertAll(
                () -> assertThat(response.getStatusCode(), equalTo(OK)),
                () -> assertThat(lengthResponseBody, equalTo(expectedLength)));
    }

    @Test
    public void notExistPostById() {
        Response response = postsApiService.getPostsById(1456778L);
        ErrorModel responseBody = response.getBody().as(ErrorModel.class);

        ErrorModel expectedResponse = ErrorModel.builder().build();

        Assertions.assertAll(
                () -> assertThat(response.getStatusCode(), equalTo(NOT_FOUND)),
                () -> assertThat(responseBody, equalTo(expectedResponse))
        );
    }

    @Test
    public void notExistCommentsById() {
        Response response = postsApiService.getPostsCommentsById(1456778L);
        ErrorModel[] responseBody = response.getBody().as(ErrorModel[].class);

        Assertions.assertAll(
                () -> assertThat(response.getStatusCode(), equalTo(OK)),
                () -> assertThat(responseBody, equalTo(new ErrorModel[]{}))
        );
    }

}