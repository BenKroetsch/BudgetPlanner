package model;

import org.json.JSONObject;
import persistence.Writable;

public class Expense implements Writable {
    private double cost;
    private String name;
    private String category;

    //Requires: expense to be an integer
    //Modifies: this
    //effects: creates new expense
    public Expense(String name, double cost, String category) {
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
    public double getCost() {
        return cost;
    }
}
