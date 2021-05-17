package knowledge.service.tree_function;

import knowledge.model.Page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TreeFunc {
    public static List<Page> sortTree(List<Page> pages) {
        List<Page> tree = new ArrayList<>();
        int maxlvl = 1;
        for (Page page : pages) {
            if (page.getLevel() > maxlvl) {
                maxlvl = page.getLevel();
            }
        }

        Collections.reverse(pages);

        for (int i = pages.size() - 1; i >= 0; i--) {
            if (pages.get(i).getAncestorId() == 0) {
                tree.add(pages.get(i));
            }
        }
        for (int lvl = 2; lvl <= maxlvl; lvl++) {
            for (Page page : pages) {
                if (page.getLevel() == lvl) {
                    tree.add(getIndexOfPageById(page.getAncestorId(), tree) + 1, page);
                }
            }
        }
        return tree;
    }

    public static int getIndexOfPageById(int id, List<Page> pages) {
        for (Page page : pages) {
            if (page.getId() == id) {
                return pages.indexOf(page);
            }
        }
        return -1;
    }
}