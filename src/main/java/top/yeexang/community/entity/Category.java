package top.yeexang.community.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author yeeq
 * @date 2020/10/6
 */
@Data
@Accessors(chain = true)
public class Category {

    private Integer id;
    private String categoryName;
    private String coverUrl;
}