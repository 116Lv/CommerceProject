import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class CommerceSystem {
    // 변수 및 리스트 선언
    private int checkNum = 0;
    private boolean checkPoint = false;
    private List<Category> categories = null;
    private Customer currentCustomer = null;

    //Scanner 객체 생성
    Scanner scanner = new Scanner(System.in);

    // 생성자
    public CommerceSystem(Customer customer, List<Category> categories) {
        this.currentCustomer = customer;
        this.categories = categories;
    }

    // 시작함수
    public void start() {
        do {
            System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
            IntStream
                    .range(0, categories.size())
                    .forEach(
                            i ->
                                System.out.println((i+1) + ". " + categories.get(i).getType().getCategoryName())
                    );
            if(!currentCustomer.getCartItems().isEmpty() || !currentCustomer.getPaidItems().isEmpty()) {
                System.out.println("[ 주문 관리 ]");
                System.out.printf("%d. %-12s | %s%n", 4, "장바구니 확인", "장바구니를 확인후 주문합니다.");
                System.out.printf("%d. %-13s | %s%n", 5, "주문 취소", "진행중인 주문을 취소합니다.");
            }
            System.out.printf("%d.%-7s | %s%n", 0, " 종료", "프로그램 종료");
            System.out.print("입력: ");

            try {
                checkNum = scanner.nextInt();
                if(checkNum == 0) {
                    System.out.print("커머스 플랫폼을 종료합니다.");
                    checkPoint = true;
                } else if(0 < checkNum && checkNum <= categories.size()) {
                    if(categories.get(checkNum-1).getProducts().isEmpty()) {
                        System.out.printf("%s 카테고리로 등록된 상품이 없습니다.%n%n", categories.get(checkNum-1).getType().getCategoryName());
                    } else {
                        showList(categories.get(checkNum-1));
                    }
                } else if(checkNum == 4 && (!currentCustomer.getCartItems().isEmpty() || !currentCustomer.getPaidItems().isEmpty())) {
                    //장바구니에 담긴 제품 조회
                    currentCustomer.printCartItemsInfo();
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
                                    ci.getProduct().setLeftCnt(ci.getAmount());
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

                } else if(checkNum == 5 && (!currentCustomer.getCartItems().isEmpty() || !currentCustomer.getPaidItems().isEmpty())) {
                    //주문이 들어간 상품들 조회 메소드 구현
                    currentCustomer.printPaidItemsInfo();
                    System.out.println("\n0. 뒤로가기");
                    System.out.print("취소할 상품의 번호를 입력하세요: ");

                    try {
                        int cancelNum = scanner.nextInt();

                        if (cancelNum == 0) {
                            System.out.println("메인으로 돌아갑니다.\n");
                        } else if (cancelNum > 0 && cancelNum <= currentCustomer.getPaidItems().size()) {
                            currentCustomer.cancelPaidItem(cancelNum - 1);
                        } else {
                            System.out.println("잘못된 범위의 입력입니다.\n");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("숫자만 입력 가능합니다.\n");
                        scanner.nextLine();
                    }

                } else {
                    System.out.println("잘못된 범위의 입력입니다.\n");
                }
            } catch(InputMismatchException e) {
                System.out.println("숫자만 입력 가능합니다.\n");
                scanner.nextLine();
            }
        } while(!checkPoint);
        //끝나는 시점에 맞춰 Scanner객체 종료
        scanner.close();
    }

    // 원하는 카테고리의 products 리스트 조회 및 검색
    public void showList(Category category) {

        //출력
        System.out.printf("%n[ %s 카테고리 ]%n", category.getType().getCategoryName());
        IntStream
                .range(0, category.getProducts().size())
                .forEach(
                        i ->
                                System.out.println((i+1) + ". " + category.getProducts().get(i).getPrintListInfo())
                );
        System.out.printf("%d. %s%n", 0, "뒤로가기");
        System.out.print("입력: ");

        do {
            try {
                checkNum = scanner.nextInt();
                if(checkNum == 0) {
                    System.out.println("다시 메뉴로 돌아갑니다.\n");
                    break;
                }
                else if(0 < checkNum && checkNum <= category.getProducts().size()) {
                    System.out.println("선택한 상품: " + category.getProducts().get(checkNum - 1).getPrintProductInfo() + "\n");
                    if(askToAddCart(checkNum - 1) == 1) {
                        addToCart(category.getProducts().get(checkNum - 1));
                        System.out.printf("%s가 장바구니에 추가되었습니다.%n", category.getProducts().get(checkNum - 1).getProdName());
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
    public int askToAddCart(int num) {
        int choice = 0;
        System.out.println("해당 상품을 장바구니에 추가하시겠습니까? ");
        for(int i = 1; i < 3; i++) {
            if(i == 1) {
                System.out.print(i + ". 추가\t\t\t");
            } else {
                System.out.println(i + ". 취소");
            }
        }
        System.out.print("입력: ");
        while(true) {
            choice = scanner.nextInt();
            if(choice == 1) {
                break;
            } else if(choice == 2) {
                System.out.println("취소 되었습니다.");
                break;
            } else {
                System.out.print("잘못된 입력입니다. 다시 입력: ");
            }
        }
        return choice;
    }

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
                // 재고 통과!
                if(existingCart != null) {
                    currentCustomer.editCartItems(product, amount);
                } else {
                    currentCustomer.addCartItems(new Cart(currentCustomer, product, amount));
                }
                scanner.nextLine();
                break;
            }

        }

    }

}
