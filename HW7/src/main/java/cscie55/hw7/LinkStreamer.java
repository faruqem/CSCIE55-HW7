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

        //Extract the lines from the files saved in the input directory args[0]
        //and save them in a list called "linesList". This is the common part for both parts
        //(i.e. two arguments vs four arguments) of the Problem 2.
        List<String> linesList =
        Files.list(Paths.get(args[0]))
                    .filter(Files::isRegularFile) //Not mandatory, just to check if the file is a regular one,
                                                  //i.e. not any kind of special unix file.
                    .flatMap(l -> { //Flatten to create a single stream
                        try {
                            return Files.lines(l);
                        } catch (IOException ioe) { }
                        return null;
                    })
                    .filter(line -> //Filter based on the number of input arguments
                            args.length == 2 //If only two arguments (i.e input directory and output file)
                                             //are passed, grab all lines.
                            || (
                                    args.length == 4 //If four arguments are passed (i.e. input directory,
                                            //output file, start and end date of links),
                                            //filter the data and only grab lines with links fell within
                                            //the date range.
                                    && Link.parse(line).timestamp() >= LinkStreamer.secondsPast(args[2])
                                    && Link.parse(line).timestamp() <= LinkStreamer.secondsPast(args[3])
                            )
                    )
                    //.peek(System.out::println)
                    .collect(Collectors.toList());



        //In the section below, based on two or four arguments are passed, take different actions.
        //As per as data is concerned appropriate action is already taken in the part above.

        if(args.length == 4) { //If four arguments are passed (i.e. input directory - args[0],
                               //output file - args[1], start and end date of links - args[2])
                               //& args[3], count and print the
                               //links, fell within the supplied date range, with counts.

            //Create a map called "linkCountMap" from the filtered lines passed
            //from the above section. The map will have the link URL as a String and
            //the count of the link URL as a Long.
            Map<String, Long> linkCountMap
                    = linesList
                    .stream()
                    .map(line -> Link.parse(line).url())
                    //.peek(System.out::println)
                    .collect(groupingBy(identity(), counting()));


            //Process the output as <URL Count> combination
            //and write to the output file passed via args[1]
            Files.write(
                    Paths.get(args[1]),
                    () -> linkCountMap
                            .entrySet()
                            .stream()
                            .<CharSequence>map(e -> e.getKey() + " " + e.getValue())
                            .sorted()
                            .iterator()
            );
        } else if (args.length == 2) { //If only two arguments are passed i.e. input directory - args[0]
                                       //and output file - args[1], process all the lines
                                       //passed from the section all the way above and create
                                       //a line for each unique URL with a comma delimited unique list of tags.


            //The challenge with this section is duplicate tags for each link
            //scattered around each file. That's why it was resolved in different steps below.

            //Create a Map "linkDupTagsStringMap" with URLs (key), and their duplicate tags (value) as
            //a comma delimited string. Resolve key duplicacy by adding the tags of the same URL.
            Map<String, String> linkDupTagsStringMap
                    = linesList
                    .stream()
                    .collect(Collectors.toMap(
                                l -> Link.parse(l).url(),
                                l -> Link.parse(l).tags().stream().collect(joining(", ")),
                                (l1,l2) -> l1 + l2 //Resolve key duplicacy by adding the tags.
                            ) //End of toMap() section
                    ); //End of collect() section

            //Now create another Map "linkTagsMap" with values as a Set to remove the duplcate
            //tags for each URL (key
            Map<String, Set<String>> linkTagsMap
                    = linkDupTagsStringMap.entrySet().stream()
                    .collect(Collectors.toMap(
                            lt -> lt.getKey(),
                            lt -> Arrays.stream(lt.getValue().split(", "))
                                    .map(t->t.trim()).distinct().collect(Collectors.toSet())
                    ));
            //linkCountMap.forEach((key, value) -> System.out.println(key + " " + value));

            //Now create the final Map with URL as the key and by joining the unique tags as a
            //comma delimited string as value from the above mapping.
            Map<String, String> linkTagsStringMap
                =linkTagsMap
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            l -> l.getKey(),
                            l -> l.getValue()
                                    .stream()
                                    .collect(joining(", "))
                            )
                    );

            //Process the output as <URL Tags> combination and write to the
            //output file passed via args[1]
            Files.write(
                    Paths.get(args[1]),
                    () -> linkTagsStringMap
                            .entrySet()
                            .stream()
                            .<CharSequence>map(e -> e.getKey() + " " + e.getValue())
                            .sorted()
                            .iterator()
            );
        } else { //If wrong number of arguments are passed, warn the user.
            System.out.println("Wrong number of arguments!");
        }
    }

    //This is just to calculate the seconds past since 1970 based
    //on the passed start and end date - args[2] & args[3]
    //This is a utility method, that's why did not directly incorporate it in
    //the streaming section for clarity reason and also did not see any advantage.
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
