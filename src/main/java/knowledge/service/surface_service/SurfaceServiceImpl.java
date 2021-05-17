package knowledge.service.surface_service;

import knowledge.dao.SurfaceRepository;
import knowledge.model.Surface;
import knowledge.model.User;
import knowledge.service.user_service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Profile("springdatajpa")
public class SurfaceServiceImpl implements SurfaceService {

    @Autowired
    private SurfaceRepository surfaceRepository;

    @Autowired
    private UserService userService;


    @Override
    public List<Surface> listAll() {
       List<Surface> surfaceList = new ArrayList<>();
       surfaceRepository.findAll().forEach(surfaceList :: add);
       surfaceList.sort(Comparator.comparing(Surface::getName));
       return surfaceList;
    }

    @Override
    public Surface getById(Integer id) {
        return surfaceRepository.findById(id).get();
    }

    @Override
    public void saveOrUpdate(Surface domainObject) {
        if (domainObject.getUsers().isEmpty()) {
            domainObject.addUser(userService.getCurrentUser());
        }
        surfaceRepository.save(domainObject);
    }

    @Override
    public void delete(Integer id) {
        surfaceRepository.deleteById(id);
    }

    @Override
    public void delete(Surface domainObject) {
        surfaceRepository.delete(domainObject);
    }

    @Override
    public List<Surface> findAllByCurrentUser() {
        return surfaceRepository.findAllByUsersOrderByName(userService.getCurrentUser());
    }

    @Override
    public List<Surface> getSurfacesWithoutSurface(Surface surface) {
        return surfaceRepository.findAllByIdNotAndAdminIdOrderByName(surface.getId(), userService.getCurrentUser().getId());
    }

    @Override
    public Surface getNewSurface() {
        Surface surface = new Surface();
        surface.setAdminId(userService.getCurrentUser().getId());
        return surface;
    }

    @Override
    public void addUser(Surface surface, User user) {
        surface.addUser(user);
        surfaceRepository.save(surface);
    }

    @Override
    public List<Surface> findAllPublicSurfacesByNameLike(String nameLike) {
        nameLike += "%";
      return surfaceRepository.findAllByAccessTypeAndNameLikeOrderByName("public", nameLike);
    }

    @Override
    public List<Surface> findAllPublicSurfacesByPopularity() {
        List<Surface> surfaceList = surfaceRepository.findAllByAccessTypeOrderByName("public");
        surfaceList.sort((o1, o2) -> -Integer.compare(o1.getUsers().size(), o2.getUsers().size()));
       return (surfaceList.size() >= 10) ? surfaceList.subList(0, 9) : surfaceList;
    }
}