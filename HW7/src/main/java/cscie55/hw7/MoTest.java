package cscie55.hw7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class MoTest {


    public static void main(String[] args) {

        List<String> myList = new ArrayList<String>();
        List<String> myNewList;

        myList.add("Apple");
        myList.add("Orange");
        myList.add("Banana");
        myList.add("Black Berry");
        myList.add("Guava");
        myList.add("Mango");
        myList.add("Cherry");

        myNewList = myList
                .stream()
                .sorted()
                .filter(s->s.length()<=6)
                .limit(3)
                .map(f->f.toUpperCase())
                //.forEach(System.out::println)
        .collect(Collectors.toList());

        myNewList
                .stream()
                .forEach(System.out::println);

        System.out.println(myNewList
                .stream()
                .count());

        String concat = myNewList
                .stream()
                .map(f->f.toLowerCase())
                .reduce("",(a,b)->a+b+", ");

        System.out.println(concat.substring(0,concat.length()-2));

        String[] province ={"Ontario", "Alberta","Nova Scotia","Winnipeg","Alberta","Prince Edward Island"};
        Arrays.stream(province)
                .sorted()
                .distinct()
                .forEach(System.out::println);
        String[] newArray = Arrays.stream(province)
                .sorted()
                .distinct()
                .map(p->p.toUpperCase())
                .toArray(String[]::new);
        for(String p: newArray){
            System.out.println(p);
        }

        Map<Integer, Long> histogram =
                Arrays.stream(province)
        	    .collect(groupingBy(String::length, counting()));
        	for( Map.Entry entry : histogram.entrySet()) {
            	    System.out.println(entry.getKey() + ":" + entry.getValue());
            }
    }
}
