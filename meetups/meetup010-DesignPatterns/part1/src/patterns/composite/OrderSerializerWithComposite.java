package patterns.composite;

import patterns.Order;
import patterns.Product;

public class OrderSerializerWithComposite implements patterns.Serializer {
    @Override
    public String toXML(Order[] orders) {
        TagNode ordersTag = new TagNode("orders");

        for (Order order : orders) {
            TagNode orderTag = new TagNode("order");
            orderTag.addAttribute("id", String.valueOf(order.getId()));

            Product[] products = order.getProducts();
            for (Product product : products) {
                TagNode nameTag = new TagNode("name");
                nameTag.addValue(product.getName());

                TagNode productTag = new TagNode("product");
                productTag.addAttribute("id", String.valueOf(product.getId()));

                productTag.add(nameTag);
                orderTag.add(productTag);
            }
            ordersTag.add(orderTag);
        }

        return ordersTag.toString();
    }
}
