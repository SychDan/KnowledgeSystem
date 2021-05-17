package knowledge.dao;

import knowledge.model.Page;
import knowledge.model.Surface;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface PageRepository extends CrudRepository<Page, Integer> {

    List<Page> findAllBySurfaceOrderByName(Surface surface);

    List<Page> findAllByAncestorId(int ancestorId);

    List<Page> findAllBySurfaceAndLevelOrderByName(Surface surface, int level);

    List<Page> findAllBySurfaceAndNameLikeOrderByName(Surface surface, String nameLike);

    List<Page> findAllBySurfaceAndContentLikeOrderByName(Surface surface, String contentLike);
}