package cscie55.hw7;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MappingTest2 {

    public static void main(String[] args) {
        Map<Integer, List<String>> len2StringMap = Arrays.asList("Java", "Core Java", "Java", "Streams", "Java","Example")
                .stream()
                .collect(Collectors.groupingBy(
                        String::length,
                        Collectors.mapping(Function.identity(),Collectors.toList())
                        )
                );

        Map<String, Set<Integer>> len2StringMap2 = Arrays.asList("Java", "Core Java", "Java", "Streams", "Java","Example")
                .stream()
                .collect(Collectors.groupingBy(
                        //String::length,
                        Function.identity(),
                        Collectors.mapping(String::length,Collectors.toSet())
                        )
                );

        len2StringMap.forEach((key, value) -> System.out.println(key + " : " + value));
        System.out.println("-------------------------");
        len2StringMap2.forEach((key, value) -> System.out.println(key + " : " + value));
    }
}