package ui;

import model.Budget;
import model.Expense;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Math.abs;
import static jdk.nashorn.internal.objects.NativeMath.round;

public class BudgetApp extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/budget.json";
    private Budget userBudget;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JFrame frame;
    private JPanel introPanel = new JPanel(new GridLayout(5, 1));
    private JPanel introPanelTop = new JPanel();
    private JPanel introPanelBottom = new JPanel();
    private JPanel introPanelButton = new JPanel();
    private JPanel bottomContainer;
    private JButton confirmButton;
    private JTextField budgetCost;
    private JTextField budgetName;
    private JComboBox removeExpenseBox;
    private JPanel rightPanel;
    private JPanel container1;
    private JPanel container2;
    private JPanel container3;
    private JPanel container4;
    private JPanel container5;
    private JComboBox categoryBox;
    private JTextField textCostName;
    private JTextField textExpenseName;

    // Effects: runs budget app
    public BudgetApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        createFrame();
        introductionFrame();
    }


    //Effects: initializes frame
    private void createFrame() {
        frame = new JFrame();
        frame.setTitle("Expense Manager");
        frame.setSize(900, 300);
        frame.setDefaultCloseOperation(3);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
    }

    //Effects: graphics that prompts user to create a new budget
    private void introductionFrame() {
        introPanel.add(introPanelTop);
        introPanel.add(introPanelBottom);
        introPanel.add(introPanelButton);

        JLabel budgetNameIcon = new JLabel("Budget Name: ");
        introPanelTop.add(budgetNameIcon);
        budgetName = new JTextField(10);
        introPanelTop.add(budgetName);

        JLabel budgetCostIcon = new JLabel("Budget Cost: ");
        introPanelBottom.add(budgetCostIcon);
        budgetCost = new JTextField(10);
        introPanelBottom.add(budgetCost);

        confirmButton = new JButton("Press to confirm");
        introPanelButton.add(confirmButton);
        confirmButton.setActionCommand("Intro Button");
        frame.add(introPanel);
        confirmButton.addActionListener(this);
        frame.setVisible(true);

    }


    private void update() {
        frame.getContentPane().removeAll();
        menuCreator();
        leftFrame();
        rightFrame();
        bottomLeftFrame();
        bottomRightFrame();
        frame.repaint();
        frame.setVisible(true);
    }

    private void leftFrame() {
        JList<String> list = new JList<>(userBudget.printBudgetSummary().toArray(new String[0]));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        frame.add(leftPanel, BorderLayout.WEST);
        leftPanel.add(new JScrollPane(list));
    }

    private void rightFrame() {
        createRightPanels();

        JLabel addExpenseText = new JLabel("Type in fields and press button to add an expense :");
        container1.add(addExpenseText);

        JLabel expenseNameText = new JLabel("Expense Name: ");
        container2.add(expenseNameText);

        textExpenseName = new JTextField(10);
        container2.add(textExpenseName);

        JLabel costText = new JLabel("Expense Cost: ");
        container3.add(costText);

        textCostName = new JTextField(10);
        container3.add(textCostName);

        JLabel textCategoryName = new JLabel("Expense Category: ");
        container4.add(textCategoryName);

        String[] categories = {"Transportation", "Groceries and Food", "Entertainment", "Housing"};
        categoryBox = new JComboBox(categories);
        container4.add(categoryBox);

        JButton toAddExpense = new JButton("Press to add expense");
        toAddExpense.addActionListener(this);
        toAddExpense.setActionCommand("Add Button");
        container5.add(toAddExpense);
    }

    private void createRightPanels() {
        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(5, 1, 5, 2));
        frame.add(rightPanel, BorderLayout.CENTER);

        container1 = new JPanel();
        rightPanel.add(container1);

        container2 = new JPanel();
        rightPanel.add(container2);

        container3 = new JPanel();
        rightPanel.add(container3);

        container4 = new JPanel();
        rightPanel.add(container4);

        container5 = new JPanel();
        rightPanel.add(container5);
    }


    private void bottomLeftFrame() {
        bottomContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel bottomLeft = new JPanel(new GridLayout(5, 1, 2, 2));
        JLabel summary = new JLabel(displayBudgetCheck());
        summary.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
        JLabel categoryCost1 = new JLabel("You have spent "
                + userBudget.addUpList("Transportation") + "$ on transportation.");
        categoryCost1.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
        JLabel categoryCost2 = new JLabel("You have spent "
                + userBudget.addUpList("Groceries and Food") + "$ on groceries and food.");
        categoryCost2.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
        JLabel categoryCost3 = new JLabel("You have spent "
                + userBudget.addUpList("Entertainment") + "$ on entertainment.");
        categoryCost3.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
        JLabel categoryCost4 = new JLabel("You have spent "
                + userBudget.addUpList("Housing") + "$ on housing.");
        categoryCost4.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
        bottomLeft.add(summary);
        bottomLeft.add(categoryCost1);
        bottomLeft.add(categoryCost2);
        bottomLeft.add(categoryCost3);
        bottomLeft.add(categoryCost4);

        bottomContainer.add(bottomLeft, BorderLayout.WEST);
        frame.add(bottomContainer, BorderLayout.SOUTH);
    }

    private void bottomRightFrame() {
        JPanel bottomRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton removeExpense = new JButton("Remove Button");
        removeExpense.addActionListener(this);
        removeExpense.setActionCommand("Remove Button");
        bottomRight.add(removeExpense);

        removeExpenseBox = new JComboBox(printList().toArray(new String[0]));
        bottomRight.add(removeExpenseBox);
        bottomContainer.add(bottomRight, BorderLayout.EAST);
    }

    private ArrayList<String> printList() {
        ArrayList<String> expenseList = new ArrayList<>();
        if (userBudget.getExpenseList().isEmpty()) {
            expenseList.add("No expenses in budget!");
        }
        for (Expense s : userBudget.getExpenseList()) {
            expenseList.add(s.getName());
        }
        return expenseList;
    }

    private void menuCreator() {
        JMenuBar bar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");
        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(this);
        save.setActionCommand("Save");

        JMenuItem load = new JMenuItem("Load");
        load.addActionListener(this);
        load.setActionCommand("Load");

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(this);
        exit.setActionCommand("Exit");

        optionsMenu.add(save);
        optionsMenu.add(load);
        optionsMenu.add(exit);
        bar.add(optionsMenu);
        frame.setJMenuBar(bar);
    }

    public void actionPerformed(ActionEvent e) {
        if (Objects.equals(e.getActionCommand(), "Save")) {
            saveBudget();
        } else if (Objects.equals(e.getActionCommand(), "Load")) {
            loadBudget();
            update();
        } else if (e.getActionCommand().equals("Exit")) {
            System.exit(0);
        } else if (e.getActionCommand().equals("Remove Button")) {
            removeExpense();
            update();
        } else if (e.getActionCommand().equals("Add Button")) {
            addExpense();
            update();
        } else if (e.getActionCommand().equals("Intro Button")) {
            userBudget = new Budget(budgetName.getText(), Double.parseDouble(budgetCost.getText()));
            update();
        }
    }

    private void addExpense() {
        String name = textExpenseName.getText();
        String category = (String) categoryBox.getSelectedItem();
        double cost = Double.parseDouble(textCostName.getText());
        userBudget.addExpense(new Expense(name,cost,category));
    }

    private void removeExpense() {
        ArrayList<String> expenseStringList = convertList(userBudget.getExpenseList());
        if (expenseStringList.contains(removeExpenseBox.getSelectedItem())) {
            int index = expenseStringList.indexOf(removeExpenseBox.getSelectedItem());
            Expense expenseToRemove = userBudget.getExpenseList().get(index);
            userBudget.removeExpense(expenseToRemove);
        }
    }

    // Effects: Helper for display budget, checks if user has exceeded budget,
    // if so displays amount over budget, else shows remaining budget amount
    public String displayBudgetCheck() {
        if (userBudget.getBalance() <= 0) {
            return "You have 0$ left to spend. You are "
                    + abs(userBudget.getBalance()) + "$ over your budget.";
        }
        return "You have " + userBudget.getBalance() + "$ left to spend.";
    }


    // Effects: converts list to string with each name
    public ArrayList<String> convertList(ArrayList<Expense> expenseList) {
        ArrayList<String> stringList = new ArrayList<>();
        for (Expense expense : expenseList) {
            stringList.add(expense.getName());
        }
        return stringList;
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
    }
}







