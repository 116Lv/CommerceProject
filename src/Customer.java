import java.util.ArrayList;
import java.util.List;

public class Customer {

    // 고객의 멤버십 등급을 정의하는 Enum
    public enum RateType {
        BRONZE("브론즈"),
        SILVER("실버"),
        GOLD("골드"),
        PLATINUM("플래티넘");

        private final String rating;

        RateType(String rating) {
            this.rating = rating;
        }

        public String getRatingName() {
            return rating;
        }
    }

    // --- 필드 선언 ---
    private String customerName = null; // 고객명
    private String email = null;        // 이메일 주소
    private RateType rating = null;     // 고객 등급

    private List<Cart> cartItems = null;                    // 현재 장바구니에 담긴 항목 리스트
    private List<Cart> paidItems = null;                    // 결제가 완료된 주문 내역 리스트

    // 고객 생성자 (기본 등급은 BRONZE로 설정, 장바구니와 구매 리스트를 초기화)
    public Customer(String customerName, String email) {
        this.customerName = customerName;
        this.email = email;
        this.rating = RateType.BRONZE;
        this.cartItems = new ArrayList<>();
        this.paidItems = new ArrayList<>();
    }

    // --- Getter 영역 ---
    public String getCustomerName() {
        return this.customerName;
    }

    public String getEmail() {
        return this.email;
    }

    public List<Cart> getCartItems() {
        return this.cartItems;
    }

    public List<Cart> getPaidItems() {
        return this.paidItems;
    }


    // 장바구니에 새로운 상품 항목을 추가
    public void addCartItems(Cart cart) {
        cartItems.add(cart);
    }

    // 장바구니에 이미 존재하는 상품의 수량을 업데이트
    public void editCartItems(Product product, int addAmount) {
        this.cartItems
                .stream()
                .filter(c -> c.getProduct().equals(product))
                .findFirst()
                .ifPresent(c-> {
                    c.setAmount(addAmount);
                    c.updatePrice();
                });
    }

    // 주문을 확정 (장바구니의 항목들을 구매 내역으로 이동, 장바구니 비움)
    public void completeOrder() {
        this.paidItems.addAll(new ArrayList<>(this.cartItems));
        this.cartItems.clear();
    }

    // 장바구니에 담긴 모든 상품의 총합 금액을 계산하여 반환
    public int getTotalPrice() {
        return cartItems.stream().mapToInt(Cart::getPrice).sum();
    }

}
