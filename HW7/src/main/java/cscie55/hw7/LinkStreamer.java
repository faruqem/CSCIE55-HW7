package cscie55.hw7;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class LinkStreamer {
    private static Map<String, Set<String>> linkTagsMap = new TreeMap<String, Set<String>>();
    private static List<String> results = new ArrayList<String>();

    public static void main(String[] args) throws Exception {

        //List<Path> files = new ArrayList<>();
        //List<String> lines = new ArrayList<>();
        //List<String> allLines = new ArrayList<>();
        //List<String> linesList = new ArrayList<>();
        Boolean isCountEachLink = false;
        Link link = null;
        //if (args[0] != null) {

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
                    .filter(l ->
                            args.length == 2
                            || (
                                    args.length == 4
                                    && Link.parse(l).timestamp() >= LinkStreamer.secondsPast(args[2])
                                    && Link.parse(l).timestamp() <= LinkStreamer.secondsPast(args[3])
                            )
                    )
                    //.sorted()
                    //.peek(System.out::println)
                    .collect(Collectors.toList());

        Map<String, Long> linkCountMap
                = linesList
                    .stream()
                    .map(line -> Link.parse(line).url())
                    //.sorted()
                    //.peek(System.out::println)
                    .collect(groupingBy(identity(),counting()));


        //linkCountMap.forEach((key, value) -> System.out.println(key + " " + value));

        Files.write(
                Paths.get(args[1]),
                        () -> linkCountMap
                                .entrySet()
                                .stream()
                                .<CharSequence>map(e -> e.getKey() + " " + e.getValue())
                                .sorted()
                                .iterator()
        );

        /*List<String> outputLinesList = new ArrayList<String>();
        linkCountMap.forEach((key, value) -> outputLinesList .add(key + " " + value));
        Files.write(Paths.get(args[1]),outputLinesList , StandardCharsets.UTF_8);*/


        //System.out.println(linkCountMap);

        /*Map<String,Set<String>> linkTagsMap1
                = linesList
                .stream()
                .collect(Collectors.groupingBy(
                        line -> Link.parse(line).url(),
                        Collectors.mapping(line -> Link.parse(line).tags(),Collectors.toSet())
                        )
                );*/
                /*.collect(
                            toMap(
                                    line -> Link.parse(line).url(),
                                    line -> Link.parse(line).tags(),
                                    (t1,t2)-> {
                                        Set<String> tagsSet
                                        = Stream.concat(t1.stream(), t2.stream())
                                                .collect(Collectors.toSet());
                                    }
                            )
                        );*/

            //LinkStreamer t = new LinkStreamer();

            String linkString;
            List<String> linkTagsList;
            //Long linkDateinPastSeconds;
            //Long startSeconds;
            //Long endSeconds;zz


            /*if(args.length == 4) {
                startSeconds = LinkStreamer.secondsPast(args[2]);
                endSeconds = LinkStreamer.secondsPast(args[3]);

                for(String line: allLines) {
                    link = Link.parse(line);
                    linkDateinPastSeconds = link.timestamp();
                    if(linkDateinPastSeconds >= startSeconds
                            && linkDateinPastSeconds <= endSeconds){
                        linesList.add(line);
                    }
                }
                isCountEachLink = true;
            } else {
                linesList = allLines;
                //isCountEachLink = true;
            }*/

            //Switch on if needed
        /*
            for(String line: linesList) {
                //System.out.println(line);
                //link = Link.parse(line);
                linkString = Link.parse(line).url();
                linkTagsList = Link.parse(line).tags();


                Set<String> linkTagsSet = new HashSet<String>();

                if(linkTagsMap.containsKey(linkString)) {
                    linkTagsSet = linkTagsMap.get(linkString);
                }

                if(args.length != 4 ) {
                    for(String tag: linkTagsList) {
                        linkTagsSet.add(tag);
                    }
                } else { //Problem 2 Part2
                    if(linkTagsSet.size()== 0){
                        linkTagsSet.add("1");
                    } else {
                        for(String s: linkTagsSet ) {
                            linkTagsSet.add((Integer.toString(Integer.parseInt(s)+1)));
                            linkTagsSet.remove(s);
                        }
                    }
                }
                linkTagsMap.put(linkString, linkTagsSet);
            }


            results = LinkStreamer.prepareResults(linkTagsMap);
        //System.out.println(results);
        LinkStreamer.writeToFile(args[1], results);
        //} //End of if (args[0] != null) block
        */ //Switch on if needed
    }

    private static List<String> prepareResults(Map<String, Set<String>> linkTagsMap){

        String tagString;
        String key;
        Set<String> tags;
        //Integer counter = 1;
        LinkStreamer t = new LinkStreamer();

        for (Map.Entry<String, Set<String>> entry : linkTagsMap.entrySet()) {
            key = entry.getKey();
            tags = entry.getValue();

            tagString = t.concatTagsWithComma(tags);

            results.add(key+ " "+tagString);
        }

        return results;
    }

    private String concatTagsWithComma(Set<String> tags){
        String tagString = "";
        Integer counter = 1;
        for(String tag: tags) {
            if(counter < tags.size()) {
                tagString += tag + ", ";
            } else {
                tagString += tag;
            }
            counter++;
        }
        return tagString;
    }

    //private static void writeToFile(String filePath, List<String> content){
    private static void writeToFile(String filePath, List<String> content) {
        if(filePath != null) {
            Path file = Paths.get(filePath);
            try {
                Files.write(file, content, Charset.forName("UTF-8"));
                //Files.write(file, lines, Charset.forName("UTF-8"));
            } catch (IOException ioe) {
                //ioe.printStackTrace();
            }
        } else {
            System.out.println("No output file path supplied!");
        }
    }

    private static Long secondsPast(String startEndDate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("EST"));
        Date startDate = null;
        try{
            startDate = simpleDateFormat.parse(startEndDate);
        } catch(ParseException pe) {

        }
        Long pastSeconds = startDate.getTime()/1000;

        return pastSeconds;
    }
}
