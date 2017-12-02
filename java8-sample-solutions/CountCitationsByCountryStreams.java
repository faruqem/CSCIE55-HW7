
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.Comparator;
import java.util.stream.Collectors;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import java.util.Map;
import static java.util.Map.Entry.comparingByValue;

public class CountCitationsByCountryStreams {
    private static final int COUNTRY_INDEX = 4;
    public static void main(String argv[]) throws Exception  {
	System.out.println(argv[0]);
	Stream<String> lines = Files.lines(Paths.get(argv[0]));
	Map<String, Long> myMap = 
	    lines
	    .filter(s -> s.split(",").length > COUNTRY_INDEX)
	    .map(s -> s.split(",")[COUNTRY_INDEX].trim())
	    .collect(groupingBy(identity(), counting()));
	myMap.entrySet().stream()
	    // Base comparison on values, switching order
    	    .sorted((e1,e2) -> e2.getValue().compareTo(e1.getValue()))
	    .limit(5)
	    .forEach(System.out::println);
    }
}
/*
  Attempts to sort the Map<Country, Count> according to Count, 
  and do it in descending order were very frustrating. There is 
  a static comparator on Map.Entry comparingByValue that will 
  order the items according to the values (not the keys). 
  Then we would have
    .sorted(comparingByValue()), 
  but that would be in ascending order. There is a method reversed()
  applied to a Comparator that inverts the comparison. Tried
    .sorted(comparingByValue().reversed())
  Compiler says:
error: incompatible types: Comparator<Entry<Object,V>> cannot be
    converted to Comparator<? super Entry<String,Long>>
	    .sorted(comparingByValue().reversed())
  where V is a type-variable:
    V extends Comparable<? super V>
  Final, correct form using comparingByValue and reversed:
    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
 */
