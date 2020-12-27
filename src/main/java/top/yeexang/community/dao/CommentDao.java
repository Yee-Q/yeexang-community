package top.yeexang.community.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.yeexang.community.entity.Comment;

import java.util.List;

/**
 * @author yeeq
 * @date 2020/11/28
 */
@Repository
public interface CommentDao {

    /**
     * 根据父id和评论类型选择评论
     *
     * @param id   帖子id
     * @param type 评论类型
     * @return 评论
     */
    List<Comment> selectCommentByParentIdAndType(@Param("id") Long id, @Param("type") Integer type);

    /**
     * 根据父id获取评论
     * @param id 帖子id
     * @return 评论
     */
    List<Comment> selectCommentByParentId(Long id);

    /**
     * 根据id获取评论
     * @param id 评论id
     * @return 评论
     */
    Comment selectCommentById(Long id);

    /**
     * 添加评论
     * @param comment 评论
     */
    void createComment(Comment comment);

    /**
     * 删除某个父id下的所有评论
     * @param parentId 父id
     */
    void deleteCommentByParentId(Long parentId);

    /**
     * 更新评论的子评论数
     * @param id 评论id
     */
    void updateSubCommentCountById(Long id);
}
