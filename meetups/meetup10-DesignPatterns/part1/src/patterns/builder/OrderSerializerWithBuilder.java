package patterns.builder;

import patterns.Order;
import patterns.Product;
import patterns.Serializer;

public class OrderSerializerWithBuilder implements Serializer{
    @Override
    public String toXML(Order[] orders) {
        TagBuilder builder = new TagBuilder("orders");
        for (Order order : orders) {
            builder.addToParent("orders", "order");
            builder.addAttribute("id", "1");

            Product[] products = order.getProducts();
            for (Product product : products) {
                builder.addToParent("order", "product");
                builder.addAttribute("id", String.valueOf(product.getId()));
                builder.addChild("name");
                builder.addValue(product.getName());
            }
        }

        return builder.toXml();
    }
}
