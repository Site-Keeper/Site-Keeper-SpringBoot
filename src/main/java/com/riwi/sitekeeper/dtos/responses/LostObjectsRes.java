package com.riwi.sitekeeper.dtos.responses;

import com.riwi.sitekeeper.entitites.SpaceEntity;
import com.riwi.sitekeeper.enums.LostObjectsStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LostObjectsRes {

    private Long id;

    private String name;

    private String description;

    private String location;

    private String image;

    private Long spaceId;

    private LostObjectsStatus status;

}
