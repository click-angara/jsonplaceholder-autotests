package calculator;

import org.example.api.services.PostsApiService;
import org.example.model.PostModel;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.google.common.collect.Streams;

public class CalculatorCountWords {

    private static final String PREFIX = "txt";

    public static void main(String[] args) {

        List<String> bodyList = Arrays.stream(
                new PostsApiService().getPosts().as(PostModel[].class))
                .map(postModel -> postModel.body).toList();

        System.out.println(PREFIX);
        Stream<String> sortedCountWordsList = getSortedCountWordsList(bodyList);
        sortedCountWordsList.forEach(System.out::println);
    }

    public static Stream<String> getSortedCountWordsList(List<String> bodyList) {
        Stream<Map.Entry<String, Integer>> sortedCountWordsList =  bodyList.stream()
                .flatMap(line -> Stream.of(line.split("\\s+")))
                .map(word -> word.replaceAll("[^a-z]+", ""))
                .collect(Collectors.toMap(key -> key, val -> 1, Integer::sum))
                .entrySet().stream()
                .sorted((word1, word2) -> word1.getValue().compareTo(word2.getValue()) * -1);

        return  Streams.mapWithIndex(sortedCountWordsList, (word, index) ->
                String.format("%s. %s - %s", index+1, word.getKey(), word.getValue()));
    }

}
