package bysu.splearn.domain;

public record Email(String address) {
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    public Email {
        if (!address.matches(EMAIL_PATTERN)) {
            throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다.");
        }
    }
}
