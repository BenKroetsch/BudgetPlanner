package persistence;

import model.Budget;
import model.Expense;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    //the following methods are partially credited to JsonSerializationDemo

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    //EFFECTS: reads workroom from file and returns it;
    //throws IOException if an error occurs reading data from file
    public Budget read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBudget(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses budget from JSON object and returns it
    private Budget parseBudget(JSONObject jsonObject) {
        String name = jsonObject.getString("Budget Name");
        String budgetString = jsonObject.getString("Budget Amount");
        Integer budgetInteger = Integer.valueOf(budgetString);
        Budget budget = new Budget(name, budgetInteger);
        addExpenses(budget, jsonObject);
        return budget;
    }

    // MODIFIES: budget
    // EFFECTS: parses expense from JSON object and adds them to budget
    private void addExpenses(Budget budget, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Expense List");
        for (Object json : jsonArray) {
            JSONObject nextExpense = (JSONObject) json;
            addExpense(budget, nextExpense);
        }
    }

    // MODIFIES: budget
    // EFFECTS: parses expenses from JSON object and adds it to workroom
    private void addExpense(Budget budget, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String costString = jsonObject.getString("cost");
        String category = jsonObject.getString("category");
        Integer costInteger = Integer.valueOf(costString);
        Expense expense = new Expense(name, costInteger, category);
        budget.addExpense(expense);
    }
}




