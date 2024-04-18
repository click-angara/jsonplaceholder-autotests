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
    public void listPost() throws Exception {
        Long id = 3L;
        Response response = postsApiService.getPosts();

        PostModel responseBody = Stream.of(response.getBody().as(PostModel[].class))
                .filter(post -> post.getId().equals(3L))
                .findAny()
                .orElseThrow(() -> new Exception(
                        String.format("Not found post with id = %s in response", id)));

        PostModel expectedResponseId3 = PostModel.builder()
                .userId(1L)
                .id(3L)
                .title("ea molestias quasi exercitationem repellat qui ipsa sit aut")
                .body("""
                        et iusto sed quo iure
                        voluptatem occaecati omnis eligendi aut ad
                        voluptatem doloribus vel accusantium quis pariatur
                        molestiae porro eius odio et labore et velit aut""")
                .build();

        Assertions.assertAll(
                () -> assertThat(response.getStatusCode(), equalTo(OK)),
                () -> assertThat(responseBody, equalTo(expectedResponseId3)));
    }

    @Test
    public void lengthListPost() {
        int expectedLength = 100;

        Response response = postsApiService.getPosts();
        int lengthResponseBody = response.getBody().as(PostModel[].class).length;

        Assertions.assertAll(
                () -> assertThat(response.getStatusCode(), equalTo(OK)),
                () -> assertThat(lengthResponseBody, equalTo(expectedLength)));
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
                () -> assertThat(responseBody, equalTo(expectedResponse)));
    }

    @Test
    public void existCommentsById() throws Exception {
        Long postId = 1L;
        Long id = 5L;

        Response response = postsApiService.getPostsCommentsById(postId);
        CommentModel responseBody = Stream.of(response.getBody().as(CommentModel[].class))
                .filter(comment -> comment.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new Exception(
                        String.format("Not found comment with id = %s in response", id)));

        CommentModel expectedResponseId5 = CommentModel.builder()
                .postId(postId)
                .id(id)
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
                () -> assertThat(responseBody, equalTo(expectedResponseId5)));
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