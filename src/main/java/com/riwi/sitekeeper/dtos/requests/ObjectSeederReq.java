package com.riwi.sitekeeper.dtos.requests;

import jakarta.persistence.Table;
import lombok.Data;

@Data
public class ObjectSeederReq {
        private String name;
        private String description;
        private String image;
        private Long updatedBy;
        private Long createdBy;
        private Long spaceId;
}
