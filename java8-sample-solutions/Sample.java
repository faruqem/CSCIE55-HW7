import java.util.stream.IntStream;

public class Sample {
    private static boolean isPrime(int number) {
	return number > 1 && 
	    IntStream.range(2, number)
	    .noneMatch(index -> number % index == 0);
    }
    public static void main(String argv[]) {
	System.out.println(isPrime(3));
	System.out.println(isPrime(5));
	System.out.println(isPrime(7));
	System.out.println(isPrime(9));
    }
}
