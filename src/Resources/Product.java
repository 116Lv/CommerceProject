package Resources;
public class Product {
    // 상품 필드 선언
    private String prodName = null;    // 상품명
    private int price = 0;          // 가격
    private String prodContent = null; // 설명
    private int leftCnt = 0;        // 재고수량


    // 상품 객체 생성자 (초기 재고는 기본값인 100개로 설정)
    public Product(String prodName, int price, String prodContent) {
        this.prodName = prodName;
        this.price = price;
        this.prodContent = prodContent;
        this.leftCnt = 100;
    }

    // --- Getter 영역: 데이터 조회 ---
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

    // 카테고리별 상품 목록 출력 시 사용되는 정렬된 문자열 정보를 반환합니다.
    public String getPrintListInfo() {
        return String.format("%-13s| %,9d원 | %s", prodName, price, prodContent);
    }


    // 특정 상품의 상세 정보를 재고 현황과 함께 문자열로 반환합니다.
    public String getPrintProductInfo() {
        return String.format("%s | %,d원 | %s | 재고: %,d개", prodName, price, prodContent, leftCnt);
    }

    // --- Setter 및 재고 관리 영역 ---

    public boolean isAvailable(int amount) {
        return this.leftCnt >= amount;
    }

    // 주문 확정 시 상품의 재고를 차감
    public void reduceLeftCnt(int amount) {
        if(isAvailable(amount)) this.leftCnt -= amount;
    }

    // 주문 취소(환불) 발생 시 차감되었던 재고를 다시 원래대로 복구
    public void returnLeftCnt(int amount) {
        this.leftCnt += amount;
    }
}
