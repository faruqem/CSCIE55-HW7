package cscie55.hw7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

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
                        .peek(System.out::println)
                        .collect(Collectors.toList());


    }
}
