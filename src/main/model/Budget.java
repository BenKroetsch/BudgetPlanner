package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Objects;

public class Budget implements Writable {
    private String name;
    private int budget;
    private int balance;

    private ArrayList<Expense> expenseList = new ArrayList<>();

    //Requires: budget is an integer
    //Effects: creates new budget
    public Budget(String name, int budget) {
        this.name = name;
        this.budget = budget;
        this.balance = budget;
    }

    //Requires:
    //Modifies: this
    //Effects: adds expense to budget list and adds expense to category
    // and subtracts the expense cost back from balance
    public void addExpense(Expense expense) {
        this.balance -= expense.getCost();
        expenseList.add(expense);
    }

    //requires: expense to be removed is already  inside list
    //modifies: this
    //effects: removes expense from budget list and removes expense amount from to category it was in
    // and adds the expense cost back to balance
    public void removeExpense(Expense expense) {
        this.balance += expense.getCost();
        expenseList.remove(expense);
    }

    //Effects: adds up total money of all expenses in list
    public int addUpList(String category) {
        int totalMoney = 0;
        for (Expense expense : expenseList) {
            if (Objects.equals(category, expense.getCategory())) {
                totalMoney += expense.getCost();
            }
        }
        return totalMoney;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Budget Name", name);
        json.put("Budget Amount", String.valueOf(budget));
        json.put("Expense List", expenseListToJson());
        return json;
    }

    // EFFECTS: returns expense list in this budget as a JSON array
    private JSONArray expenseListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Expense expense : expenseList) {
            jsonArray.put(expense.toJson());
        }

        return jsonArray;
    }


    public String getName() {
        return name;
    }

    public int getBudget() {
        return budget;
    }

    public int getBalance() {
        return balance;
    }

    public ArrayList<Expense> getExpenseList() {
        return expenseList;
    }

}
