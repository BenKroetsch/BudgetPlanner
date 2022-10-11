package ui;

import model.Budget;
import model.Expense;

import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

import static java.lang.Math.abs;

public class BudgetApp {
    private Scanner input = new Scanner(System.in);
    private Budget userBudget;


    // Effects: runs budget app
    public BudgetApp() {
        introduction();
        runBudget();
    }


    //Effects: prompts user to create their budget
    private void introduction() {
        System.out.println("Welcome to Ben's budget Tracker!");
        System.out.println("Enter your new budgets name:");
        String name = input.nextLine();
        System.out.println("Enter the amount you would like for this budget:");
        int desiredBudget = input.nextInt();

        userBudget = new Budget(name, desiredBudget);
    }


    //Effects: main menu that displays features
    private void displayMenu() {
        System.out.println();
        System.out.println("-To view a summary of your budget enter: 1");
        System.out.println("-To add new expense: 2");
        System.out.println("-To remove an expense: 3");
        System.out.println("-To quit enter: 4");
    }


    //Effects: processes user input
    private void runBudget() {
        boolean keepGoing = true;
        while (keepGoing) {
            displayMenu();
            int scan = input.nextInt();

            if (scan == 1) {
                keepGoing = false;
                displayBudget();

            } else if (scan == 2) {
                keepGoing = false;
                createExpense();

            } else if (scan == 3) {
                keepGoing = false;
                removeExpense();

            } else if (scan == 4) {
                keepGoing = false;

            } else {
                System.out.println("Error, please type a number 1-4.");

            }
        }
    }


    //Effects: displays summary of budget in string format
    private void displayBudget() {
        System.out.println();
        System.out.println("Displaying summary of budget: " + userBudget.getName()
                + " that has a goal of spending " + userBudget.getBudget() + "$");
        printList(userBudget.getExpenseStringList());

        System.out.println("Money spent on transportation " + userBudget.getTransportationSpent() + "$");
        System.out.println("Money spent on groceries: " + userBudget.getGroceriesSpent() + "$");
        System.out.println("Money spent on entertainment: " + userBudget.getEntertainmentSpent() + "$");
        System.out.println("Money spent on housing: " + userBudget.getHousingSpent() + "$");

        displayBudgetCheck();
        System.out.println("To return to menu enter 1, to quit enter 2.");
        if (input.nextInt() == 1) {
            runBudget();
        }
    }

    // Effects: Helper for display budget, checks if user has exceeded budget,
    // if so displays amount over budget, else shows remaining budget amount
    public void displayBudgetCheck() {
        if (userBudget.getBalance() <= 0) {
            System.out.println("You have 0$ left to spend. You are "
                    + abs(userBudget.getBalance()) + "$ over your budget.");
        } else {
            System.out.println("You have " + userBudget.getBalance() + "$ left to spend.");
        }

    }

    // Effects: concatenates expenses in a neat list separated by commas
    public void printList(List<String> list1) {
        StringJoiner joiner = new StringJoiner(", ");
        list1.forEach(joiner::add);
        System.out.println("Expenses: " + joiner);
    }




    // Effects: allows user to create and add expense
    private void createExpense() {
        System.out.println();
        System.out.println("Enter expenses name:");
        String expenseName = input.next();
        System.out.println("Enter expenses cost:");
        int expenseCost = input.nextInt();

        System.out.println("Please specify the category of your expense");
        System.out.println("1 - Transportation");
        System.out.println("2 - Groceries and Food");
        System.out.println("3 - Entertainment");
        System.out.println("4 - Housing");
        int category = input.nextInt();
        String expenseCategory = assignCategory(category);
        Expense a1 = new Expense(expenseName, expenseCost, expenseCategory);
        userBudget.addExpense(a1);

        runBudget();
    }

    // Effects: helper for create budget, assigns expense to category based on user input
    private String assignCategory(int category) {
        if (category == 1) {
            return "Transportation";
        }
        if (category == 2) {
            return "Groceries and Food";
        }
        if (category == 3) {
            return "Entertainment";
        } else {
            return "Housing";
        }
    }


    // Effects: allows user to remove an expense
    private void removeExpense() {
        boolean expenseLoop = true;
        while (expenseLoop) {
            System.out.println("Enter the name of the expense you would like removed: ");
            printList(userBudget.getExpenseStringList());
            String expenseStringToRemove = input.next();

            if (userBudget.getExpenseStringList().contains(expenseStringToRemove)) {
                int index = userBudget.getExpenseStringList().indexOf(expenseStringToRemove);
                Expense expenseToRemove = userBudget.getExpenseList().get(index);
                userBudget.removeExpense(expenseToRemove);
                System.out.println("Expense has been removed! ");
                expenseLoop = false;
                runBudget();
            } else {
                System.out.println("The expense does not exist.");
                System.out.println("To return to menu enter 1, to quit enter 2.");
                if (input.nextInt() == 1) {
                    runBudget();
                }
            }
        }
    }
}





