import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.Arrays;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class WordHistogramStreams {
    static List<String> getWordList(String args[]) throws Exception {
	List<String> result = new ArrayList<String>();
	for (String arg : args) {
	    Stream<String> lines = Files.lines(Paths.get(arg));
	    lines.map(s -> s.toLowerCase().split("\\s+"))
		.flatMap(Arrays::stream)
		.sorted()
		.forEach(result::add);
	}
	return result;
    }

    public static void main(String argv[]) throws Exception {
	List<String> words = getWordList(argv);
	Map<Integer, Long> histogram = 
	    words.stream()
	    .collect(groupingBy(String::length, counting()));
	for( Map.Entry entry : histogram.entrySet()) { 
	    System.out.println(entry.getKey() + ":" + entry.getValue());
	}
    }
}

