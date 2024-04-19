package api;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.example.api.services.PostsApiService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.example.api.constant.StatusCode.OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@DisplayName("Jsonplaceholder API")
@Story("Delete Posts Operations")
public class DeletePostsTests {

    private static PostsApiService postsApiService;

    @BeforeAll
    static void beforeAll() {
        postsApiService = new PostsApiService();
    }

    public static Stream<Arguments> idPostsStream() {
        return Stream.of(
                Arguments.of(1L, "Exist resource"),
                Arguments.of(11234521L, "Not exist resource")
        );
    }


    @AllureId("0002")
    @MethodSource("idPostsStream")
    @ParameterizedTest(name = "Check success response deleting exist resource")
    public void existDelete(Long id, String description) {
        Response response = postsApiService.deletePosts(id);

        Allure.step("Check response DELETE: /posts/{id}", () -> {
            assertThat(response.getStatusCode(), equalTo(OK));
        });
    }

}
