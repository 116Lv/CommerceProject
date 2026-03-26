import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class CommerceSystem {

    // 리스트 선언
    private List<Product> products = null;

    // 생성자(리스트를 곁들인..)
    public CommerceSystem(List<Product> products) {
        this.products = products;
    }

    // 시작함수
    public void start() {
        Scanner scanner = new Scanner(System.in);
        //출력
        System.out.println("[ 실시간 커머스 플랫폼 - 전자제품 ]");
        IntStream
            .range(0, products.size())
            .forEach(
                i ->
                    System.out.println((i+1) + ". " + products.get(i).getPrintInfo())
                );
        System.out.printf("%d.%-13s | %s%n", 0, " 종료", "프로그램 종료");

        int checkNum = 0;
        do {
            checkNum = scanner.nextInt();
        } while(checkNum != 0);

        scanner.close();
    }


}
