import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {

        int checkNum;

        List<Product> products = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        //Product 생성
        products.add(new Product("Galaxy S25", 1200000, "최신 안드로이드 스마트폰"));
        products.add(new Product("iPhone 16", 1350000, "Apple의 최신 스마트폰"));
        products.add(new Product("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북"));
        products.add(new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰"));


        //출력
        System.out.println("[ 실시간 커머스 플랫폼 - 전자제품 ]");
        IntStream
            .range(0, products.size())
            .forEach(
                i ->
//                    System.out.printf("%d. %-13s | %,10d원 | %s\n", (i+1), products.get(i).getProdName(), products.get(i).getPrice(), products.get(i).getProdContent())
                    System.out.println((i+1) + ". " + products.get(i).getPrintInfo())
            );
        System.out.printf("%d.%-13s | %s%n", 0, " 종료", "프로그램 종료");

        do {
            checkNum = scanner.nextInt();
        } while(checkNum != 0);

    }
}
