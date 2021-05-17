package knowledge.service.page_service;

import knowledge.dao.PageRepository;
import knowledge.dao.SurfaceRepository;
import knowledge.model.Page;
import knowledge.model.Surface;
import knowledge.service.tree_function.TreeFunc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile("springdatajpa")
public class PageServiceImpl implements PageService {

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private SurfaceRepository surfaceRepository;

    @Override
    public List<Page> listAll() {
        List<Page> pageList = new ArrayList<>();
        pageRepository.findAll().forEach(pageList::add);
        return pageList;
    }

    @Override
    public Page getById(Integer id) {
        return pageRepository.findById(id).get();
    }

    @Override
    public void saveOrUpdate(Page domainObject) {
        pageRepository.save(domainObject);
    }

    @Override
    public void delete(Integer id) {
        List<Page> children = findAllByAncestorId(id);
        if (!children.isEmpty()) {
            for (Page child : children) {
                delete(child.getId());
            }
        }
        pageRepository.deleteById(id);
    }

    @Override
    public void delete(Page domainObject) {
        pageRepository.delete(domainObject);
    }

    @Override
    public List<Page> findAllBySurface(Surface surface) {
        return TreeFunc.sortTree(pageRepository.findAllBySurfaceOrderByName(surface));
    }

    @Override
    public List<Page> findAllByAncestorId(int ancestorId) {
        return pageRepository.findAllByAncestorId(ancestorId);
    }

    @Override
    public void setSurface(Page page, Surface surface) {
        page.setSurface(surface);
        pageRepository.save(page);
    }

    @Override
    public List<Page> findAllBySurfaceAndLevel(Surface surface, int level) {
        return pageRepository.findAllBySurfaceAndLevelOrderByName(surface, level);
    }

    @Override
    public void participateToSurface(int surfaceId, int pageId) {
        Page page = pageRepository.findById(pageId).get();
        setSurface(page, surfaceRepository.findById(surfaceId).get());

        for (Page child : findAllByAncestorId(pageId)) {
            child.setLevel(page.getLevel() + 1);
            participateToSurface(surfaceId, child.getId());
        }
    }

    @Override
    public void participateToPage(int pageId, int anotherPageId) {
        Page page = pageRepository.findById(pageId).get();
        Page anotherPage = pageRepository.findById(anotherPageId).get();
        page.setAncestorId(anotherPageId);
        page.setLevel(anotherPage.getLevel() + 1);
        pageRepository.save(page);
        setLevelForChildren(page);
    }

    private void setLevelForChildren(Page page) {
        for (Page child : findAllByAncestorId(page.getId())) {
            child.setLevel(page.getLevel() + 1);
            pageRepository.save(child);
            setLevelForChildren(child);
        }
    }

    @Override
    public Page getNewChildFor(int id) {
        Page page = new Page();
        page.setLevel(getById(id).getLevel() + 1);
        page.setAncestorId(id);
        return page;
    }

    private void removeCertainPagesFromList(Page page, List<Page> pageList) {
        pageList.remove(page);
        for (Page child : findAllByAncestorId(page.getId())) {
            removeCertainPagesFromList(child, pageList);
        }
    }

    @Override
    public List<Page> findAllPagesBySurfaceWithoutPage(Page page, Surface surface) {
        List<Page> pageList = findAllBySurface(surface);
        removeCertainPagesFromList(page, pageList);
        return TreeFunc.sortTree(pageList);
    }


    @Override
    public List<Page> findAllBySurfaceAndNameLike(Surface surface, String nameLike) {
        nameLike += "%";
        return pageRepository.findAllBySurfaceAndNameLikeOrderByName(surface, nameLike);
    }

    @Override
    public List<Page> findAllBySurfaceAndContentLike(Surface surface, String contentLike) {
        contentLike = "%" + contentLike + "%";
        return pageRepository.findAllBySurfaceAndContentLikeOrderByName(surface, contentLike);
    }
}