package patterns.composite;

import java.util.ArrayList;
import java.util.List;

public class TagNode {
    private final String name;
    private String value = "";
    private final StringBuffer attributes;
    private final List<TagNode> children;
    private TagNode parent;

    public TagNode(String name) {
        this.name = name;
        this.attributes = new StringBuffer("");
        this.children = new ArrayList<>();
    }

    public void addAttribute(String attribute, String value) {
        this.attributes.append(" ");
        this.attributes.append(attribute);
        this.attributes.append("='");
        this.attributes.append(value);
        this.attributes.append("'");
    }

    public void addValue(String value) {
        this.value = value;
    }

    public void add(TagNode node) {
        node.setParent(this);
        this.children.add(node);
    }

    @Override
    public String toString() {
        String result;
        result = "<" + name + attributes + ">";

        for (Object aChildren : this.children) {
            TagNode node = (TagNode) aChildren;
            result += node.toString();
        }

        result += value;
        result += "</" + name + ">";
        return result;
    }

    public void setParent(TagNode parent) {
        this.parent = parent;
    }
    public TagNode getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }
}
