package ch.supsi.webapp.web.service;

import ch.supsi.webapp.web.model.Role;
import ch.supsi.webapp.web.model.User;
import ch.supsi.webapp.web.repository.RoleRepository;
import ch.supsi.webapp.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.encoder = new BCryptPasswordEncoder();
    }

    @PostConstruct
    public void init() {
        if (userRepository.findAll().isEmpty()) {
            Role adminRole = roleRepository.findById("ROLE_ADMIN")
                    .orElseGet(() -> roleRepository.save( new Role( "ROLE_ADMIN" )));
            User admin = new User("admin", encoder.encode("admin"),
                    adminRole, "Admin", "Admin");
            userRepository.save(admin);
        }
    }

    public List<User> getAuthors(){
        return userRepository.findAll();
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseGet(() -> new User("Anonymous"));
    }

    public User saveUser(User user) {
        user.setPassword( encoder.encode( user.getPassword() ));
        user.setRole( roleRepository
                .findById( "ROLE_USER" )
                .orElseGet(() -> roleRepository.save( new Role( "ROLE_USER" ))));
        return userRepository.save(user);
    }

    public void deleteAll() {
        if (!userRepository.findAll().isEmpty())
            userRepository.deleteAll();
        if (!roleRepository.findAll().isEmpty())
            roleRepository.deleteAll();
    }
}