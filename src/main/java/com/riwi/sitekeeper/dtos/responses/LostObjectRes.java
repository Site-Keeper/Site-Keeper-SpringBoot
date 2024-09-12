package com.riwi.sitekeeper.dtos.responses;

import com.riwi.sitekeeper.entitites.SpaceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LostObjectRes {

    private Long id;

    private String name;

    private String description;

    private String image;

    private SpaceRes spaceId;

}
