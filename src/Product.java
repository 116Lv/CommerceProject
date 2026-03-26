public class Product {
    String prodName;    // 상품명
    int price;          // 가격
    String prodContent; // 설명
    int leftCnt;        // 재고수량

    public Product(String prodName, int price, String prodContent, int leftCnt) {
        this.prodName = prodName;
        this.price = price;
        this.prodContent = prodContent;
        this.leftCnt = leftCnt;
    }

}
