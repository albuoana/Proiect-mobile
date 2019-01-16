package repositories;
import model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User>{
    User findUserByUsernameAndPassword(String username,String password);
    User findByUsername(String username);
}