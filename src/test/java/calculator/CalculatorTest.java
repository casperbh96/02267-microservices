package calculator;

import com.calculator.Calculator;
import com.calculator.Customer;
import com.calculator.Token;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.UUID;

public class CalculatorTest {

    @Test
    public final void when2NumbersAreUsedThenNoExceptionIsThrown() {
        Calculator.add("1,2");
        Assert.assertTrue(true);
    }

    @Test(expected = RuntimeException.class)
    public final void whenNonNumberIsUsedThenExceptionIsThrown() {
        Calculator.add("1,X");
    }

    @Test
    public final void whenEmptyStringIsUsedThenReturnValueIs0() {
        Assert.assertEquals(0, Calculator.add(""));
    }

    @Test
    public final void whenOneNumberIsUsedThenReturnValueIsThatSameNumber() {
        Assert.assertEquals(3, Calculator.add("3"));
    }

    @Test
    public final void whenTwoNumbersAreUsedThenReturnValueIsTheirSum() {
        Assert.assertEquals(3+6, Calculator.add("3,6"));
    }

    @Test
    public final void whenAnyNumberOfNumbersIsUsedThenReturnValuesAreTheirSums() {
        Assert.assertEquals(3+6+15+18+46+33, Calculator.add("3,6,15,18,46,33"));
    }

    @Test
    public final void whenNewLineIsUsedBetweenNumbersThenReturnValuesAreTheirSums() {
        Assert.assertEquals(3+6+15, Calculator.add("3,6\n15"));
    }

    /**
     * This test specifies delimiter using //[DELIMITER]\n format.
     * Actual delimiter is semicolon (;).
     * Expected outcome is that sum of all numbers separated with semicolon is returned.
     */
    @Test
    public final void whenSemicolonDelimiterIsSpecifiedThenItIsUsedToSeparateNumbers() {
        Assert.assertEquals(3+6+15, Calculator.add("//;\n3;6;15"));
    }

    @Test
    public final void WhenCustomerOnlyHasOneUnusedTokenAndOneUsedTokenThenHeIsValidToGetNewTokens(){
        //Arrange
        Customer c = new Customer();
        c.setName("Test");
        c.setId(1);
        ArrayList<Token> t = new ArrayList<>();

        //New unused token
        Token t1 = new Token();
        t1.setId(UUID.randomUUID());
        t1.setUsed(false);

        //new used token
        Token t2 = new Token();
        t2.setId(UUID.randomUUID());
        t2.setUsed(true);

        t.add(t1);
        t.add(t2);

        c.setTokens(t);

        //Act
        boolean actual = Calculator.CanCustomerGetTokens(c, 3);

        //Assert
        Assert.assertTrue(actual);
    }

    @Test
    public final void WhenCustomerHasTwoUnusedTokenThenHeIsNotValidToGetNewTokens(){
        //Arrange
        Customer c = new Customer();
        c.setName("Test");
        c.setId(1);
        ArrayList<Token> t = new ArrayList<>();

        //New unused token
        Token t1 = new Token();
        t1.setId(UUID.randomUUID());
        t1.setUsed(false);

        //new unused token
        Token t2 = new Token();
        t2.setId(UUID.randomUUID());
        t2.setUsed(false);

        t.add(t1);
        t.add(t2);

        c.setTokens(t);

        //Act
        boolean actual = Calculator.CanCustomerGetTokens(c, 3);

        //Assert
        Assert.assertFalse(actual);
    }

    @Test
    public final void WhenCustomerOnlyHasNoTokensThenHeIsValidToGetNewTokens(){
        //Arrange
        Customer c = new Customer();
        c.setName("Test");
        c.setId(1);

        ArrayList<Token> t = new ArrayList<>();
        c.setTokens(t);

        //Act
        boolean actual = Calculator.CanCustomerGetTokens(c, 5);

        //Assert
        Assert.assertTrue(actual);
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public final void WhenCustomerAskForTooManyTokensHeWillGetAnException() throws Exception{
        //Arrange
        expectedEx.expect(RuntimeException.class);

        Customer c = new Customer();
        c.setName("Test");
        c.setId(1);

        ArrayList<Token> t = new ArrayList<>();
        c.setTokens(t);

        int numTokens = 6;

        //Act
        expectedEx.expectMessage("Too many token request: " + numTokens);
        Calculator.CanCustomerGetTokens(c, numTokens);
    }
}