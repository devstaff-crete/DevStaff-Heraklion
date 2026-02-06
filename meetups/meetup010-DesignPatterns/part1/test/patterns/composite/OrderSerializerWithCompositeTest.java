package patterns.composite;

import org.junit.Test;
import patterns.OrderSerializerSharedTests;

public class OrderSerializerWithCompositeTest {

    @Test
    public void testOrderWithoutProducts() {
        OrderSerializerSharedTests tests = new OrderSerializerSharedTests();
        tests.testOrderWithoutProducts(new OrderSerializerWithComposite());
    }

    @Test
    public void testOrderWithTwoProducts() {
        OrderSerializerSharedTests tests = new OrderSerializerSharedTests();
        tests.testOrderWithTwoProducts(new OrderSerializerWithComposite());
    }
}
