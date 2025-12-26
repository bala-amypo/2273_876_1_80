public interface UserAccountService {

    UserAccount register(RegisterRequest request);

    UserAccount findByEmail(String email);

    List<UserAccount> getAllUsers();

    UserAccount getUser(Long id);
}
