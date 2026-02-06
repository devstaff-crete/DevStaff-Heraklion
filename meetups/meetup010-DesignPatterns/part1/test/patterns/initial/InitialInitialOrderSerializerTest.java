package patterns.initial;

import org.junit.Test;
import patterns.OrderSerializerSharedTests;

public class InitialInitialOrderSerializerTest {

    @Test
    public void testOrderWithoutProducts() {
        OrderSerializerSharedTests tests = new OrderSerializerSharedTests();
        tests.testOrderWithoutProducts(new InitialOrderSerializer());
    }

    @Test
    public void testOrderWithTwoProducts() {
        OrderSerializerSharedTests tests = new OrderSerializerSharedTests();
        tests.testOrderWithTwoProducts(new InitialOrderSerializer());
    }
}
