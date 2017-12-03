package cscie55.hw7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public class MoTestNew {

    public static void main(String[] args) throws Exception {

        List<String> linesList =
                Files.list(Paths.get(args[0]))
                        .filter(Files::isRegularFile)
                        .flatMap(s -> {
                            try {
                                return Files.lines(s);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return null;
                        })
                        //.peek(System.out::println)
                        .collect(Collectors.toList());

        Map<String, String> linkDupTagsStringMap
                = linesList
                .stream()
                .collect(Collectors.toMap(
                        l -> Link.parse(l).url(),
                        l -> Link.parse(l).tags().stream().collect(joining(", ")),
                        (l1,l2) -> l1 + l2
                        )
                );


        Map<String, Set<String>> linkTagsMap
                = linkDupTagsStringMap.entrySet().stream()
                .collect(Collectors.toMap(
                        lt -> lt.getKey(),
                        lt->Arrays.stream(lt.getValue().split(", "))
                                .map(t->t.trim()).distinct().collect(Collectors.toSet())
                ));

        System.out.println(linkTagsMap);
        //System.out.println(lineTagsMap);
        /*String s ="Hello, how, are, you, are, you";
        Set<String> sSet = Arrays.stream(s.split(", ")).map(t->t.trim()).distinct().collect(Collectors.toSet());
        System.out.println(sSet);*/
    }
}
