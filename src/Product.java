public class Product {
    // 변수 선언
    private String prodName = null;    // 상품명
    private int price = 0;          // 가격
    private String prodContent = null; // 설명
    private int leftCnt = 0;        // 재고수량

    // 상품객체 생성자
    public Product(String prodName, int price, String prodContent) {
        this.prodName = prodName;
        this.price = price;
        this.prodContent = prodContent;
        this.leftCnt = 100;
    }

    // Getter
    public String getProdName() {
        return this.prodName;
    }

    public int getPrice() {
        return this.price;
    }

    public String getProdContent() {
        return this.prodContent;
    }

    public int getLeftCnt() {
        return this.leftCnt;
    }

    public String getPrintListInfo() {
        return String.format("%-13s| %,9d원 | %s", prodName, price, prodContent);
    }

    public String getPrintProductInfo() {
        return String.format("%s | %,d원 | %s | 재고: %,d개", prodName, price, prodContent, leftCnt);
    }

    //Setter
    // 장바구니에 담을때
    public void setLeftCnt(int amount) {
        this.leftCnt -= amount;
    }

    // 주문취소시 재고 다시 채울때
    public void returnLeftCnt(int amount) {
        this.leftCnt += amount;
    }
}
