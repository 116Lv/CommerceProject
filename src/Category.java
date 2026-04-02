import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Category {

    public enum CategoryType {
        ELEC("전자제품"),
        CLOTHES("의류"),
        FOOD("식품");

        private final String categoryName;

        CategoryType(String categoryName) {
            this.categoryName = categoryName;
        }

        public static CategoryType searchSameType(String categoryName) {
            return Arrays.stream(CategoryType.values())
                    .filter(ct -> ct.getCategoryName().equals(categoryName))
                    .findFirst()
                    .orElseThrow();
        }

        public String getCategoryName() {
            return categoryName;
        }
    }

    // 변수 및 리스트 선언
    private CategoryType type = null;
    private List<Product> products = null;

    // 생성자 (카테고리이름을 곁들인..)
    public Category(String categoryName) {
        this.type = CategoryType.searchSameType(categoryName);
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    // Getter
    public CategoryType getType() {
        return this.type;
    }

    public List<Product> getProducts() {
        return this.products;
    }


}
