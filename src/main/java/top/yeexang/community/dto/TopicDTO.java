package top.yeexang.community.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import top.yeexang.community.entity.Category;

/**
 * @author yeeq
 * @date 2020/10/7
 */
@Data
@Accessors(chain = true)
public class TopicDTO {

    private Long id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creatorId;
    private UserDTO creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private Long gmtLatestComment;
    private Integer status;
    private Integer categoryId;
    private Category category;
}
