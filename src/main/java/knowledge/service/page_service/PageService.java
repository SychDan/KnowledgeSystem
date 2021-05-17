package knowledge.service.page_service;

import knowledge.model.Page;
import knowledge.model.Surface;
import knowledge.service.CRUDService;

import java.util.List;

public interface PageService extends CRUDService<Page> {

    List<Page> findAllBySurface(Surface surface);

    List<Page> findAllByAncestorId(int ancestorId);

    void setSurface(Page page, Surface surface);

    List<Page> findAllBySurfaceAndLevel(Surface surface, int level);

    void participateToSurface(int surfaceId, int pageId);

    Page getNewChildFor(int id);

    void participateToPage(int pageId, int anotherPageId);

    List<Page> findAllPagesBySurfaceWithoutPage(Page page, Surface surface);

    List<Page> findAllBySurfaceAndNameLike(Surface surface, String nameLike);

    List<Page> findAllBySurfaceAndContentLike(Surface surface, String contentLike);
}