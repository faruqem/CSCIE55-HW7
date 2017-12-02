import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map;
import java.util.stream.Stream;
import static java.util.stream.Collectors.groupingBy;

import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.counting;

/**
 * Created by charliesawyer on 10/29/16.
 */
public class WordCountSingleFile {
    public static void main(String argv[]) throws Exception {
	Stream<String> lines = Files.lines(Paths.get(argv[0]));
	Map<String, Long> wordCountMap =
	    lines.map(s -> s.split("\\s+"))
	    .flatMap(Arrays::stream)
	    .collect(groupingBy(identity(), counting()));
	System.out.println(wordCountMap);
    }
}
