import java.util.*;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;
public class CollectorsTest {
    static public class Dish {

        private final String name;
        private final boolean vegetarian;
        private final int calories;
        private final Type type;

        public Dish(String name, boolean vegetarian, int calories, Type type) {
            this.name = name;
            this.vegetarian = vegetarian;
            this.calories = calories;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public boolean isVegetarian() {
            return vegetarian;
        }

        public int getCalories() {
            return calories;
        }

        public Type getType() {
            return type;
        }

        public enum Type { MEAT, FISH, OTHER }

        @Override
        public String toString() {
            return name;
        }

    }

    public static final List<Dish> menu =
            Arrays.asList( new Dish("pork", false, 800, Dish.Type.MEAT),
                    new Dish("beef", false, 700, Dish.Type.MEAT),
                    new Dish("chicken", false, 400, Dish.Type.MEAT),
                    new Dish("french fries", true, 530, Dish.Type.OTHER),
                    new Dish("rice", true, 350, Dish.Type.OTHER),
                    new Dish("season fruit", true, 120, Dish.Type.OTHER),
                    new Dish("pizza", true, 550, Dish.Type.OTHER),
                    new Dish("prawns", false, 400, Dish.Type.FISH),
                    new Dish("salmon", false, 450, Dish.Type.FISH));

    public static void main(String argv[]){
        List<Dish> dishes = menu.stream()
                .collect(toList());
        Set<Dish> dishSet = menu.stream()
                .collect(toSet());
        Collection<Dish> dishCollection = menu.stream()
                .collect(toCollection(ArrayList::new));
        Long count = menu.stream()
                .collect(counting());
        Integer totalCalories = menu.stream()
                .collect(summingInt(Dish::getCalories));
        Double averageCalories = menu.stream()
                .collect(averagingInt(Dish::getCalories));
        String names = menu.stream()
                .map(Dish::getName)
                .collect(joining(", "));
        Optional<Dish> fattest = menu.stream()
                .collect(maxBy(comparingInt(Dish::getCalories)));
        Optional<Dish> lightest = menu.stream()
                .collect(minBy(comparingInt(Dish::getCalories)));
        int sumCalories = menu.stream()
                .collect(reducing(0, Dish::getCalories, Integer::sum));
        int howManyDishes = menu.stream()
                .collect(collectingAndThen(toList(), List::size));
        Map<Dish.Type, List<Dish>> dishesByType = menu.stream()
                .collect(groupingBy(Dish::getType));
        Map<Boolean, List<Dish>> vegetarianDishes = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian));
    }

    public static List<String> getLowCaloricDishesNamesInJava7(List<Dish> dishes){
        List<Dish> lowCaloricDishes = new ArrayList<>();
        for(Dish d: dishes){
            if(d.getCalories() > 400){
                lowCaloricDishes.add(d);
            }
        }
        List<String> lowCaloricDishesName = new ArrayList<>();
        Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
            public int compare(Dish d1, Dish d2){
                return Integer.compare(d1.getCalories(), d2.getCalories());
            }
        });
        for(Dish d: lowCaloricDishes){
            lowCaloricDishesName.add(d.getName());
        }
        return lowCaloricDishesName;
    }

    public static List<String> getLowCaloricDishesNamesInJava8(List<Dish> dishes){
        return dishes.stream()
                .filter(d -> d.getCalories() > 400)
                .sorted(comparing(Dish::getCalories))
                .map(Dish::getName)
                .collect(toList());
    }

    public static int sumLowCaloricDishesNamesInJava8(List<Dish> dishes){
        return dishes.stream()
                .filter(d -> d.getCalories() > 400)
                .collect(summingInt(Dish::getCalories));
    }
}
