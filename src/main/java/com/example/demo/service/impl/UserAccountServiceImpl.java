@Override
public UserAccount findByEmail(String email) {
    return userAccountRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
}

@Override
public List<UserAccount> getAllUsers() {
    return userAccountRepository.findAll();
}

@Override
public UserAccount getUser(Long id) {
    return userAccountRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
}
