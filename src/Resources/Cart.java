package Resources;
public class Cart {

    // --- 필드 선언 ---
    private Product product = null;
    private int amount = 0;
    private int price = 0;

    // 장바구니 항목 생성자 (수량을 먼저 설정 후 그에 맞는 총 금액을 계산하여 초기화)
    public Cart(Product product, int amount) {
        this.product = product;
        this.amount = amount;
        this.price = calcTotalPrice();
    }

    // 상품의 단가와 현재 담긴 수량을 곱하여 총 가격을 산출
    public int calcTotalPrice() {
        return product.getPrice() * amount;
    }

    // --- Getter 영역 ---
    public Product getProduct() {
        return this.product;
    }

    public int getAmount() {
        return this.amount;
    }

    public int getPrice() {
        return this.price;
    }

    // --- 데이터 업데이트 영역 ---
    // 기존에 담긴 수량에 새로운 수량을 추가
    public void setAmount(int newAmount) {
        this.amount += newAmount;
    }

    // 수량이 변경되었을 때, 변경된 수량을 기준으로 총 금액을 다시 계산하여 갱신
    // 주의사항 : Customer.editCartItems() 등에서 수량 변경 후 호출 필수
    public void updatePrice() {
        this.price = calcTotalPrice();
    }
}
