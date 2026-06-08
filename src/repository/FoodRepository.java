package repository;

import model.Food;
import util.VendingMachineException;

public class FoodRepository extends BaseRepository<Food> {

    private static FoodRepository instance;

    private FoodRepository() {}

    public static FoodRepository getInstance() {
        if (instance == null) instance = new FoodRepository();
        return instance;
    }

    @Override
    protected String getId(Food food) {
        return food.getProductId();
    }
}