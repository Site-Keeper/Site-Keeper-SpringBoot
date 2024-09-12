package com.riwi.sitekeeper.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpaceRes {

    private Long id;

    private String name;

    private String location;

    private String description;

    private String image;

}
