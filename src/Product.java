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

    public String getPrintInfo() {
        return String.format("%-15s| %,10d원 | %s", prodName, price, prodContent);
    }
}
