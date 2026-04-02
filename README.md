# CommerceProject
# 🛒 실시간 커머스 플랫폼 (Commerce System)

> **Java Stream API와 OOP를 활용한 콘솔 기반 커머스 시스템**
> 사용자가 상품을 조회하고, 장바구니 관리, 실시간 재고 검증, 주문 및 취소까지 가능한 풀 프로세스를 제공합니다.

---

## 🚀 주요 기능 (Key Features)

### 1. 상품 및 카테고리 관리
* **Enum 기반 카테고리:** `CategoryType`을 활용하여 전자제품, 의류, 식품 등 체계적인 상품 분류.
* **실시간 재고 반영:** 상품 목록 및 상세 조회 시 현재 남은 재고(`leftCnt`)를 실시간으로 확인 가능.

### 2. 스마트 장바구니 (`Cart`)
* **중복 상품 자동 합산:** 장바구니에 동일 상품 추가 시, 새로운 객체를 생성하지 않고 기존 수량에 합산하는 최적화 로직 적용.
* **실시간 재고 검증:** (기존 담긴 수량 + 새로 추가할 수량)이 실제 재고를 초과할 경우 추가를 거절하고 재입력을 유도.

### 3. 주문 확정 및 재고 시스템
* **재고 차감 로직:** 장바구니에 담을 때는 재고를 '선점'하고, 주문 확정 시점에 실제 상품 재고를 최종 차감.
* **주문 내역 관리:** 결제 완료 시 장바구니(`cartItems`) 데이터를 주문 내역(`paidItems`)으로 이동하여 이력 관리.

### 4. 주문 취소 및 환불
* **자동 재고 복구:** 주문 내역에서 상품 취소 시, 해당 상품의 수량만큼 재고(`leftCnt`)를 즉시 복구하는 환불 로직 구현.
* **동적 메뉴 UI:** 장바구니나 주문 내역이 있을 때만 관련 메뉴(4. 장바구니, 5. 주문 취소)가 활성화되는 사용자 친화적 인터페이스.

---

## 🛠 기술 스택 (Tech Stack)

- **Language:** Java 17
- **Concepts:**
    - **Stream API:** `filter`, `anyMatch`, `findFirst`, `IntStream` 등을 활용한 선언적 데이터 처리.
    - **OOP:** 객체 간의 역할 분담(Customer, Product, Cart, Category) 및 캡슐화.
    - **Enum:** 카테고리 및 고객 등급 관리를 통한 데이터 타입 안정성 확보.
    - **Exception Handling:** `InputMismatchException` 및 사용자 정의 입력 범위 검증.

---

## 🏗 프로젝트 구조 (Architecture)

| 클래스명 | 역할 설명 |
| :--- | :--- |
| **Main** | 프로그램 진입점 및 초기 카테고리/상품 데이터 생성. |
| **CommerceSystem** | 메인 루프, 사용자 입력 처리 및 전반적인 시스템 흐름 제어. |
| **Customer** | 장바구니 및 주문 내역 관리, 총 금액 계산 및 등급 정보 보유. |
| **Category** | 상품 그룹화 및 Enum을 통한 유형 정의. |
| **Product** | 상품 상세 정보 및 재고 차감/복구 핵심 로직 처리. |
| **Cart** | 상품-수량-금액 간의 관계를 정의하는 개별 항목 객체. |

---

## 💡 핵심 코드 (Code Highlights)

### 🔹 장바구니 중복 체크 및 수량 합산
```java
// 기존 장바구니에 해당 상품이 있는지 탐색
Cart existingCart = currentCustomer.getCartItems().stream()
                .filter(c -> c.getProduct().equals(product))
                .findFirst()
                .orElse(null);

if (existingCart != null) {
        currentCustomer.editCartItems(product, amount); // 기존 수량 업데이트
} else {
        currentCustomer.addCartItems(new Cart(currentCustomer, product, amount)); // 신규 추가
}
```
🔹 주문 취소 시 재고 복구 로직
```Java
public void cancelPaidItem(int num) {
  Cart itemToCancel = paidItems.get(num);
  // 1. 상품 객체 내부의 재고를 직접 복구 (자율적 객체 설계)
  itemToCancel.getProduct().returnLeftCnt(itemToCancel.getAmount());
  // 2. 결제 내역 리스트에서 제거
  paidItems.remove(num);
}
```
---
### 📝 실행 화면 (Example)
```Plaintext
[ 실시간 커머스 플랫폼 메인 ]
1. 전자제품
2. 의류
3. 식품
[ 주문 관리 ]
4. 장바구니 확인 | 장바구니를 확인 후 주문합니다.
5. 주문 취소    | 진행중인 주문을 취소합니다.
0. 종료
입력: _
```
---
## 🧐 트러블슈팅 (Troubleshooting)

1. 객체지향 원칙(Tell, Don't Ask) 위반
   - **문제**: 초기 설계 시 CommerceSystem에서 상품의 재고를 직접 수정하여 캡슐화가 깨짐.

   - **해결**: Product 클래스 내부에 isAvailable(), reduceLeftCnt(), returnLeftCnt() 메서드를 정의하여, 상품 객체가 스스로의 상태를 관리하도록 수정.

2. 사용자 인증 로직의 논리적 모순
   - **문제**: Customer 객체 내부에 전체 고객 리스트를 두어 자가 검증을 수행하는 어색한 구조 발견.

   - **해결**: 고객 명단 관리를 Main 및 시스템 레벨로 격상하고, 로그인 시도자의 정보와 시스템 명단을 대조하는 실제 서비스 방식의 인증 로직으로 개편.

3. 스캐너 입력 예외로 인한 무한 루프
   - **문제**: 숫자 입력란에 문자를 입력할 경우 InputMismatchException이 발생하며 무한 루프에 빠짐.

   - **해결**: catch 블록에서 scanner.nextLine()을 호출하여 입력 버퍼를 강제로 비워줌으로써 시스템 안정성 확보.