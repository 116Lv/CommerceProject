import java.util.ArrayList;
import java.util.List;

public class Customer {

    //변수선언
    private String customerName = null;
    private String email = null;
    private String rating = null;

    private List<Customer> customers = new ArrayList<>();

    public Customer(String customerName, String email) {
        this.customerName = customerName;
        this.email = email;
        this.rating = "BRONZE";
    }

}
