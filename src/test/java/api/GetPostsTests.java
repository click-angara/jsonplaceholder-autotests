package api;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.example.api.services.PostsApiService;
import org.example.model.CommentModel;
import org.example.model.ErrorModel;
import org.example.model.PostModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.example.api.constant.StatusCode.NOT_FOUND;
import static org.example.api.constant.StatusCode.OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Jsonplaceholder API")
@Story("Get Posts Operations")
public class GetPostsTests {

    private static PostsApiService postsApiService;

    @BeforeAll
    static void beforeAll() {
        postsApiService = new PostsApiService();
    }

    @Test
    @AllureId("0004")
    @DisplayName("Check success response body element listing all resources")
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

        Allure.step("Check response GET: /posts", () -> {
            Assertions.assertAll(
                    () -> assertThat(response.getStatusCode(), equalTo(OK)),
                    () -> assertThat(responseBody, equalTo(expectedResponseId3)));
        });
    }

    @Test
    @AllureId("0005")
    @DisplayName("Check success length listing all resources")
    public void lengthListPost() {
        int expectedLength = 100;

        Response response = postsApiService.getPosts();
        int lengthResponseBody = response.getBody().as(PostModel[].class).length;

        Allure.step("Check response GET: /posts", () -> {
            Assertions.assertAll(
                    () -> assertThat(response.getStatusCode(), equalTo(OK)),
                    () -> assertThat(lengthResponseBody, equalTo(expectedLength)));
        });
    }

    @Test
    @AllureId("0006")
    @DisplayName("Check success response getting exist resource")
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

        Allure.step("Check response GET: /posts/{id}", () -> {
            Assertions.assertAll(
                    () -> assertThat(response.getStatusCode(), equalTo(OK)),
                    () -> assertThat(responseBody, equalTo(expectedResponse)));
        });
    }

    @Test
    @AllureId("0007")
    @DisplayName("Check success response getting comments exist resource")
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

        Allure.step("Check response GET: /posts/{id}", () -> {
            Assertions.assertAll(
                    () -> assertThat(response.getStatusCode(), equalTo(OK)),
                    () -> assertThat(responseBody, equalTo(expectedResponseId5)));
        });
    }

    @Test
    @AllureId("0008")
    @DisplayName("Check success length getting comments resources")
    public void lengthCommentsById() {
        Long postId = 2L;
        int expectedLength = 5;

        Response response = postsApiService.getPostsCommentsById(postId);
        int lengthResponseBody = response.getBody().as(CommentModel[].class).length;

        Allure.step("Check response GET: /posts/{id}/comments", () -> {
            Assertions.assertAll(
                    () -> assertThat(response.getStatusCode(), equalTo(OK)),
                    () -> assertThat(lengthResponseBody, equalTo(expectedLength)));
        });
    }

    @Test
    @AllureId("0009")
    @DisplayName("Check success response getting not exist post resources")
    public void notExistPostById() {
        Response response = postsApiService.getPostsById(1456778L);
        ErrorModel responseBody = response.getBody().as(ErrorModel.class);

        ErrorModel expectedResponse = ErrorModel.builder().build();

        Allure.step("Check response GET: /posts/{id}", () -> {
            Assertions.assertAll(
                    () -> assertThat(response.getStatusCode(), equalTo(NOT_FOUND)),
                    () -> assertThat(responseBody, equalTo(expectedResponse))
            );
        });
    }

    @Test
    @AllureId("0010")
    @DisplayName("Check success  response getting not exist comments resources")
    public void notExistCommentsById() {
        Response response = postsApiService.getPostsCommentsById(1456778L);
        ErrorModel[] responseBody = response.getBody().as(ErrorModel[].class);

        Allure.step("Check response GET: /posts/{id}/comments", () -> {
            Assertions.assertAll(
                    () -> assertThat(response.getStatusCode(), equalTo(OK)),
                    () -> assertThat(responseBody, equalTo(new ErrorModel[]{}))
            );
        });
    }

}