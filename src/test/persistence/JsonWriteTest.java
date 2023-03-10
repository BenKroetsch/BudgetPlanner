package persistence;

import model.Budget;
import model.Expense;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriteTest extends JsonTest {

    //the following methods are partially credited to JsonSerializationDemo
    @Test
    void testWriterInvalidFile() {
        try {
            Budget budget = new Budget("July Budget", 5.00);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterBudget() {
        try {
            Budget budget = new Budget("July Budget", 5.00);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBudget.json");
            writer.open();
            writer.write(budget);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyBudget.json");
            budget = reader.read();
            assertEquals("July Budget", budget.getName());
            assertEquals(5, budget.getBalance());
            assertEquals(0, budget.getExpenseList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralBudget() {
        try {
            Budget budget = new Budget("July Budget", 5.00);
            budget.addExpense(new Expense("Coffee", 5.00, "Transportation"));
            budget.addExpense(new Expense("Ben", 51.00, "Groceries and Food"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBudget.json");
            writer.open();
            writer.write(budget);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBudget.json");
            budget = reader.read();
            assertEquals("July Budget", budget.getName());
            assertEquals(-51, budget.getBalance());
            assertEquals(2, budget.getExpenseList().size());
            checkExpense("Coffee", 5.0, "Transportation", budget.getExpenseList().get(0));
            checkExpense("Ben", 51.0, "Groceries and Food", budget.getExpenseList().get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
