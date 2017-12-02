import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.Map;
import java.util.function.Function;
import java.util.function.BiFunction;
import static java.util.stream.Collectors.toMap;

public class MaxTemperatureStreams {
    public static void main(String argv[]) throws Exception {
	Stream<String> lines = Files.lines(Paths.get(argv[0]));
	Function<String, Integer> getTempFromString =  s -> {
	    if (s.charAt(87) == '+') {
		return Integer.parseInt(s.substring(88,92));
	    }
	    else {
		return Integer.parseInt(s.substring(87, 92));
	    }
	};
	BiFunction<Integer, Integer, Integer> maxTemp = 
	    (i1, i2) -> i1 > i2 ? i1 : i2;
	Map<Integer, Integer> year2tempmap = 
	lines.collect(toMap(
	 s -> Integer.parseInt(s.substring(15,19)),//Determines K of <K,V>
		getTempFromString, // Determines V of <K,V>
	 (s1, s2) -> maxTemp.apply(s1, s2)
	         //merge function applies to V of <K,V>
		));
	year2tempmap.entrySet().forEach(System.out::println);
    }
}
