package patterns.initial;

import patterns.Order;
import patterns.Product;
import patterns.Serializer;

public class InitialOrderSerializer implements Serializer {
    @Override
    public String toXML(Order[] orders) {
        StringBuffer xml = new StringBuffer();
        xml.append("<orders>");
        for (Order order : orders) {
            xml.append("<order");
            xml.append(" id='");
            xml.append(order.getId());
            xml.append("'>");
            Product[] products = order.getProducts();
            for (Product product : products) {
                xml.append("<product");
                xml.append(" id='");
                xml.append(product.getId());
                xml.append("'>");
                xml.append("<name>");
                xml.append(product.getName());
                xml.append("</name>");
                xml.append("</product>");
            }
            xml.append("</order>");
        }
        xml.append("</orders>");
        return xml.toString();
    }
}
