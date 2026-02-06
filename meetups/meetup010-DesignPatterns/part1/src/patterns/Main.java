package patterns;

import patterns.builder.OrderSerializerWithBuilder;

public class Main {

    public static void main(String[] args) {
        Product[] productsForOrder1 = {
                new Product(1, "Design Patterns"),
                new Product(2, "Refactoring")
        };

        Product[] productsForOrder2 = {
                new Product(3, "TDD"),
        };

        Order[] orders = {
                new Order(1, productsForOrder1),
                new Order(2, productsForOrder2)
        };

        Serializer serializer = new OrderSerializerWithBuilder();
        String xml = serializer.toXML(orders);
        System.out.println(xml);
    }
}
