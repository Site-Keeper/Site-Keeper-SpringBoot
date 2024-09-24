package com.riwi.sitekeeper.entities;

import com.riwi.sitekeeper.enums.ReportStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ReportEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Boolean isEvent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status;

    @Column(nullable = false)
    private LocalDateTime theDate;

    @Column(nullable = false)
    private Long topicId;

    @Column(nullable = true)
    private String image;

    @Column(nullable = true)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "space_id", nullable = false)
    private SpaceEntity spaceId;

    @ManyToOne
    @JoinColumn(name = "object_id", nullable = true)
    private ObjectEntity objectId;


}
