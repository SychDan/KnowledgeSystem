package knowledge.service.surface_service;

import knowledge.model.Surface;
import knowledge.model.User;
import knowledge.service.CRUDService;

import java.util.List;

public interface SurfaceService extends CRUDService<Surface> {

    List<Surface> findAllByCurrentUser();

    List<Surface> getSurfacesWithoutSurface(Surface surface);

    Surface getNewSurface();

    void addUser(Surface surface, User user);

    List<Surface> findAllPublicSurfacesByNameLike(String nameLike);

    List<Surface> findAllPublicSurfacesByPopularity();
}