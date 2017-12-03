package cscie55.hw7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;

public class LinkStreamer {

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

        if(args.length == 4) {
            Map<String, Long> linkCountMap
                    = linesList
                    .stream()
                    .map(line -> Link.parse(line).url())
                    //.sorted()
                    //.peek(System.out::println)
                    .collect(groupingBy(identity(), counting()));

            Files.write(
                    Paths.get(args[1]),
                    //() -> linkCountMap
                    () -> linkCountMap
                            .entrySet()
                            .stream()
                            .<CharSequence>map(e -> e.getKey() + " " + e.getValue())
                            .sorted()
                            .iterator()
            );
        } else if (args.length == 2) {
            Map<String, Set<String>> linkTagsMap = new TreeMap<String, Set<String>>();
            for (String line : linesList) {
                String linkURLString = Link.parse(line).url();
                List<String> linkTagsList = Link.parse(line).tags();


                Set<String> linkTagsSet = new HashSet<String>();

                if (linkTagsMap.containsKey(linkURLString)) {
                    linkTagsSet = linkTagsMap.get(linkURLString);
                }

                for (String tag : linkTagsList) {
                    linkTagsSet.add(tag);
                }

                linkTagsMap.put(linkURLString, linkTagsSet);
            }

            
            //linkCountMap.forEach((key, value) -> System.out.println(key + " " + value));
            Map<String, String> linkTagsStringMap
                =linkTagsMap
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(l -> l.getKey(),l -> l.getValue().stream().collect(joining(", "))));

            //System.out.println(linkTagsStringMap1);

            Files.write(
                    Paths.get(args[1]),
                    //() -> linkCountMap
                    () -> linkTagsStringMap
                            .entrySet()
                            .stream()
                            .<CharSequence>map(e -> e.getKey() + " " + e.getValue())
                            .sorted()
                            .iterator()
            );
        } else {
            System.out.println("Wrong number of arguments!");
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
