package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MyModelTest {
    Budget testBudget1;
    Budget testBudget2;
    Expense expense1;
    Expense expense2;
    Expense expense3;
    Expense expense4;
    Expense expense5;
    Expense expense6;
    Expense expense7;

    @BeforeEach
    public void initialize() {
        testBudget1 = new Budget("Ben's Budget", 400);
        testBudget2 = new Budget("Joe's Budget", 1000);
        expense1 = new Expense("Hockey", 500, "Entertainment");
        expense2 = new Expense("Car Insurance", 450, "Entertainment");
        expense3 = new Expense("Rent", 1000, "Housing");
        expense4 = new Expense("Coffee", 3, "Groceries and Food");
        expense5 = new Expense("Soccer", 100, "Entertainment");
        expense6 = new Expense("Zero", 0, "Transportation");
        expense7 = new Expense("Car", 100, "Transportation");
    }

    @Test
    public void budgetConstructorTest() {
        assertEquals(testBudget1.getBalance(), 400);
        assertEquals(testBudget1.getBudget(), 400);
        assertEquals(testBudget1.getName(), "Ben's Budget");
    }

    @Test
    public void ExpenseConstructorTest() {
        assertEquals(expense1.getCost(), 500);
        assertEquals(expense1.getCategory(), "Entertainment");
        assertEquals(expense1.getName(), "Hockey");
    }

    @Test
    public void AddExpenseTest() {
        testBudget2.addExpense(expense1);
        assertEquals(testBudget2.getBudget(), 1000);
        assertEquals(testBudget2.getBalance(), 500);
        assertEquals(testBudget2.getName(), "Joe's Budget");
        assertEquals(testBudget2.addUpList("Entertainment"), 500);
        assertEquals((testBudget2.getExpenseList().size()), 1);
        assertEquals(testBudget2.getExpenseList().get(0), expense1);

        testBudget2.addExpense(expense6);
        assertEquals(testBudget2.addUpList("Entertainment"), 500);
        assertEquals(testBudget2.addUpList("Transportation"), 0);
        assertEquals(testBudget2.getBudget(), 1000);
        assertEquals(testBudget2.getBalance(), 500);
        assertEquals(testBudget2.getName(), "Joe's Budget");
        assertEquals((testBudget2.getExpenseList().size()), 2);
        assertEquals(testBudget2.getExpenseList().get(0), expense1);
        assertEquals(testBudget2.getExpenseList().get(1), expense6);

        testBudget2.addExpense(expense2);
        assertEquals(testBudget2.getBudget(), 1000);
        assertEquals(testBudget2.getBalance(), 50);
        assertEquals(testBudget2.getName(), "Joe's Budget");
        assertEquals((testBudget2.getExpenseList().size()), 3);
        assertEquals(testBudget2.getExpenseList().get(0), expense1);
        assertEquals(testBudget2.getExpenseList().get(1), expense6);
        assertEquals(testBudget2.getExpenseList().get(2), expense2);
        assertEquals(testBudget2.addUpList("Groceries and Food"), 0);

        testBudget2.addExpense(expense3);
        assertEquals(testBudget2.addUpList("Housing"), 1000);
        assertEquals(testBudget2.getBalance(), 50 - 1000);

        testBudget2.addExpense(expense4);
        assertEquals(testBudget2.addUpList("Groceries and Food"), 3);

    }


    @Test
    public void RemoveExpenseTest() {
        testBudget2.addExpense(expense1);
        testBudget2.addExpense(expense2);
        testBudget2.addExpense(expense3);

        testBudget2.removeExpense(expense1);
        assertEquals(testBudget2.getBudget(), 1000);
        assertEquals(testBudget2.getBalance(), -450);
        assertEquals(testBudget2.getExpenseList().size(), 2);
        assertEquals(testBudget2.getExpenseList().get(0), expense2);
        assertEquals(testBudget2.getExpenseList().get(1), expense3);
        assertEquals(testBudget2.addUpList("Entertainment"), 450);
        assertEquals(testBudget2.addUpList("Housing"), 1000);
        assertEquals(testBudget2.addUpList("Groceries and Food"), 0);

        testBudget2.removeExpense(expense3);
        assertEquals(testBudget2.getBudget(), 1000);
        assertEquals(testBudget2.getBalance(), 550);
        assertEquals(testBudget2.getExpenseList().size(), 1);
        assertEquals(testBudget2.getExpenseList().get(0), expense2);
        assertEquals(testBudget2.addUpList("Entertainment"), 450);
        assertEquals(testBudget2.addUpList("Housing"), 0);

        testBudget2.removeExpense(expense2);
        assertEquals(testBudget2.getBudget(), 1000);
        assertEquals(testBudget2.getBalance(), 1000);
        assertEquals(testBudget2.getExpenseList().size(), 0);
        assertEquals(testBudget2.addUpList("Entertainment"), 0);

        testBudget2.addExpense(expense6);
        testBudget2.removeExpense(expense6);
        assertEquals(testBudget2.getBudget(), 1000);
        assertEquals(testBudget2.getBalance(), 1000);
        assertEquals(testBudget2.getExpenseList().size(), 0);
        assertEquals(testBudget2.addUpList("Entertainment"), 0);

        testBudget2.addExpense(expense4);
        testBudget2.removeExpense(expense4);
        assertEquals(testBudget2.getBudget(), 1000);
        assertEquals(testBudget2.getBalance(), 1000);
        assertEquals(testBudget2.getExpenseList().size(), 0);
        assertEquals(testBudget2.addUpList("Groceries and Food"), 0);
        assertEquals(testBudget2.addUpList("Transportation"), 0);
        assertEquals(testBudget2.addUpList("Housing"), 0);
    }

    @Test
    public void printSummaryTest() {
        assertEquals(expense1.printSummary(), "Expense: Hockey, Cost: 500, Category: Entertainment");
        testBudget2.addExpense(expense1);
        testBudget2.addExpense(expense2);
        assertEquals(testBudget2.printBudgetSummary().get(0),
                "Expense: Hockey, Cost: 500, Category: Entertainment");
        assertEquals(testBudget2.printBudgetSummary().get(1),
                "Expense: Car Insurance, Cost: 450, Category: Entertainment");


    }
}