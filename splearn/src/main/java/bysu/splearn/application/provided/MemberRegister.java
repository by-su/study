package bysu.splearn.application.provided;

import bysu.splearn.domain.Member;
import bysu.splearn.domain.MemberRegisterRequest;

/**
 * 회원의 등록과 관련된 기능을 제공한다.
 */
public interface MemberRegister {
    Member register(MemberRegisterRequest request);
    Member activate(Long memberId);
}