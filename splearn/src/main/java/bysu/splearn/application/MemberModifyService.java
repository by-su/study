package bysu.splearn.application;

import bysu.splearn.application.provided.MemberFinder;
import bysu.splearn.application.provided.MemberRegister;
import bysu.splearn.application.required.EmailSender;
import bysu.splearn.application.required.MemberRepository;
import bysu.splearn.domain.Member;
import bysu.splearn.domain.MemberRegisterRequest;
import bysu.splearn.domain.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberModifyService implements MemberRegister {
    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;
    private final MemberFinder memberFinder;

    @Override
    public Member register(MemberRegisterRequest request) {
        Member member = Member.register(request, passwordEncoder);

        memberRepository.save(member);

        emailSender.send(member.getEmail(), "등록을 완료해주세요.", "아래 링크를 클릭해서 등룍을 완료해주세요.");

        return null;
    }

    @Override
    public Member activate(Long memberId) {
        var member = memberFinder.find(memberId);

        member.activate();

        return memberRepository.save(member);
    }

}
