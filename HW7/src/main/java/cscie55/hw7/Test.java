package cscie55.hw7;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {

    private static Map<String, Set<String>> linkTagsMap = new TreeMap<String, Set<String>>();
    private static List<String> results = new ArrayList<String>();

    public static void main(String[] args) {

        List<Path> files = new ArrayList<>();
        List<String> lines = new ArrayList<>();
        List<String> allLines = new ArrayList<>();

        Path outputDirectory = Paths.get("output_problem2");
        try {
            Files.createDirectories(outputDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }





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
                //System.out.print(path+"\n");
                try (Stream<String> stream = Files.lines(Paths.get(path.toString()))) {
                    //stream.forEach(System.out::println);
                    lines = stream.collect(Collectors.toList());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //System.out.println(path);
                for(String line: lines) {
                    allLines.add(line);
                }
            }


            String linkString;
            List<String> linkTagsList;


            for(String line: allLines){
                //System.out.println(line);
                Link link = Link.parse(line);
                linkString = link.url();
                linkTagsList = link.tags();
                Set<String> linkTagsSet = new HashSet<String>();

                //System.out.print("*****"+ linkString+"\n");
                //System.out.println("#######"+line);

                /*System.out.println("----------------");
                for(String tag: linkTagsList) {
                    System.out.println(tag);
                }*/
                /*
                if(linkTagsList.size()==0){
                    System.out.println("Empty tag!");
                }*/


                ///*
                if(linkTagsMap.containsKey(linkString)) {
                    linkTagsSet = linkTagsMap.get(linkString);
                    for(String tag: linkTagsList) {
                        linkTagsSet.add(tag);
                    }
                    linkTagsMap.put(linkString, linkTagsSet);
                } else {
                    for(String tag: linkTagsList) {
                        linkTagsSet.add(tag);
                    }
                    linkTagsMap.put(linkString, linkTagsSet);
                }
                //*/
            }


            String tagString = "";
            String key;
            Set<String> tags;
            Integer counter = 1;

            for (Map.Entry<String, Set<String>> entry : linkTagsMap.entrySet()) {
                key = entry.getKey();
                tags = entry.getValue();

                for(String tag: tags) {
                    if(counter < tags.size()) {
                        tagString += tag + ", ";
                    } else {
                        tagString += tag;
                    }
                    counter++;
                }

                //System.out.println(key+ " "+tagString);
                results.add(key+ " "+tagString);
                counter = 1;
                tagString = "";
            }

            ///*
            Path file = Paths.get("output_problem2/part-r-00000");
            try {
                Files.write(file, results, Charset.forName("UTF-8"));
                //Files.write(file, lines, Charset.forName("UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //*/
        }
    }
}
