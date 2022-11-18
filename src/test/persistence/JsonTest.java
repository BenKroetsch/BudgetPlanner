package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import model.Expense;

public class JsonTest {
    //the following methods are partially credited to JsonSerializationDemo

    protected void checkExpense(String name, Integer cost, String category, Expense expense) {
        assertEquals(name, expense.getName());
        assertEquals(category, expense.getCategory());
        assertEquals(cost, expense.getCost());
    }
}
