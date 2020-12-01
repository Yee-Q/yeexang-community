package top.yeexang.community.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.yeexang.community.dao.CategoryDao;
import top.yeexang.community.dto.CategoryDTO;
import top.yeexang.community.entity.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>论坛专栏分类缓存，用于存放专栏分类名</p>
 *
 * @author yeeq
 * @date 2020/10/6
 */
@Slf4j
@Component
public class CategoryCache {

    @Autowired
    private CategoryDao categoryDao;

    private static final List<CategoryDTO> CATEGORYDTOS = new ArrayList<>();

    /**
     * 获取分类缓存中的所有数据
     *
     * @return 分类缓存中的所有数据
     */
    public List<CategoryDTO> getAllCategory() {
        // 为空则先进行初始化
        if (CATEGORYDTOS.isEmpty()) {
            initCategoryCache();
        }
        List<CategoryDTO> categoryDTOList = new ArrayList<>(CATEGORYDTOS);
        return categoryDTOList;
    }

    /**
     * 初始化专栏分类缓存
     *
     * @return true 为成功初始化，false 为初始化失败
     */
    public boolean initCategoryCache() {

        List<Category> updateCategoryList = categoryDao.selectAllCategory();
        if (updateCategoryList.isEmpty()) {
            log.info("无法从数据库获取数据，专栏分类初始化失败");
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