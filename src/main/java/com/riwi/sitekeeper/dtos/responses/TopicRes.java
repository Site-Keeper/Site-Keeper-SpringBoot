package com.riwi.sitekeeper.dtos.responses;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicRes {

    private Long id;

    private String name;

    private String icon;
}
