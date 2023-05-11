package kr.eddi.demo.accountTest;

import kr.eddi.demo.lectureClass.testCode.account.controller.form.TestAccountLoginResponseForm;
import kr.eddi.demo.lectureClass.testCode.account.controller.form.TestAccountRequestForm;
import kr.eddi.demo.lectureClass.testCode.account.entity.TestAccount;

import kr.eddi.demo.lectureClass.testCode.account.repository.TestAccountRepository;
import kr.eddi.demo.lectureClass.testCode.account.service.TestAccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AccountTest {

    // @Autowired는
    @Autowired
    private TestAccountService testAccountService;

    @Autowired
    private TestAccountRepository testAccountRepository;

    @Test
    @DisplayName("사용자가 회원 가입 할 수 있음")
    void 사용자가_회원_가입한다 () {
        final String email = "test@test.com";
        final String password = "test";

        TestAccountRequestForm requestForm = new TestAccountRequestForm(email, password);

        /*
         TestAccountRequest request = requestForm.toTestAccountRequest();    // 확장성
         RequestForm에는 여러가지 형태가 들어올 수 있고
         그 내부에 있는 정보들은 여러가지 Domain 정보를 가질 가능성이 존재함
         */

        TestAccount account = testAccountRepository.save(requestForm.toTestAccount());
//         requestForm 객체의 toTestAccount() 메소드를 호출하여 TestAccount 객체를 생성하고,
//         이걸 testAccountRepository 의 save() 메소드로 jpa를 이용하여 저장한다.
//         저장된 TestAccount 객체를 account 변수에 할당한다.


        assertEquals(email, account.getEmail());
        assertEquals(password, account.getPassword());
        // account 객체의 getEmail() 메소드와 getPassword() 메소드가 반환하는 값이
        // 각각 email과 password 변수의 값과 같은지 확인한다.
    }

/*
         이 테스트에서 Service는 사실상 누락되어 있음.
         도대체 Service는 왜 필요할까 ?
         요청에 대한 처리를 하기 위해서라는 답변이 나왔음
         요청에 대한 처리를 Service에서 할 필요가 있는가 ?
         Repository에서 하면 Repository의 목적성에 맞지 않아서라는 답변이 나왔음
         결론적으로 Domain Service가 필요한 상황이 만들어짐

         OOP를 진행함에 있어 DDD는 Domain Entity로만 구현할 수 있다 생각할 수 있음.
         실제 대부분의 객체지향(OOP) 책에서 하는 이야기가
         그냥 class에 데이터 넣고 이 데이터를 제어할 수 있는 매서드를 만들라는 형식으로 설명함
         그러나 이렇게 되면 목적성이 불분명해지고 이건 왜 여기서하고
         저건 왜 또 여기 있고 하는 상황이 발생하면서 모호성이 발생하기 시작함.

         실제 Entity는 객체에 해당하며
         이 Entity에 모든 매서드를 집어넣는 순간 Entity 자체의 목적성이 불명확해짐
         이와 같은 이유로 DB에 저장하는 작업은 Repository로 분리합니다.
         반면 Service는 조금 더 별개로 생각해야 합니다.

         사실 이전에도 Java 수업 때 이야기 했던 내용입니다.
         전구에는 팔이 달려서 지를 키거나 끌 수 없습니다.
         외부의 요청에 의해 이것을 눌러서 키거나 끄게 되죠.
         이렇게 Entity 자신이 직접 수행하기 모호한 녀석들을 모두 Service로 빼고
         이렇게 뺀 Service들을 Domain Service라고 합니다.

         마찬가지로 한 번 읽어보라고 했던 study materials의 DDD에 있는 내용입니다.

         Domain Service의 분리는 궁극적으로 어떤 이점을 가져올까요 ?
         재사용성이 올라간다.
         Controller에서 여러 Service를 활용하는 경우가 발생함
         Service에서 여러 Repository를 활용하는 경우가 발생하기 시작함

         위와 같이 분리하지 않고 모든 책에 나온대로
         Entity에 데이터와 이를 제어하기 위한 매서들를 전부 때려박으면
         Entity object = new Entity();
         object.이게뭐야(응몰라.아무거나다해()); 와 같은 코드가 만들어집니다.
         넌뭐하는엔티티.나도몰라(object.이것도한다());
 */

    @Test // 테스트 메소드임을 나타낸다.
    @DisplayName("사용자가 회원 가입 할 수 있음") // 테스트의 이름을 정한다.
    void 사용자가_회원_가입한다_refactoring () {
        final String email = "test@test.com";
        final String password = "test";

        TestAccountRequestForm requestForm = new TestAccountRequestForm(email, password);
        // Vue에서 받아온 정보라고 생각하고 email, password을 가지고 TestAccountRequestForm 객체를 생성.
        TestAccount account = testAccountService.register(requestForm);
        // testAccountService.register(requestForm) 메소드를 호출하여 회원가입 진행. TestAccountServiceImpl

        assertEquals(email, account.getEmail());
        assertEquals(password, account.getPassword());
        // 호출된 email, password가 입력한 값과 일치하는지 확인
    }
    @Test
    @DisplayName("똑같은 사용자는 회원 가입 할 수 없음")
    void 이미_존재하는_이메일로_회원_가입시도 () {
        final String email = "test@test.com";
        final String password = "test";

        TestAccountRequestForm requestForm = new TestAccountRequestForm(email, password);
        TestAccount account = testAccountService.register(requestForm);
        // 위에와 같다. 여기까지는

        assertTrue(account == null);
        // account가 null인지 확인
        // null 이면 true 반환

    }

    @Test
    @DisplayName("잘못된 비밀번호 정보를 토대로 로그인")
    void 이메일만_맞게_입력한_상태에서_로그인 () {
        final String email = "test@test.com";
        final String password = "응틀렸어";

        TestAccountRequestForm requestForm = new TestAccountRequestForm(email, password);
        // TestAccountRequestForm 객체를 생성하여 email, password 정보를 전달

        TestAccountLoginResponseForm responseForm = testAccountService.login(requestForm);
        // testAccountService.login(requestForm) 이거로 로그인을 시도한다.

        assertTrue(responseForm.getUserToken() == null);
        // 비밀번호가 다를 경우  null 이면 true 반환
    }

    @Test
    @DisplayName("이메일을 잘못 입력한 상태로 로그인")
    void 이메일을_틀리게_입력한_상태에서_로그인 () {
        final String email = "gogo@gogo.com";
        final String password = "응틀렸어";

        TestAccountRequestForm requestForm = new TestAccountRequestForm(email, password);
        // TestAccountRequestForm 객체를 생성하여 email, password 정보를 전달

        TestAccountLoginResponseForm responseForm = testAccountService.login(requestForm);

        assertTrue(responseForm.getUserToken() == null);
        // 이메일이 다른 경우 null 이면 true 반환
    }

    @Test
    @DisplayName("올바른 입력한 정보를 토대로 로그인")
    void 올바른_정보로_로그인 () {
        // 윈도우의 경우 대소문자 구별이 잘 안되는 문제가 추가로 존재함(이것은 운영체제 문제)
        final String email = "test@test.com";
        final String password = "test";
        // 로그인을 좀 더 잘 관리하기 위해선 docker 기반의 redis에 token 관리가 필요합니다.
        // token 관리는 Docker redis 및 AWS 설정 이후에 작업해야하므로 잠시 보류합니다.

        TestAccountRequestForm requestForm = new TestAccountRequestForm(email, password);
        // TestAccountRequestForm 객체를 생성하여 email, password 정보를 전달

        TestAccountLoginResponseForm responseForm = testAccountService.login(requestForm);

        assertTrue(responseForm.getUserToken() != null);
        // 이메일이 맞고 비밀번호도 맞으면 userToken은 null이 아닐거임
        // null 아니면 true 반환


        // 로그아웃, 회원 탈퇴와 같은 사항들이 남아있음
        // 이 사항들은 역시나 로그인 되어 있는 token을 기반으로 진행되어야 합니다.
        // 그러므로 위 두 가지 사항은 현 시점에선 보류합니다.
    }
}