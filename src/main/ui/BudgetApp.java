package ui;

import model.Budget;
import model.Expense;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.Math.abs;

public class BudgetApp {
    private static final String JSON_STORE = "./data/workroom.json";
    private Scanner input = new Scanner(System.in);
    private Budget userBudget;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // Effects: runs budget app
    public BudgetApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        introduction();
        runBudget();
    }


    //Effects: prompts user to create their budget
    private void introduction() {
        System.out.println("Welcome to Ben's budget Tracker! "
                + "\n-To create a new budget enter: 1\n"
                + "-To load a prior budget enter: 2");
        int scan = input.nextInt();
        //clears next line to avoid bug
        input.nextLine();
        if (scan == 1) {
            System.out.println("Enter your new budgets name:");
            String name = input.nextLine();
            System.out.println("Enter the amount you would like for this budget:");
            int desiredBudget = input.nextInt();

            //clears next line to avoid bug
            input.nextLine();
            userBudget = new Budget(name, desiredBudget);
            runBudget();
        } else {
            loadBudget();
            runBudget();
        }
    }


    //Effects: main menu that displays features
    private void displayMenu() {
        System.out.println();
        System.out.println("-To view a summary of your budget enter: 1\n"
                + "-To add new expense: 2\n"
                + "-To remove an expense: 3\n"
                + "-To save your budget enter: 4\n"
                + "-To quit enter: 5");
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
                saveBudget();

            } else if (scan == 5) {
                keepGoing = false;

            } else {
                System.out.println("Error, please type a number 1-4.");

            }
        }
    }


    //Effects: displays summary of budget in string format
    private void displayBudget() {

        System.out.println("Displaying summary of budget named \"" + userBudget.getName()
                + "\" that has a goal of spending " + userBudget.getBudget() + "$");
        printList(userBudget.getExpenseList());

        System.out.println("Money spent on transportation " + userBudget.addUpList("Transportation") + "$");
        System.out.println("Money spent on groceries: " + userBudget.addUpList("Groceries and Food") + "$");
        System.out.println("Money spent on entertainment: " + userBudget.addUpList("Entertainment") + "$");
        System.out.println("Money spent on housing: " + userBudget.addUpList("Housing") + "$");

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


    // Effects: converts list to string with each name
    public ArrayList<String> convertList(ArrayList<Expense> expenseList) {
        ArrayList<String> stringList = new ArrayList<>();
        for (Expense expense : expenseList) {
            stringList.add(expense.getName());
        }
        return stringList;
    }

    // Effects: concatenates expenses in a neat list separated by commas
    public void printList(ArrayList<Expense> expenseList) {
        ArrayList<String> stringList = convertList(expenseList);

        StringBuilder s1 = new StringBuilder();
        int s = stringList.size();
        int i = 1;
        for (String a : stringList) {
            if (s > i) {
                s1.append(a).append(", ");
                i++;
            } else {
                s1.append(a);
            }
        }
        System.out.println("Expenses: " + s1);
    }


    // Effects: allows user to create and add expense
    private void createExpense() {
        //clears next line to avoid bug
        input.nextLine();

        //System.out.println();
        System.out.println("Enter expenses name:");
        String expenseName = input.nextLine();
        System.out.println("Enter expenses cost:");
        int expenseCost = input.nextInt();

        System.out.println("Please specify the category of your expense\n"
                + "1 - Transportation\n"
                + "2 - Groceries and Food\n"
                + "3 - Entertainment\n"
                + "4 - Housing");
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
            //clears next line to avoid bug

            System.out.println("Enter the name of the expense you would like removed: ");
            printList(userBudget.getExpenseList());
            String expenseStringToRemove = input.nextLine();
            ArrayList<String> expenseStringList = convertList(userBudget.getExpenseList());

            if (expenseStringList.contains(expenseStringToRemove)) {
                int index = expenseStringList.indexOf(expenseStringToRemove);
                Expense expenseToRemove = userBudget.getExpenseList().get(index);
                userBudget.removeExpense(expenseToRemove);
                System.out.println("Expense has been removed! ");
                expenseLoop = false;
                runBudget();
            } else {
                System.out.println("The expense does not exist.\n"
                        + "To return to menu enter 1, to quit enter 2.");
                if (input.nextInt() == 1) {
                    runBudget();
                }
            }
        }
    }

    // EFFECTS: saves the budget to file
    private void saveBudget() {
        try {
            jsonWriter.open();
            jsonWriter.write(userBudget);
            jsonWriter.close();
            System.out.println("Saved " + userBudget.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
        runBudget();
    }

    // MODIFIES: this
    // EFFECTS: loads budget from file
    private void loadBudget() {
        try {
            userBudget = jsonReader.read();
            System.out.println("Loaded " + userBudget.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
        runBudget();
    }
}







