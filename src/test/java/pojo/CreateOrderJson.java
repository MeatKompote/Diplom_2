package pojo;

import java.util.List;

public class CreateOrderJson extends AbstractPojo{

    private List<String> ingredients;

    public CreateOrderJson(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public CreateOrderJson() {
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
