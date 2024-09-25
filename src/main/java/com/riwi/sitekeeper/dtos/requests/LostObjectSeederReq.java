package com.riwi.sitekeeper.dtos.requests;

import com.riwi.sitekeeper.enums.LostObjectsStatus;
import lombok.Data;

@Data
public class LostObjectSeederReq {
    private Long Id;

    private String name;

    private String description;

    private String image;

    private Long createdBy;

    private Long updatedBy;

    private LostObjectsStatus status;

    private Long spaceId;
}
