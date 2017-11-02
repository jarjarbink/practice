import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import practice.StringAccumulator;
import practice.processor.DefaultProcessor;
import practice.validator.DefaultValidator;
import practice.validator.NegativeValidator;

public class StringAccumulatorTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test @Category(UnitTests.class)
    public void testWithPossitieNumberOnly() throws Exception {
        StringAccumulator positiveNumberTest = new StringAccumulator(new DefaultValidator(), new DefaultProcessor());
        Assert.assertEquals(new Integer(6), positiveNumberTest.add("1,2,3"));
        Assert.assertEquals(new Integer(12), positiveNumberTest.add("3,\n4,\n5"));
        Assert.assertEquals(new Integer(7), positiveNumberTest.add("3,\n4,\n1000"));
        Assert.assertEquals(new Integer(1006), positiveNumberTest.add("3,\n4,\n999"));
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Ending with line break is not allowed.");
        Assert.assertEquals(new Integer(12), positiveNumberTest.add("3,\n4,\n5\n"));
        Assert.assertEquals(new Integer(33), positiveNumberTest.add("3,\n4,\n5\n6,7,8"));
        Assert.assertEquals(new Integer(33), positiveNumberTest.add("//BBC\n3,\n4BBC\n5\n6BBC7BBC8"));
        Assert.assertEquals(new Integer(33), positiveNumberTest.add("//BBC|CCD\n3BBC\n4CCD\n5\n6BBC7CCD8"));
        Assert.assertEquals(new Integer(33), positiveNumberTest.add("//*^|%$3,\n4*^\n5\n6*^7*^8"));
        Assert.assertEquals(new Integer(33), positiveNumberTest.add("//$|#|@|!\n3$\n4#\n5\n6@7!8"));
        Assert.assertEquals(new Integer(33), positiveNumberTest.add("//$|#|@|!\n3$\n4#\n5\n6@@@@@!!!!!@@7!8"));
        Assert.assertEquals(new Integer(33), positiveNumberTest.add("//$|#|@|!\n3$\n4#\n5\n6@@@  @@!!!!  !@@7!8"));
        Assert.assertEquals(new Integer(34), positiveNumberTest.add("//$|#|@|!\n3$\n4#\n5\n6@@@  @@!!!!  !@@7!   9"));
    }

    @Test @Category(UnitTests.class)
    public void testWithNegativeNumberAsWell() throws Exception {
        StringAccumulator negativeNumberTester = new StringAccumulator(new NegativeValidator(new DefaultValidator()), new DefaultProcessor());
        // Reg part
        Assert.assertEquals(new Integer(6), negativeNumberTester.add("1,2,3"));
        Assert.assertEquals(new Integer(12), negativeNumberTester.add("3,\n4,\n5"));
        Assert.assertEquals(new Integer(7), negativeNumberTester.add("3,\n4,\n1000"));
        Assert.assertEquals(new Integer(1006), negativeNumberTester.add("3,\n4,\n999"));
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Ending with line break is not allowed.");
        Assert.assertEquals(new Integer(12), negativeNumberTester.add("3,\n4,\n5\n"));
        Assert.assertEquals(new Integer(33), negativeNumberTester.add("3,\n4,\n5\n6,7,8"));
        Assert.assertEquals(new Integer(33), negativeNumberTester.add("//BBC\n3,\n4BBC\n5\n6BBC7BBC8"));
        Assert.assertEquals(new Integer(33), negativeNumberTester.add("//BBC|CCD\n3BBC\n4CCD\n5\n6BBC7CCD8"));
        Assert.assertEquals(new Integer(33), negativeNumberTester.add("//*^|%$3,\n4*^\n5\n6*^7*^8"));
        Assert.assertEquals(new Integer(33), negativeNumberTester.add("//$|#|@|!\n3$\n4#\n5\n6@7!8"));
        Assert.assertEquals(new Integer(33), negativeNumberTester.add("//$|#|@|!\n3$\n4#\n5\n6@@@@@!!!!!@@7!8"));
        Assert.assertEquals(new Integer(33), negativeNumberTester.add("//$|#|@|!\n3$\n4#\n5\n6@@@  @@!!!!  !@@7!8"));
        Assert.assertEquals(new Integer(34), negativeNumberTester.add("//$|#|@|!\n3$\n4#\n5\n6@@@  @@!!!!  !@@7!   9"));

        //Neg part
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("negatives not allowed : [-5, -8]");
        Assert.assertEquals(new Integer(33), negativeNumberTester.add("//BBC\n3,\n4BBC\n-5\n6BBC-7BBC8"));
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("negatives not allowed : [-4, -5, -8]");
        Assert.assertEquals(new Integer(33), negativeNumberTester.add("//BBC|CCD\n3BBC\n-4CCD\n-5\n6BBC7CCD-8"));
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("negatives not allowed : [-3, -7]");
        Assert.assertEquals(new Integer(33), negativeNumberTester.add("//*^|%$-3,\n4*^\n5\n6*^-7*^8"));
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("negatives not allowed : [-6]");
        Assert.assertEquals(new Integer(33), negativeNumberTester.add("//$|#|@|!\n3$\n4#\n5\n-6@7!8"));
    }
}
