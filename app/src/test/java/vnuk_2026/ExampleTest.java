package vnuk_2026;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ExampleTest {

    @Test
    public void testAddition() {
        int a = 2;
        int b = 3;
        int expected = 5;
        int actual = a + b;

        Assert.assertEquals(actual, expected, "Addition should return the correct sum");
    }

    @Test
    public void testSubtraction() {
        int a = 5;
        int b = 2;
        int expected = 3;
        int actual = a - b;

        Assert.assertEquals(actual, expected, "Subtraction should return the correct difference");
    }

}
