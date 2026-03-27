import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class CommerceSystem {

    int checkNum = 0;
    boolean checkPoint = false;
    List<Category> categories = null;

    Scanner scanner = new Scanner(System.in);

    // 생성자
    public CommerceSystem(List<Category> categories) {
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
                                System.out.println((i+1) + ". " + categories.get(i).getCategoryName())
                    );
            System.out.printf("%d.%-7s | %s%n", 0, " 종료", "프로그램 종료");

            try {
                checkNum = scanner.nextInt();
                if(checkNum == 0) {
                    System.out.print("커머스 플랫폼을 종료합니다.");
                    checkPoint = true;
                } else if(0 < checkNum && checkNum <= categories.size()) {
                    if(categories.get(checkNum-1).getProducts().isEmpty()) {
                        System.out.printf("%s 카테고리로 등록된 상품이 없습니다.\n\n", categories.get(checkNum-1).getCategoryName());
                    } else {
                        showList(categories.get(checkNum-1));
                    }
                } else {
                    System.out.print("잘못된 범위의 접근입니다. 다시 입력: ");
                }
            } catch(InputMismatchException e) {
                System.out.println("숫자만 입력 가능합니다.\n");
                scanner.nextLine();
            }
        } while(!checkPoint);
        scanner.close();
    }

    public void showList(Category category) {

        //출력
        System.out.printf("\n[ %s 카테고리 ]\n", category.getCategoryName());
        IntStream
                .range(0, category.getProducts().size())
                .forEach(
                        i ->
                                System.out.println((i+1) + ". " + category.getProducts().get(i).getPrintListInfo())
                );
        System.out.printf("%d.%-13s\n", 0, "뒤로가기");

        do {
            try {
                checkNum = scanner.nextInt();
                if(checkNum == 0) {
                    break;
                }
                else if(0 < checkNum && checkNum <= category.getProducts().size()) {
                    System.out.println("선택한 상품: " + category.getProducts().get(checkNum-1).getPrintProductInfo() + "\n");
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

}
