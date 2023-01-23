package hello.demo.repository.service;

import hello.demo.domain.Member;
import hello.demo.repository.MemberRepository;
import hello.demo.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//@Service //추가
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    //@Autowired //추가
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = new MemoryMemberRepository();
    }

    /**
     * 회원가입
     * @param member
     * @return
     */
    public Long join(Member member){

        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) { //같은 이름이 있는 회원X

//        Optional<Member> result = memberRepository.findByName(member.getName());
//        result.ifPresent(m -> {    //바로 꺼내지보단 만약 존재한다면 꺼내고 아니면 에러를 리턴한다. 바로 꺼내려면 .get()하면 된다.
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        });

        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    /**
     * 전체 회원 조회
     * @return
     */
    public List<Member> findMember() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

}
