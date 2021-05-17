package knowledge.dao;

import knowledge.model.Surface;
import knowledge.model.User;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findAllBySurfaces(Surface surface);

    User findByUsername(String username);

    List<User> findAllByIdNotAndEmailLikeOrderBySurname(int userId, String email);

}