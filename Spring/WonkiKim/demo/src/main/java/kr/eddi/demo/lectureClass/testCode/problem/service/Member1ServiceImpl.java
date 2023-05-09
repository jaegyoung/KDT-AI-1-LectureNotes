package kr.eddi.demo.lectureClass.testCode.problem.service;

import kr.eddi.demo.lectureClass.testCode.problem.entity.Member1;
import kr.eddi.demo.lectureClass.testCode.problem.repository.Member1Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class Member1ServiceImpl implements Member1Service{
    final private Member1Repository memberRepository;
    @Override
    public Member1 register(Member1 member) {
        final Optional<Member1> maybeMember = memberRepository.findByAccountId(member.getAccountId());
        if(maybeMember.isPresent()) {
            log.info("동일한 계정명의 회원이 존재합니다.");
        }
        return memberRepository.save(member);
    }
}
