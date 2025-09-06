package bysu.splearn.application.provided;

import bysu.splearn.domain.Member;

public interface MemberFinder {
    Member find(Long memberId);
}
