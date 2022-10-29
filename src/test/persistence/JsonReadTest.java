package persistence;

import model.Budget;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReadTest extends JsonTest{

    //the following methods are partially credited to JsonSerializationDemo

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Budget budget = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyBudget() {
        JsonReader reader = new JsonReader("./data/testWriterEmptyBudget.json");
        try {
            Budget budget = reader.read();
            assertEquals("July Budget", budget.getName());
            assertEquals(0, budget.getExpenseList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralBudget() {
        JsonReader reader = new JsonReader("./data/testWriterGeneralBudget.json");
        try {
            Budget budget = reader.read();
            assertEquals("July Budget", budget.getName());
            assertEquals(2, budget.getExpenseList().size());
            checkExpense("Coffee", 5, "Transportation", budget.getExpenseList().get(0));
            checkExpense("Ben", 51, "Groceries and Food", budget.getExpenseList().get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
