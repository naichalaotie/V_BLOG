package com.xxx.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Archive {
    private Integer year;
    private Integer month;
    private Integer count;
}
