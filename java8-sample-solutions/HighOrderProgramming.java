import java.util.function.Function;
import java.util.List;
import java.util.Arrays;
/*
 * Functions that return Functions
 */
public class HighOrderProgramming {
    static public Function<Integer, Function<Integer,Integer>> makeAddr =
	x -> y -> x + y;
    static public Function<Integer,Integer> add3 = makeAddr.apply(3);
    public static void main(String argv[]) {
	List<Integer> intList = Arrays.asList(2, 3, 5, 55);
	intList.stream()
	    .map(i -> makeAddr.apply(3).apply(i))
	    .forEach(System.out::println);
    }
}
