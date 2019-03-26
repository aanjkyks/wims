package bootcamp.wims.auth.service;

import bootcamp.wims.auth.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
