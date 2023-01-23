package hello.demo.controller;

import hello.demo.domain.Member;
import hello.demo.repository.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    /*
    스프링 컨테이너가 @Controller를 보고 이 클래스 객체를 만들어서 스프링이 갖고 있다.
    이걸 스프링 빈이 관리된다고 표현한다.

    이제 MemberService를 가져가 써야하는데, 새로 생성해주는 방식은 만약 여러 컨트롤러가 MemberService를 사용할 때마다 새로 생성해야한다.
    private final MemberService memberService = new MemberService();
    이렇게 매번 새로 생성하지 말고 스프링 컨테이너에 등록되어 있는 스프링 빈을 받아 써야된다.
     */

    private final MemberService memberService;

    //생성자에 @Autowired가 있으면 스프링 연관된 객체를 스프링 컨테이너에 찾아서 넣어준다. 이렇게 객체 의존관계를
    //외부에서 넣어주는 것을 DI(Dependency Injection) 의존성 주입이라 한다.
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMember();
        model.addAttribute("members", members);
        return "members/memberlist";
    }


}
