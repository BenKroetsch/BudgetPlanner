package model;

public class Expense {
    private int cost;
    private String name;
    private String category;

    //Requires: expense to be an integer
    //Modifies: this
    //effects: creates new expense
    public Expense(String name, int cost, String category) {
        this.name = name;
        this.cost = cost;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }
}
