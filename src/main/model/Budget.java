package model;

import java.util.ArrayList;
import java.util.Objects;

public class Budget {
    private String name;
    private int budget;
    private int balance;

    private ArrayList<Expense> expenseList = new ArrayList<>();
    private ArrayList<String> expenseStringList = new ArrayList<>();
    private int entertainmentSpent;
    private int groceriesSpent;
    private int transportationSpent;
    private int housingSpent;


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
        String type = expense.getCategory();
        if (Objects.equals(type, "Entertainment")) {
            this.entertainmentSpent += expense.getCost();
        }
        if (Objects.equals(type, "Groceries and Food")) {
            this.groceriesSpent += expense.getCost();
        }
        if (Objects.equals(type, "Transportation")) {
            this.transportationSpent += expense.getCost();
        }
        if (Objects.equals(type, "Housing")) {
            this.housingSpent += expense.getCost();
        }
        this.balance -= expense.getCost();
        expenseStringList.add(expense.getName());
        expenseList.add(expense);
    }

    //requires: expense to be removed is already  inside list
    //modifies: this
    //effects: removes expense from budget list and removes expense amount from to category it was in
    // and adds the expense cost back to balance
    public void removeExpense(Expense expense) {
        String expenseCategory = expense.getCategory();
        if (Objects.equals(expenseCategory, "Entertainment")) {
            this.entertainmentSpent -= expense.getCost();
        }
        if (Objects.equals(expenseCategory, "Groceries and Food")) {
            this.groceriesSpent -= expense.getCost();
        }
        if (Objects.equals(expenseCategory, "Transportation")) {
            this.transportationSpent -= expense.getCost();
        }
        if (Objects.equals(expenseCategory, "Housing")) {
            this.housingSpent -= expense.getCost();
        }
        this.balance += expense.getCost();
        expenseStringList.remove(expense.getName());
        expenseList.remove(expense);
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

    public int getHousingSpent() {
        return housingSpent;
    }

    public int getEntertainmentSpent() {
        return entertainmentSpent;
    }

    public int getGroceriesSpent() {
        return groceriesSpent;
    }

    public int getTransportationSpent() {
        return transportationSpent;
    }

    public ArrayList<String> getExpenseStringList() {
        return expenseStringList;
    }

    public ArrayList<Expense> getExpenseList() {
        return expenseList;
    }

}
