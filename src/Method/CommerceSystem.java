package Method;

import java.util.*;
import java.util.stream.IntStream;
import Resources.Category;
import Resources.Product;
import Resources.Customer;
import Resources.Cart;

public class CommerceSystem {
    // --- 필드 선언 ---
    private int checkNum = 0;
    private boolean checkPoint = false;
    private List<Category> categories = null;
    private Customer currentCustomer = null;
    // Scanner 객체 생성
    Scanner scanner = new Scanner(System.in);

    // 시스템 생성자
    public CommerceSystem(Customer customer, List<Category> categories) {
        this.currentCustomer = customer;
        this.categories = categories;
    }

    // 메인메뉴 실행
    public void start() {
        do {
            printMainMenu();
            try {
                checkNum = scanner.nextInt();
                if(checkNum == 0) {
                    System.out.print("커머스 플랫폼을 종료합니다.");
                    checkPoint = true;
                } else if(0 < checkNum && checkNum <= categories.size()) {
                    handleCategorySelection(checkNum-1);
                } else if(checkNum == 4 && (!currentCustomer.getCartItems().isEmpty() || !currentCustomer.getPaidItems().isEmpty())) {
                    handleOrderProcess();
                } else if(checkNum == 5 && (!currentCustomer.getCartItems().isEmpty() || !currentCustomer.getPaidItems().isEmpty())) {
                    handleCancelProcess();
                } else {
                    System.out.println("잘못된 범위의 입력입니다.\n");
                }
            } catch(InputMismatchException e) {
                System.out.println("숫자만 입력 가능합니다.\n");
                scanner.nextLine();
            }
        } while(!checkPoint);
        // 끝나는 시점에 맞춰 Scanner객체 종료
        scanner.close();
    }

    // 메인메뉴 UI 출력
    private void printMainMenu() {
        System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
        IntStream
                .range(0, categories.size())
                .forEach(
                        i ->
                                System.out.println((i+1) + ". " + categories.get(i).getType().getCategoryName())
                );
        // 장바구니나 주문내역이 있을 때만 메뉴 노출
        if(!currentCustomer.getCartItems().isEmpty() || !currentCustomer.getPaidItems().isEmpty()) {
            System.out.println("[ 주문 관리 ]");
            System.out.printf("%d. %-12s | %s%n", 4, "장바구니 확인", "장바구니를 확인후 주문합니다.");
            System.out.printf("%d. %-13s | %s%n", 5, "주문 취소", "진행중인 주문을 취소합니다.");
        }
        System.out.printf("%d.%-7s | %s%n", 0, " 종료", "프로그램 종료");
        System.out.print("입력: ");
    }

    // 카테고리 선택 시 해당 카테고리의 상품 목록 조회
    private void handleCategorySelection(int num) {
        if(categories.get(num).getProducts().isEmpty()) {
            System.out.printf("%s 카테고리로 등록된 상품이 없습니다.%n%n", categories.get(num).getType().getCategoryName());
        } else {
            showList(categories.get(num));
        }
    }

    // 장바구니 확인 및 주문 확정
    private void handleOrderProcess() {
        //장바구니에 담긴 제품 조회
        printCartItemsInfo();
        for(int i = 1; i < 3; i++) {
            if(i == 1) {
                System.out.print(i + ". 주문 확정\t\t\t");
            } else {
                System.out.println(i + ". 메인으로 돌아가기");
            }
        }
        System.out.print("입력: ");
        do {
            int selectNum = scanner.nextInt();
            switch(selectNum) {
                case 1:
                    System.out.printf("주문이 완료되었습니다! 총 금액: %,d원%n", currentCustomer.getTotalPrice());

                    currentCustomer.getCartItems().forEach(ci -> {
                        ci.getProduct().reduceLeftCnt(ci.getAmount());
                        System.out.printf("%s 재고가 %d개 -> %d개로 업데이트되었습니다.%n%n", ci.getProduct().getProdName(), ci.getProduct().getLeftCnt() + ci.getAmount(), ci.getProduct().getLeftCnt());
                    });
                    currentCustomer.completeOrder();
                    checkPoint = true;
                    break;
                case 2:
                    System.out.println("메인으로 돌아갑니다.\n");
                    checkPoint = true;
                    break;
                default:
                    System.out.print("잘못된 입력입니다. 다시 입력: ");
                    scanner.nextLine();
            }
        } while(!checkPoint);
        checkPoint = false;
    }

    // 주문 취소
    private void handleCancelProcess() {
        //주문이 들어간 상품들 조회 메소드 구현
        printPaidItemsInfo();
        System.out.println("\n0. 뒤로가기");
        System.out.print("취소할 상품의 번호를 입력하세요: ");

        try {
            int cancelNum = scanner.nextInt();

            if (cancelNum == 0) {
                System.out.println("메인으로 돌아갑니다.\n");
            } else if (cancelNum > 0 && cancelNum <= currentCustomer.getPaidItems().size()) {
                cancelPaidItem(cancelNum - 1);
            } else {
                System.out.println("잘못된 범위의 입력입니다.\n");
            }
        } catch (InputMismatchException e) {
            System.out.println("숫자만 입력 가능합니다.\n");
            scanner.nextLine();
        }
    }

