package Method;

import java.util.*;
import Resources.Category;
import Resources.Product;
import Resources.Customer;

public class Main {

    public static void main(String[] args) {

        // 1. 카테고리 생성 및 각 카테고리별 상품 데이터 초기화
        // 전자제품 카테고리 생성 및 상품 추가
        Category electronic = new Category(Category.CategoryType.ELEC);
        electronic.addProduct(new Product("Galaxy S25", 1200000, "최신 안드로이드 스마트폰"));
        electronic.addProduct(new Product("iPhone 16", 1350000, "Apple의 최신 스마트폰"));
        electronic.addProduct(new Product("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북"));
        electronic.addProduct(new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰"));

        // 의류 및 식품 카테고리 생성
        Category clothes = new Category(Category.CategoryType.CLOTHES);
        Category food = new Category(Category.CategoryType.FOOD);

        // 2. 시스템에서 관리할 전체 카테고리 리스트 구성
        List<Category> categories = new ArrayList<>();
        categories.add(electronic);
        categories.add(clothes);
        categories.add(food);

        // 3. 접속 고객 객체 생성
        List<Customer> customerList = new ArrayList<>();    // 시스템 등록 고객 리스트 (검증용)
        customerList.add(new Customer("홍길동", "hong@gmail.com"));    // 고객1
        customerList.add(new Customer("김철수", "chul@naver.com"));    // 고객2

        // 로그인 유저
        Customer loginUser = new Customer("홍길동", "hong@gmail.com");

        // 4. 검증 (명단에 로그인 유저가 있는지 확인) 및 커머스 시스템 시작
        boolean isAuthenticated = customerList.stream().anyMatch(c -> c.getCustomerName().equals(loginUser.getCustomerName())&& c.getEmail().equals(loginUser.getEmail()));

        if (isAuthenticated) {
            System.out.println(loginUser.getCustomerName() + "님, 로그인 성공!");
            CommerceSystem cs = new CommerceSystem(loginUser, categories);
            cs.start();
        } else {
            System.out.println("등록되지 않은 사용자 정보입니다. 프로그램을 종료합니다.");
        }

    }
}
