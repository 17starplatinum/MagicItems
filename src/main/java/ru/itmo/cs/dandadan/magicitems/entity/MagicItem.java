package ru.itmo.cs.dandadan.magicitems.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "magic_items")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MagicItem {
    @Id
    @UuidGenerator
    private UUID id;

    @NotNull
    @NotBlank
    @Size(max = 120)
    @Column(length = 120)
    private String name;

    @Size(max = 1000)
    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Builder.Default
    @Column(length = 10)
    private Rarity rarity = Rarity.COMMON;

    @Min(0)
    @Max(10_000)
    @NotNull
    @Builder.Default
    private int powerLevel = 0;

    @Min(0)
    @NotNull
    @Builder.Default
    private int cooldownSeconds = 0;

    @NotNull
    @Builder.Default
    private boolean cursed = false;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(length = 11)
    @Builder.Default
    private UniversalNature universalNature = UniversalNature.NONE;

    @CreatedDate
    @Column(updatable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();
}
