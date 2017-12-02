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

public class LinkStreamer {
    private static Map<String, Set<String>> linkTagsMap = new TreeMap<String, Set<String>>();
    private static List<String> results = new ArrayList<String>();

    public static void main(String[] args) throws Exception {

        List<Path> files = new ArrayList<>();
        //List<String> lines = new ArrayList<>();
        List<String> allLines = new ArrayList<>();
        List<String> eligibleLines = new ArrayList<>();
        Boolean isCountEachLink = false;

        //if (args[0] != null) {

        allLines =
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
                //.forEach(System.out::println);
                    .collect(Collectors.toList());






            LinkStreamer t = new LinkStreamer();

            String linkString;
            List<String> linkTagsList;
            Long linkDateinPastSeconds;
            Long startSeconds;
            Long endSeconds;

            if(args.length == 4) {
                startSeconds = t.secondsPast(args[2]);
                endSeconds = t.secondsPast(args[3]);

                for(String line: allLines) {
                    Link link = Link.parse(line);
                    linkDateinPastSeconds = link.timestamp();
                    if(linkDateinPastSeconds >= startSeconds
                            && linkDateinPastSeconds <= endSeconds){
                        eligibleLines.add(line);
                    }
                }
                isCountEachLink = true;
            } else {
                eligibleLines = allLines;
                //isCountEachLink = true;
            }


            for(String line: eligibleLines) {
                //System.out.println(line);
                Link link = Link.parse(line);
                linkString = link.url();
                linkTagsList = link.tags();


                Set<String> linkTagsSet = new HashSet<String>();

                if(linkTagsMap.containsKey(linkString)) {
                    linkTagsSet = linkTagsMap.get(linkString);
                }

                if(!isCountEachLink ) {
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


            results = t.prepareResults(linkTagsMap);
        //System.out.println(results);
            t.writeToFile(args[1], results);
        //} //End of if (args[0] != null) block
    }

    private List<String> prepareResults(Map<String, Set<String>> linkTagsMap){

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
    private void writeToFile(String filePath, List<String> content) {
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

    private Long secondsPast(String startEndDate){
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
