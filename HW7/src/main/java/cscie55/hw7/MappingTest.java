package cscie55.hw7;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

;

public class MappingTest {
    public static void main (String[] args) {
        Stream<String> s = Stream.of("apple", "banana", "orange", "apple");
        Set<String> list = s.collect(Collectors.mapping(s1 -> s1.substring(0, 2),
                Collectors.toSet()));
        System.out.println(list);
    }
}