package top.yeexang.community.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.yeexang.community.entity.Category;

import java.util.List;

/**
 * @author yeeq
 * @date 2020/10/6
 */
@Repository
public interface CategoryDao {

    /**
     * 查询所有专栏分类信息并返回
     *
     * @return 所有专栏分类信息
     */
    List<Category> selectAllCategory();

    /**
     * 根据id查询专栏分类信息
     *
     * @param id 分类id
     * @return 符合查询条件的  专栏分类信息
     */
    Category selectCategoryById(@Param("id") Integer id);
}
