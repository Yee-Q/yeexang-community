package top.yeexang.community.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author yeeq
 * @date 2020/11/28
 */
@Data
@Accessors(chain = true)
public class Comment {

    private Long id;
    private Long parentId;
    private String content;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Integer commentCount;
}
