import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
/* Adapted from "Java Programming" by Yakov Fain
 */
public class Beer implements Comparable<Beer> {
    public final String name;
    public final String country;
    private float price;

    public Beer(String name, String country,float price){
        this.name=name;
        this.country=country;
        this.price=price;
    }
    public int compareTo(Beer otherBeer) {
	if (price > otherBeer.price) {
	    return 1;
	}
	if (price < otherBeer.price) {
	    return -1;
	}
	return 0;
    }
    public static Comparator<Beer> priceComparator = 
	Comparator.comparing(beer -> beer.getPrice());


    public String toString(){
        return "Country: " + 
	    country +  " Name: " +
	    name + ", price: " + price;
    }
    public float getPrice() {
	return price;
    }
    public String getCountry() {
	return country;
    }
    public void setPrice(float price) {
	this.price = price;
    }
    public static List<Beer> loadCellar(){
        List<Beer> beerStock = new ArrayList<>();

        beerStock.add(new Beer("Stella", "Belgium", 7.75f));
        beerStock.add(new Beer("Sam Adams", "USA", 7.00f));
        beerStock.add(new Beer("Obolon", "Ukraine", 4.00f));
        beerStock.add(new Beer("Bud Light", "USA", 5.00f));
        beerStock.add(new Beer("Zagorka", "Bulgaria", 5.00f));
        beerStock.add(new Beer("Yuengling", "USA", 5.50f));
        beerStock.add(new Beer("Leffe Blonde", "Belgium", 8.75f));
        beerStock.add(new Beer("Chimay Blue", "Belgium", 10.00f));
        beerStock.add(new Beer("Brooklyn Lager", "USA", 8.25f));

        return beerStock;
    }

}
