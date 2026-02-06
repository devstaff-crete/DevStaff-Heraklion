package patterns;

import static org.junit.Assert.assertEquals;

public class OrderSerializerSharedTests {
    public void testOrderWithoutProducts(Serializer serializer) {
        Product[] productsForOrder1 = new Product[0];

        Order[] orders = {
                new Order(1, productsForOrder1)
        };

        String actualXML = serializer.toXML(orders);
        String expectedXML = "<orders><order id='1'></order></orders>";

        assertEquals("Generated XML for order without any products", expectedXML, actualXML);
    }

    public void testOrderWithTwoProducts(Serializer serializer) {
        Product[] productsForOrder1 = {
                new Product(1, "Design Patterns"),
                new Product(2, "Refactoring")
        };

        Order[] orders = {
                new Order(1, productsForOrder1)
        };

        String actualXML = serializer.toXML(orders);
        String expectedXML =
                "<orders>" +
                    "<order id='1'>" +
                        "<product id='1'><name>Design Patterns</name></product>" +
                        "<product id='2'><name>Refactoring</name></product>" +
                    "</order>" +
                "</orders>";

        assertEquals("Generated XML for order with 2 products", expectedXML, actualXML);
    }
}
