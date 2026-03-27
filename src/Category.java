import java.util.ArrayList;
import java.util.List;

public class Category {

    // 변수 및 리스트 선언
    private String categoryName = null;
    private List<Product> products = null;

    // 생성자 (카테고리이름을 곁들인..)
    public Category(String categoryName) {
        this.categoryName = categoryName;
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    // Getter
    public String getCategoryName() {
        return this.categoryName;
    }

    public List<Product> getProducts() {
        return this.products;
    }

}
