package top.yeexang.community.dto;

import lombok.Data;

/**
 * @author yeeq
 * @date 2020/11/28
 */
@Data
public class CommentDTO {

    private Long id;
    private Long parentId;
    private Integer type;
    private String content;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private UserDTO userDTO;
    private Long likeCount;
    private Integer commentCount;
}