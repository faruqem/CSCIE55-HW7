import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.function.BinaryOperator;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.counting;
public class BeerStreams {
    public static void main(String argv[]) {
	List<Beer> beerList = Beer.loadCellar();
	beerList.stream()
	    .sorted(Beer.priceComparator.reversed())
	    .filter(beer -> beer.getPrice() > 7.5f)
	    .forEach(System.out::println);
	BinaryOperator<String> stringSplicer = 
	    (s1, s2) -> s1 + " " + s2;
	// Create Map of countries and the prices of their beers
	Map<String, String> countryPricesMap =
	    beerList.stream()
             .collect(toMap(beer -> beer.country, 
		            beer -> String.valueOf(beer.getPrice()), 
			    stringSplicer));
	System.out.println(countryPricesMap);
	Map<Float, List<Beer>> mfb = 
	    beerList.stream().collect(groupingBy(Beer::getPrice));
	for (Map.Entry<Float, List<Beer>> entry : mfb.entrySet()) {
	    String s = String.valueOf(entry.getKey());
	    for (Beer beer : entry.getValue()) {
		s += " " + beer.name;
	    }
	    System.out.println(s);
	}
    }
}
