package com.riwi.sitekeeper.dtos.responses;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ObjectRes {

    private Long id;

    private String name;

    private String description;

    private String image;

    private Long spaceId;

}
