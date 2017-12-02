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
import java.util.stream.Stream;

public class Test {

    private static Map<String, Set<String>> linkTagsMap = new TreeMap<String, Set<String>>();
    private static List<String> results = new ArrayList<String>();
/*
    public static void main(String[] args) {

        List<Path> files = new ArrayList<>();
        List<String> lines = new ArrayList<>();
        List<String> allLines = new ArrayList<>();
        List<String> eligibleLines = new ArrayList<>();
        Boolean isCountEachLink = false;



        if (args[0] != null) {

            try (Stream<Path> paths = Files.walk(Paths.get(args[0]))) {
                files = paths
                        .filter(Files::isRegularFile)
                        .collect(Collectors.toList());
                        //.forEach(System.out::println);
            } catch (IOException e) {
                //e.printStackTrace();
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


            Test t = new Test();

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
            t.writeToFile(args[1], results);
        }
    }

    private List<String> prepareResults(Map<String, Set<String>> linkTagsMap){

        String tagString;
        String key;
        Set<String> tags;
        //Integer counter = 1;
        Test t = new Test();

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
    */
}
