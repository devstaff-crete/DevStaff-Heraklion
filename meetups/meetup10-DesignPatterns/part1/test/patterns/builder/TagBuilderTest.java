package patterns.builder;

import org.junit.Test;

import static org.junit.Assert.*;

public class TagBuilderTest {
    @Test
    public void testBuildOneNode() throws Exception {
        String expectedXML = "<orders></orders>";

        String actualXML = new TagBuilder("orders").toXml();

        assertEquals("Build XML from one node", expectedXML, actualXML);
    }

    @Test
    public void testBuildNodeWithOneChild() throws Exception {
        String expectedXML = "<orders><order></order></orders>";

        TagBuilder builder = new TagBuilder("orders");
        builder.addChild("order");

        String actualXML = builder.toXml();

        assertEquals("Build XML from one node", expectedXML, actualXML);
    }

    @Test
    public void testBuildNestedNodes() throws Exception {
        String expectedXML = "<orders><order><product></product></order></orders>";

        TagBuilder builder = new TagBuilder("orders");
        builder.addChild("order");
        builder.addChild("product");

        String actualXML = builder.toXml();

        assertEquals("Build XML from one node", expectedXML, actualXML);
    }

    @Test
    public void testBuildSiblingNodes() throws Exception {
        String expectedXML =
                "<orders>" +
                        "<order>" +
                                "<product1></product1>" +
                                "<product2></product2>" +
                        "</order>" +
                "</orders>";

        TagBuilder builder = new TagBuilder("orders");
        builder.addChild("order");
        builder.addChild("product1");
        builder.addSibling("product2");

        String actualXML = builder.toXml();

        assertEquals("Build XML from one node", expectedXML, actualXML);
    }

    @Test
    public void testBuildRepeatingChildren() throws Exception {
        String expectedXML =
                "<orders>" +
                        "<order>" +
                            "<product>" +
                                "<name>" +
                                "</name>" +
                            "</product>" +
                        "</order>" +
                        "<order>" +
                            "<product>" +
                                "<name>" +
                                "</name>" +
                            "</product>" +
                        "</order>" +
                "</orders>";

        TagBuilder builder = new TagBuilder("orders");
        for (int i = 0; i < 2; i++) {
            builder.addToParent("orders", "order");
            builder.addChild("product");
            builder.addChild("name");
        }

        String actualXML = builder.toXml();

        assertEquals("Build XML from one node", expectedXML, actualXML);
    }

    @Test
    public void testBuildNodesWithAttributesAndValues() {
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

        TagBuilder builder = new TagBuilder("orders");
        builder.addChild("order");
        builder.addAttribute("id", "1");

        builder.addChild("product");
        builder.addAttribute("id", "1");

        builder.addChild("name");
        builder.addValue("TDD");

        String actualXML = builder.toXml();

        assertEquals("Build XML from one node", expectedXML, actualXML);
    }
}
