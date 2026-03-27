import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        //필수 생성요소들
        Category electronic = new Category("전자제품");
        electronic.addProduct(new Product("Galaxy S25", 1200000, "최신 안드로이드 스마트폰"));
        electronic.addProduct(new Product("iPhone 16", 1350000, "Apple의 최신 스마트폰"));
        electronic.addProduct(new Product("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북"));
        electronic.addProduct(new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰"));

        Category clothes = new Category("의류");

        Category food = new Category("식품");

        List<Category> categories = new ArrayList<>();
        categories.add(electronic);
        categories.add(clothes);
        categories.add(food);

        // 관리시스템 생성 후 시작
        CommerceSystem cs = new CommerceSystem(categories);

        cs.start();

    }
}
