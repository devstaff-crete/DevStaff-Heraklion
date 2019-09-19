package patterns.builder;

import patterns.composite.TagNode;

class TagBuilder {
    private final TagNode root;
    private TagNode currentNode;

    public TagBuilder(String name) {
        root = new TagNode(name);
        currentNode = root;
    }

    public String toXml() {
        return root.toString();
    }

    public void addToParent(String parentName, String childName) {
        TagNode parent = findParent(parentName);
        if (parent == null)
            throw new RuntimeException("Missing parent tag: " + parentName);

        addTo(parent, childName);
    }

    public void addChild(String name) {
        addTo(currentNode, name);
    }

    public void addSibling(String name) {
        addTo(currentNode.getParent(), name);
    }

    public void addAttribute(String attribute, String value) {
        currentNode.addAttribute(attribute, value);
    }

    public void addValue(String value) {
        currentNode.addValue(value);
    }

    private void addTo(TagNode parent, String name) {
        currentNode = new TagNode(name);
        parent.add(currentNode);
    }

    private TagNode findParent(String name) {
        TagNode parent = currentNode;
        while (parent != null) {
            if (name.equals(parent.getName()))
                return parent;
            parent = parent.getParent();
        }
        return null;
    }
}
