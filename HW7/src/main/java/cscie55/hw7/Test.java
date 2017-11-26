package cscie55.hw7;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {

        List<Path> files = new ArrayList<>();
        List<String> lines = new ArrayList<>();

        Path outputDirectory = Paths.get("output_problem2");
        try {
            Files.createDirectories(outputDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Path file = Paths.get("output_problem2/part-r-00000");


        if (args[0] != null) {

            try (Stream<Path> paths = Files.walk(Paths.get(args[0]))) {
                files = paths
                        .filter(Files::isRegularFile)
                        .collect(Collectors.toList());
                        //.forEach(System.out::println);
            } catch (IOException e) {
                e.printStackTrace();
            }

            for( Path path: files) {
                try (Stream<String> stream = Files.lines(Paths.get(path.toString()))) {
                    //stream.forEach(System.out::println);
                    lines = stream.collect(Collectors.toList());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //System.out.println(path);
            }

            for(String line: lines){
                System.out.println(line);
            }

            try {
                Files.write(file, lines, Charset.forName("UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
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
