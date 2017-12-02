import java.util.List;
import static java.util.stream.Collectors.toList;

public class BeerDemo {
    public static void main(String argv[]) {
	List<Beer> beerList = Beer.loadCellar();
	beerList.stream()
	    .filter(beer -> beer.getPrice() >= 7.0f)
	    .sorted(Beer.priceComparator)
	    .forEach(System.out::println);
	System.out.println("==========");
	beerList.stream()
	    .map(beer -> beer.country)
	    .filter(c -> c.startsWith("B"))
	    .forEach(System.out::println);
    }
}
