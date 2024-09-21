package com.riwi.sitekeeper.dtos.responses;

import com.riwi.sitekeeper.entitites.ObjectEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    private List<ObjectRes> objects;

}
