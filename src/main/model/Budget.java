package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

//Class that makes a budget
public class Budget implements Writable {
    private String name;
    private Double budget;
    private Double balance;
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private ArrayList<Expense> expenseList = new ArrayList<>();



    //Requires: budget is an integer
    //Effects: creates new budget
    public Budget(String name, Double budget) {
        this.name = name;
        this.budget = budget;
        this.balance = budget;
        EventLog.getInstance().logEvent(new Event("Created budget named " + this.name));
    }

    //Requires:
    //Modifies: this
    //Effects: adds expense to budget list and adds expense to category
    // and subtracts the expense cost back from balance
    public void addExpense(Expense expense) {
        this.balance -= expense.getCost();
        expenseList.add(expense);
        EventLog.getInstance().logEvent(new Event("Added " + expense.getName()
                + " to the budget"));
    }

    //requires: expense to be removed is already  inside list
    //modifies: this
    //effects: removes expense from budget list and removes expense amount from to category it was in
    // and adds the expense cost back to balance
    public void removeExpense(Expense expense) {
        this.balance += expense.getCost();
        expenseList.remove(expense);
        EventLog.getInstance().logEvent(new Event("Removed " + expense.getName()
                + " from budget"));
    }

    //Effects: adds up total money of all expenses in list
    public String addUpList(String category) {
        Double totalMoney = 0.00;
        for (Expense expense : expenseList) {
            if (Objects.equals(category, expense.getCategory())) {
                totalMoney += expense.getCost();
            }
        }
        return decimalFormat.format(totalMoney);
    }

    //Effects: converts budget into json
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

    //Effects: prints a list of each individual expenses summary
    public ArrayList<String> printBudgetSummary() {
        ArrayList<String> listForTable = new ArrayList<>();
        for (Expense s: expenseList) {
            listForTable.add(s.printSummary());
        }
        return listForTable;
    }

    public String getName() {
        return name;
    }

    public Double getBudget() {
        return budget;
    }

    public Double getBalance() {
        return balance;
    }

    public ArrayList<Expense> getExpenseList() {
        return expenseList;
    }

}
