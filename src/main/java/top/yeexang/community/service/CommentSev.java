package top.yeexang.community.service;

import top.yeexang.community.dto.CommentCreateDTO;
import top.yeexang.community.dto.CommentDTO;
import top.yeexang.community.dto.ResultDTO;
import top.yeexang.community.dto.UserDTO;
import top.yeexang.community.entity.Comment;

import java.util.List;

/**
 * @author yeeq
 * @date 2020/11/28
 */
public interface CommentSev {

    /**
     * 获取对应id帖子下的全部评论
     *
     * @param id   对应帖子id
     * @param type 评论类型
     * @return 对应帖子的全部评论
     */
    List<CommentDTO> getCommentList(Long id, Integer type);

    /**
     * 将评论实体转换为可返回的数据传输实体
     *
     * @param comment 评论
     * @return 数据传输对象
     */
    CommentDTO getCommentDTO(Comment comment);

    /**
     * 添加评论
     *
     * @param commentCreateDTO 评论
     * @param userDTO          评论用户
     * @return 数据传输对象
     */
    ResultDTO<?> createComment(CommentCreateDTO commentCreateDTO, UserDTO userDTO);

    /**
     * 删除某个父id下的所有评论
     *
     * @param parentId 父id
     */
    void deleteCommentByParentId(Long parentId);

    /**
     * 增加评选下子评论数
     *
     * @param parentId 父评论id
     */
    void incSubComment(Long parentId);

    /**
     * 创建评论
     * @param commentCreateDTO 新增评论DTO
     * @param userDTO          评论用户
     * @return 评论
     */
    Comment newComment(CommentCreateDTO commentCreateDTO, UserDTO userDTO);
}
