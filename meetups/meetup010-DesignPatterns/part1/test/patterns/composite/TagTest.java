package patterns.composite;

import org.junit.Test;

import static org.junit.Assert.*;

public class TagTest {
    @Test
    public void testSimpleTagWithOneAttributeAndValue() {
        TagNode priceTag = new TagNode("price");
        priceTag.addAttribute("currency", "EUR");
        priceTag.addValue("5.00");

        String expectedXML =
                "<price currency='EUR'>" +
                        "5.00" +
                "</price>";
        String actualXML = priceTag.toString();

        assertEquals("Add property to a XML node", expectedXML, actualXML);
    }

    @Test
    public void testNodeWithOneChild() {
        TagNode productTag = new TagNode("product");
        productTag.add(new TagNode("price"));

        String expectedXML =
                "<product>"+
                        "<price>" +
                        "</price>" +
                "</product>";

        String actualXML = productTag.toString();

        assertEquals("Node with 1 child", expectedXML, actualXML);
    }

    @Test
    public void testNestedNodes() {
        TagNode ordersTag = new TagNode("orders");
        TagNode orderTag = new TagNode("order");
        orderTag.addAttribute("id", "1");

        TagNode nameTag = new TagNode("name");
        nameTag.addValue("TDD");

        TagNode productTag = new TagNode("product");
        productTag.addAttribute("id", "1");
        productTag.add(nameTag);

        ordersTag.add(orderTag);
        orderTag.add(productTag);

        String expectedXML =
                "<orders>"+
                        "<order id='1'>" +
                            "<product id='1'>" +
                                "<name>" +
                                    "TDD" +
                                "</name>" +
                            "</product>" +
                        "</order>" +
                "</orders>";

        String actualXML = ordersTag.toString();

        assertEquals("Nested nodes", expectedXML, actualXML);
    }

    @Test
    public void testParents() {
        TagNode root = new TagNode("root");
        assertNull(root.getParent());

        TagNode child = new TagNode("child");
        root.add(child);
        assertEquals(root, child.getParent());
    }
}
