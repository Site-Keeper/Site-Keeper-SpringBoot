package com.riwi.sitekeeper.entitites;

import com.riwi.sitekeeper.enums.LostObjectsStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "lost_objects")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class LostObjectsEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String image;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'PERDIDO'")
    private LostObjectsStatus status = LostObjectsStatus.PERDIDO;

    @Column
    private Long claimedBy;

    @ManyToOne
    @JoinColumn(name = "space_id", nullable = false)
    private SpaceEntity spaceId;
}
