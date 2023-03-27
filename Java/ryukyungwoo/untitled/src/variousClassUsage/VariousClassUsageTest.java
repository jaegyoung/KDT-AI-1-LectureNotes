package variousClassUsage;

import test.test.TestStudent;

public class VariousClassUsageTest {
    public static void main(String[] args) {
        //  1. 그냥 클래스 사용하기
//        TestProduct product = new TestProduct("이거", "저거브랜드", 5000);
//
//        System.out.println("초기 상품: " + product);
//        // setter를 사용할 땐 정말 필요한지 묻고 사용하기!
//        product.changePrice(10000);
//        //  2. 클래스간 정보 전달하기
//
//        System.out.println("상품 정보 갱신: " + product);
        TestMember member = new TestMember(0,"test@test.com", "test")
        TestProduct product = new TestProduct("이거", "저거브랜드", 5000, member.getMemberId());

        System.out.println("초기 상품: " + product);


        System.out.println("상품 정보 갱신: " + product);

        product.findMember(member);
    }
}
