package services;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import repositories.UserRepository;
import static java.util.Collections.emptyList;


@Service
public class UserService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public void registerUser(User user){
        userRepository.save(user);
    }


    public User findUser(String username, String password) {
        return userRepository.findUserByUsernameAndPassword(username,password);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),emptyList());
    }

}
