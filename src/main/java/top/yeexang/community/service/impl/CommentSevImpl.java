package top.yeexang.community.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yeexang.community.dao.CommentDao;
import top.yeexang.community.dao.UserDao;
import top.yeexang.community.dto.CommentCreateDTO;
import top.yeexang.community.dto.CommentDTO;
import top.yeexang.community.dto.ResultDTO;
import top.yeexang.community.dto.UserDTO;
import top.yeexang.community.entity.Comment;
import top.yeexang.community.entity.Topic;
import top.yeexang.community.enums.ResponseCodeEnum;
import top.yeexang.community.exception.CustomizeException;
import top.yeexang.community.service.CommentSev;
import top.yeexang.community.service.NotificationSev;
import top.yeexang.community.service.TopicSev;
import top.yeexang.community.service.UserSev;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yeeq
 * @date 2020/11/28
 */
@Service
@Transactional
public class CommentSevImpl implements CommentSev {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserSev userSev;

    @Autowired
    private TopicSev topicSev;

    @Autowired
    private NotificationSev notificationSev;

    @Autowired
    private CommentSev commentSev;

    @Override
    public List<CommentDTO> getCommentList(Long id, Integer type) {
        // 0代表查找全部评论，1代表查找一级评论，2代表查找二级评论
        List<Comment> commentList;
        if (type == 0) {
            commentList = commentDao.selectCommentByParentId(id);
        } else if (type == 1 || type == 2) {
            commentList = commentDao.selectCommentByParentIdAndType(id, type);
        } else {
            throw new CustomizeException(ResponseCodeEnum.REQUEST_FAILED);
        }
        if (commentList == null || commentList.size() == 0) {
            return new ArrayList<>();
        }
        List<CommentDTO> commentDTOList = commentList.stream().map(this::getCommentDTO).collect(Collectors.toList());
        return commentDTOList;
    }

    @Override
    public CommentDTO getCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        BeanUtils.copyProperties(comment, commentDTO);
        UserDTO userDTO = userSev.getUserDTO(userDao.selectUserById(comment.getCommentator()));
        commentDTO.setUserDTO(userDTO);
        return commentDTO;
    }

    @Override
    public ResultDTO<?> createComment(CommentCreateDTO commentCreateDTO, UserDTO userDTO) {
        // 1表示回复帖子，2表示回复评论
        if (commentCreateDTO.getType() == 1) {
            // 增加帖子评论数
            topicSev.incTopicComment(commentCreateDTO.getParentId());
            // 创建评论
            Comment comment = newComment(commentCreateDTO, userDTO);
            commentDao.createComment(comment);
            // 创建通知
            Topic topic = topicSev.getTopic(commentCreateDTO.getParentId());
            try {
                notificationSev.createNotification(comment, topic.getCreator(), userDTO.getName(), topic.getTitle(), 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (commentCreateDTO.getType() == 2) {
            // 增加帖子评论数
            topicSev.incTopicComment(commentDao.selectCommentById(commentCreateDTO.getParentId()).getParentId());
            // 增加父级评论数
            incSubComment(commentCreateDTO.getParentId());
            // 创建评论
            Comment comment = newComment(commentCreateDTO, userDTO);
            commentDao.createComment(comment);
            // 创建通知
            Comment pComment = commentDao.selectCommentById(commentCreateDTO.getParentId());
            notificationSev.createNotification(comment, pComment.getCommentator(), userDTO.getName(), pComment.getContent(), 2);
        } else {
            return ResultDTO.errorOf();
        }
        return ResultDTO.successOf();
    }

    @Override
    public void deleteCommentByParentId(Long parentId) {
        commentDao.deleteCommentByParentId(parentId);
    }

    @Override
    public void incSubComment(Long parentId) {
        commentDao.updateSubCommentCountById(parentId);
    }

    @Override
    public Comment newComment(CommentCreateDTO commentCreateDTO, UserDTO userDTO) {
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId()).setContent(commentCreateDTO.getContent())
                .setType(commentCreateDTO.getType()).setGmtModified(System.currentTimeMillis())
                .setGmtCreate(System.currentTimeMillis()).setCommentator(userDTO.getId()).setLikeCount(0L)
                .setCommentCount(0);
        return comment;
    }
}