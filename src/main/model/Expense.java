package model;

import org.json.JSONObject;
import persistence.Writable;

//Class that makes an expense contained in a budget
public class Expense implements Writable {
    private Integer cost;
    private String name;
    private String category;

    //Requires: expense to be an integer
    //Modifies: this
    //effects: creates new expense
    public Expense(String name, Integer cost, String category) {
        this.name = name;
        this.cost = cost;
        this.category = category;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("cost", String.valueOf(cost));
        json.put("category", category);
        return json;
    }

    public String printSummary() {
        return "Expense: " + name + ", Cost: " + cost + ", Category: " + category;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public Integer getCost() {
        return cost;
    }
}
