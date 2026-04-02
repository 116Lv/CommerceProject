package Resources;
import java.util.ArrayList;
import java.util.List;

public class Category {

    // 플랫폼에서 취급하는 카테고리 종류를 정의한 내부 Enum
    public enum CategoryType {
        ELEC("전자제품"),
        CLOTHES("의류"),
        FOOD("식품");

        private final String categoryName;

        CategoryType(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryName() {
            return categoryName;
        }
    }

    // --- Category 필드 선언 ---
    private CategoryType type = null;
    private List<Product> products = null;

    // 카테고리 생성자
    public Category(CategoryType type) {
        // 문자열 이름을 기반으로 Enum 타입을 찾아 할당
        this.type = type;
        this.products = new ArrayList<>();
    }

    // 현재 카테고리에 새로운 상품을 등록
    public void addProduct(Product product) {
        products.add(product);
    }

    // --- Getter 영역 ---
    public CategoryType getType() {
        return this.type;
    }

    public List<Product> getProducts() {
        return this.products;
    }


}
