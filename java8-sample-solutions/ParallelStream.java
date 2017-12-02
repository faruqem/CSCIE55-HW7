import java.util.Arrays;
import java.util.List;

public class ParallelStream {
    public static int times2(int value) {
	try { Thread.sleep(500); } catch (Exception ex) { }
	return value * 2;
    }
    public static void main(String argv[]) {
	List<Integer> intList = Arrays.asList(2, 4, 5, 7, 15, 22);
	System.out.println(intList.stream()
	    .mapToInt(ParallelStream::times2)
			   .sum());
	System.out.println(intList.parallelStream()
	    .mapToInt(ParallelStream::times2)
			   .sum());
    }
}
