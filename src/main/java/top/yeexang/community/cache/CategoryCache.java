package top.yeexang.community.cache;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.yeexang.community.dao.CategoryDao;
import top.yeexang.community.dto.CategoryDTO;
import top.yeexang.community.dto.TopicDTO;
import top.yeexang.community.entity.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * @author yeeq
 * @date 2020/10/6
 */
@Component
public class CategoryCache {

    @Autowired
    private CategoryDao categoryDao;

    private static final List<CategoryDTO> CATEGORYDTOS = new CopyOnWriteArrayList<>();

    /**
     * 获取分类缓存中的所有数据
     *
     * @return 分类缓存中的所有数据
     */
    public List<CategoryDTO> getAllCategory() {

        List<CategoryDTO> categoryDTOList = new ArrayList<>(CATEGORYDTOS);
        return categoryDTOList;
    }

    /**
     * 初始化专栏分类缓存
     */
    public boolean initCategoryCache() {

        List<Category> updateCategoryList = categoryDao.selectAllCategory();
        if (!updateCategoryList.isEmpty()) {
            return false;
        }
        List<CategoryDTO> categoryDTOList = updateCategoryList.stream().map(category -> {
            CategoryDTO categoryDTO = new CategoryDTO();
            BeanUtils.copyProperties(category, categoryDTO);
            return categoryDTO;
        }).collect(Collectors.toList());
        CATEGORYDTOS.clear();
        CATEGORYDTOS.addAll(categoryDTOList);
        return true;
    }
}