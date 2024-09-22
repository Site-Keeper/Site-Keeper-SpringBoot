package com.riwi.sitekeeper.dtos.responses;

import com.riwi.sitekeeper.entitites.SpaceEntity;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ObjectRes {

    private Long id;

    private String name;

    private String description;

    private String location;

    private String image;

    private Long spaceId;

}
