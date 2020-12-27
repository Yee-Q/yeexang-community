package top.yeexang.community.dto;

import lombok.Data;

/**
 * @author yeeq
 * @date 2020/11/30
 */
@Data
public class CommentCreateDTO {

    private Long parentId;
    private Integer type;
    private String content;
}