    // 선택한 카테고리의 상품 리스트를 출력하고 상품 선택 유도
    public void showList(Category category) {

        // 1. 카테고리 이름 및 소속 상품 목록 출력
        System.out.printf("%n[ %s 카테고리 ]%n", category.getType().getCategoryName());
        IntStream
                .range(0, category.getProducts().size())
                .forEach(
                        i ->
                                System.out.println((i+1) + ". " + category.getProducts().get(i).getPrintListInfo())
                );
        System.out.printf("%d. %s%n", 0, "뒤로가기");
        System.out.print("입력: ");

        // 2. 유효한 입력이 들어올 때까지 반복 (상품 선택 루프)
        do {
            try {
                checkNum = scanner.nextInt();
                if(checkNum == 0) {     // 뒤로가기
                    System.out.println("다시 메뉴로 돌아갑니다.\n");
                    break;
                }
                else if(0 < checkNum && checkNum <= category.getProducts().size()) {
                    // 상품 선택 성공 시 상세 정보 출력
                    Product selectedProduct = category.getProducts().get(checkNum - 1);
                    System.out.println("선택한 상품: " + selectedProduct.getPrintProductInfo() + "\n");

                    // 장바구니 추가 여부 확인
                    if(askToAddCart() == 1) {
                        addToCart(selectedProduct);
                    } else {
                        System.out.println("다시 메뉴로 돌아갑니다.\n");
                    }
                    break;
                } else {
                    System.out.print("잘못된 범위의 접근입니다. 다시 입력: ");
                }
            } catch(InputMismatchException e) {
                System.out.print("숫자만 입력 가능합니다. 다시 입력: ");
                scanner.nextLine();
            }
        } while(true);

    }

    // 장바구니 추가여부 확인
    public int askToAddCart() {
        int choice = 0;
        System.out.println("해당 상품을 장바구니에 추가하시겠습니까? ");

        // 선택지 제공 (1. 추가, 2. 취소)
        for(int i = 1; i < 3; i++) {
            if(i == 1) {
                System.out.print(i + ". 추가\t\t\t");
            } else {
                System.out.println(i + ". 취소");
            }
        }
        System.out.print("입력: ");

        // 유효한 번호(1, 2)를 입력할 때까지 반복
        while(true) {
            choice = scanner.nextInt();
            if(choice == 1) {
                break;
            } else if(choice == 2) {
                System.out.println("취소 되었습니다.");
                break;
            } else {
                System.out.print("잘못된 입력입니다. 다시 입력: ");
                scanner.nextLine();
            }
        }
        return choice;
    }

    // 사용자에게 장바구니 추가 여부를 묻는 선택 메뉴를 출력
    public void addToCart(Product product) {
        System.out.print("몇개의 상품을 추가하시겠습니까?\n입력: ");
        while(true) {
            int amount = scanner.nextInt();

            // 1. 기존 장바구니에 해당 상품이 있는지 먼저 찾습니다.
            Cart existingCart = currentCustomer.getCartItems().stream()
                    .filter(c -> c.getProduct().equals(product))
                    .findFirst()
                    .orElse(null);

            // 2. (이미 담긴 수량 + 새로 입력한 수량) 계산
            int totalRequestedAmount = (existingCart != null ? existingCart.getAmount() : 0) + amount;

            // 3. 합산 수량이 재고를 넘는지 체크
            if (totalRequestedAmount > product.getLeftCnt()) {
                System.out.printf("죄송합니다. 재고가 부족합니다. (현재 재고: %d개 / 장바구니 포함 총 요청: %d개)%n", product.getLeftCnt(), totalRequestedAmount);
                System.out.print("다시 입력: ");
            } else if (amount <= 0) {
                System.out.print("1개 이상의 수량을 입력해주세요. 다시 입력: ");
            } else {
                if(existingCart != null) {
                    currentCustomer.editCartItems(product, amount);
                } else {
                    currentCustomer.addCartItems(new Cart(product, amount));
                }
                scanner.nextLine();
                break;
            }

        }
        System.out.printf("%s가 장바구니에 추가되었습니다.%n", product.getProdName());
    }

    // 현재 장바구니에 담긴 목록과 총 결제 예정 금액을 출력
    public void printCartItemsInfo() {
        System.out.println("아래와 같이 주문 하시겠습니까?\n");
        System.out.println("[ 장바구니 내역 ]");
        if(currentCustomer.getCartItems().isEmpty()) {
            System.out.println("현재 담아놓은 상품이 없습니다.");
        } else {
            currentCustomer.getCartItems().forEach(ci -> System.out.printf("%-10s | %-,10d원 | %-15s | 수량: %d개%n", ci.getProduct().getProdName(), ci.getProduct().getPrice(), ci.getProduct().getProdContent(), ci.getAmount()));
            System.out.println("\n[ 총 주문 금액 ]");
            int totalPrice = currentCustomer.getTotalPrice();
            System.out.printf("%-,10d원%n%n", totalPrice);
        }
    }

    // 결제가 완료된 주문 내역을 인덱스 번호와 함께 출력
    public void printPaidItemsInfo() {
        System.out.println("[ 주문 내역 ]");

        IntStream
                .range(0, currentCustomer.getPaidItems().size())
                .forEach(
                        i -> {
                            Cart temp = currentCustomer.getPaidItems().get(i);
                            System.out.printf("%d. %-10s | %d개 | %-,10d원%n", (i+1), temp.getProduct().getProdName(), temp.getAmount(), temp.getPrice());
                        }
                );
    }

    // 특정 주문 내역을 취소하고 해당 상품의 재고를 원복
    public void cancelPaidItem(int num) {
        if(num >= 0 && num < currentCustomer.getPaidItems().size()) {
            Cart itemToCancel = currentCustomer.getPaidItems().get(num);

            // 1. 해당 상품 객체의 재고 복구 (Product 내부 로직 호출)
            itemToCancel.getProduct().returnLeftCnt(itemToCancel.getAmount());

            // 2. 결제 내역 리스트에서 해당 항목 삭제
            currentCustomer.getPaidItems().remove(num);

            System.out.println("해당 주문이 정상적으로 취소 및 환불 처리되었습니다.");
        } else {
            System.out.println("잘못된 번호입니다. 취소에 실패했습니다.");
        }
    }
}
