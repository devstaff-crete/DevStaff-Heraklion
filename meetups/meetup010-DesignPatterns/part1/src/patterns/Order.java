package patterns;

public class Order {
    private final int id;
    private final Product[] products;

    public Order(int id, Product[] products) {
        this.id = id;
        this.products = products;
    }

    public int getId() {
        return this.id;
    }

    public Product[] getProducts() {
        return this.products;
    }
}
