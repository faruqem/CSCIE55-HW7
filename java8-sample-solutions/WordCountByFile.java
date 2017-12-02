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
public class WordCountByFile {
    public static void main(String argv[]) throws Exception {
        Map<String, Map<String, Long>> bigPicture =
                new HashMap<String, Map<String, Long>>();
        for (String fileName : argv) {
	    // Build a stream for each file named on the command line
            Stream<String> lines = Files.lines(Paths.get(fileName));
            Map<String, Long> wordCountMap =
                    lines.map(s -> s.split("\\s+"))
                            .flatMap(Arrays::stream)
                            .collect(groupingBy(identity(), counting()));
            bigPicture.put(fileName, wordCountMap);
        }
        /*
          bigPicture maps filenames to word counts, e.g.
          "f1.txt" -> <"foo" 1>, <"bar" 2>
          The goal is to turn this inside out, i.e.
          "foo" -> <"f1.txt" 1> <"f2.txt" 2> ...
         */
        Map<String, String> countByFile =
                new TreeMap<String, String>();
        for (Map.Entry<String, Map<String, Long>> bigPictureEntry : bigPicture.entrySet()) {
            String fileName = bigPictureEntry.getKey();
            // Now iterate over the word->count map
            for (Map.Entry<String, Long> countEntry : bigPictureEntry.getValue().entrySet()) {
                String word = countEntry.getKey();
                String fileAndCount = fileName + ":" + countEntry.getValue();
                String currentCount = countByFile.get(word);
                // append latest fileAndCount to old value
                String newValue = (currentCount != null ? currentCount : "") + " " + fileAndCount;
                countByFile.put(word, newValue);
            }
        }
        // Print out all entries in the countByFile, e.g. "foo" -> <"f1.txt" 1> <"f2.txt" 3>
        for (Map.Entry<String, String> e : countByFile.entrySet()) {
            String word = e.getKey();
            String counts = e.getValue();
            System.out.println(word + " -> " + counts);
        }
    }
}
/* Output:
a ->  Mary.txt:1 Mary2.txt:1
sure ->  Mary.txt:1
went ->  Mary.txt:1
Harry ->  Mary2.txt:1
fleece ->  Mary2.txt:1
go ->  Mary.txt:1
was ->  Mary.txt:1
had ->  Mary.txt:1 Mary2.txt:2
The ->  Mary.txt:1
that ->  Mary.txt:1
as ->  Mary.txt:1 Mary2.txt:2
His ->  Mary2.txt:1
white ->  Mary.txt:1 Mary2.txt:1
Her ->  Mary.txt:1
snow ->  Mary.txt:1 Mary2.txt:1
were ->  Mary.txt:1
Every ->  Mary.txt:1
lamb ->  Mary.txt:3 Mary2.txt:2
where ->  Mary.txt:1
to ->  Mary.txt:1
fleas ->  Mary.txt:1
little ->  Mary.txt:1 Mary2.txt:1
Mary ->  Mary.txt:2
*/
