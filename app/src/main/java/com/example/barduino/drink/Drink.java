package com.example.barduino.drink;

import java.util.LinkedHashMap;
import java.util.Map;

public class Drink {

    private String name;
    private Map<String, Double> ingredient;

    private Integer img;

    public Drink(String name, String[] ingredientname, Double[] ingredientqty, Integer img) {
        this.name = name;
        this.ingredient = new LinkedHashMap<String, Double>();
        for(int i = 0; i < ingredientname.length; i++){
            this.ingredient.put(ingredientname[i],ingredientqty[i]);
        }

        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredient() {
        String ret = "";
        for(Map.Entry<String, Double> entry : this.ingredient.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            ret += key+" "+value+"cl, ";
        }
        return ret;
    }

    public void setIngredient(String[] ingredientname, Double[] ingredientqty) {
        for(int i = 0; i < ingredientname.length; i++){
            this.ingredient.put(ingredientname[i],ingredientqty[i]);
        }
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }

}
