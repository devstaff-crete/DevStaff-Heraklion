package patterns.builder;

import org.junit.Test;
import patterns.OrderSerializerSharedTests;


public class OrderSerializerWithBuilderTest {

    @Test
    public void testOrderWithoutProducts() {
        OrderSerializerSharedTests tests = new OrderSerializerSharedTests();
        tests.testOrderWithoutProducts(new OrderSerializerWithBuilder());
    }

    @Test
    public void testOrderWithTwoProducts() {
        OrderSerializerSharedTests tests = new OrderSerializerSharedTests();
        tests.testOrderWithTwoProducts(new OrderSerializerWithBuilder());
    }
}
