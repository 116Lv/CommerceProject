public class Product {
    String prodName;    // 상품명
    int price;          // 가격
    String prodContent; // 설명
    int leftCnt;        // 재고수량

    public Product(String prodName, int price, String prodContent) {
        this.prodName = prodName;
        this.price = price;
        this.prodContent = prodContent;
        this.leftCnt = 0;
    }

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
