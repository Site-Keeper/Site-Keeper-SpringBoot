package com.riwi.sitekeeper.entitites;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "lost_objects")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class LostObjectsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String image;

    @ManyToOne
    @JoinColumn(nullable = false)
    private SpaceEntity spaceId;
}
