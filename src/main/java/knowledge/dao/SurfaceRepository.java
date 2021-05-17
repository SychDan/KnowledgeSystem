package knowledge.dao;

import knowledge.model.Surface;
import knowledge.model.User;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SurfaceRepository extends CrudRepository<Surface, Integer> {
    List<Surface> findAllByUsersOrderByName(User user);

    List<Surface> findAllByIdNotAndAdminIdOrderByName(int id, int adminId);

    List<Surface> findAllByIdNotAndUsersOrderByName(int id, User user);

    List<Surface> findAllByAccessTypeAndNameLikeOrderByName(String accessType, String nameLike);

    List<Surface> findAllByAccessTypeOrderByName(String accessType);
}