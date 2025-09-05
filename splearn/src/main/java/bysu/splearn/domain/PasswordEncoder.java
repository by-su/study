package bysu.splearn.domain;

public interface PasswordEncoder {
    String encode(CharSequence password);
    boolean matches(CharSequence password, String passwordHash);
}