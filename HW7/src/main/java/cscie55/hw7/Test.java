package cscie55.hw7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {

        List<Path> pathList = new ArrayList<>();

        if (args[0] != null) {

            try (Stream<Path> paths = Files.walk(Paths.get(args[0]))) {
                pathList = paths
                        .filter(Files::isRegularFile)
                        .collect(Collectors.toList());
                        //.forEach(System.out::println);
            } catch (IOException e) {
                e.printStackTrace();
            }

            for( Path path: pathList) {
                try (Stream<String> stream = Files.lines(Paths.get(path.toString()))) {
                    stream.forEach(System.out::println);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //System.out.println(path);
            }

            /*

            String fileName = args[0];


            try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

                stream.forEach(System.out::println);

            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
    }
}
