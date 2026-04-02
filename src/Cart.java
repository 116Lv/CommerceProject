import java.util.ArrayList;
import java.util.List;

public class Cart {

    private Customer customer = null;
    private Product product = null;
    private int amount = 0;
    private int price = 0;

    public Cart(Customer customer, Product product, int amount) {
        this.customer = customer;
        this.product = product;
        this.amount = amount;
        this.price = calcTotalPrice();
    }

    public int calcTotalPrice() {
        return product.getPrice() * amount;
    }

    public Product getProduct() {
        return this.product;
    }

    public int getAmount() {
        return this.amount;
    }

    public int getPrice() {
        return this.price;
    }

    public void setAmount(int newAmount) {
        this.amount += newAmount;
    }

    public void updatePrice() {
        this.price = calcTotalPrice();
    }
}
