import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Customer {



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

    //변수 및 리스트 선언
    private String customerName = null;
    private String email = null;
    private RateType rating = null;

    private List<Customer> customers = new ArrayList<>();
    private List<Cart> cartItems = null;
    private List<Cart> paidItems = null;

    public Customer(String customerName, String email) {
        this.customerName = customerName;
        this.email = email;
        this.rating = RateType.BRONZE;
        this.cartItems = new ArrayList<>();
        this.paidItems = new ArrayList<>();
        customers.add(this);
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public List<Cart> getCartItems() {
        return this.cartItems;
    }

    public List<Cart> getPaidItems() {
        return this.paidItems;
    }

    public void addCartItems(Cart cart) {
        cartItems.add(cart);
    }

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

    public void printCartItemsInfo() {
        System.out.println("아래와 같이 주문 하시겠습니까?\n");
        System.out.println("[ 장바구니 내역 ]");
        if(cartItems.isEmpty()) {
            System.out.println("현재 담아놓은 상품이 없습니다.");
        } else {
            cartItems.forEach(ci -> System.out.printf("%-10s | %-,10d원 | %-15s | 수량: %d개%n", ci.getProduct().getProdName(), ci.getProduct().getPrice(), ci.getProduct().getProdContent(), ci.getAmount()));
            System.out.println("\n[ 총 주문 금액 ]");
            int totalPrice = getTotalPrice();
            System.out.printf("%-,10d원%n%n", totalPrice);
        }
    }

    public void completeOrder() {
        this.paidItems.addAll(new ArrayList<>(this.cartItems));
        this.cartItems.clear();
    }

    public void printPaidItemsInfo() {
        System.out.println("[ 주문 내역 ]");

        IntStream
                .range(0, paidItems.size())
                .forEach(
                        i -> {
                            Cart temp = paidItems.get(i);
                            System.out.printf("%d. %-10s | %d개 | %-,10d원%n", (i+1), temp.getProduct().getProdName(), temp.getAmount(), temp.getPrice());
                        }
                );
    }

    public void cancelPaidItem(int num) {
        if(num >= 0 && num < paidItems.size()) {
            Cart itemToCancel = paidItems.get(num);

            // 1. 해당 상품의 재고 복구
            itemToCancel.getProduct().returnLeftCnt(itemToCancel.getAmount());

            // 2. 결제 내역 리스트에서 삭제
            paidItems.remove(num);

            System.out.println("해당 주문이 정상적으로 취소 및 환불 처리되었습니다.");
        } else {
            System.out.println("잘못된 번호입니다. 취소에 실패했습니다.");
        }
    }

    public boolean checkVerification() {
        return customers.stream().anyMatch(c -> c.getCustomerName().equals(this.getCustomerName()));
    }

    public int getTotalPrice() {
        return cartItems.stream().mapToInt(Cart::getPrice).sum();
    }



}
