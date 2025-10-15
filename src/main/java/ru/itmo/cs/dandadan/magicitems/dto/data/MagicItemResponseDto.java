package ru.itmo.cs.dandadan.magicitems.dto.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class MagicItemResponseDto {
    @NotNull
    private String id;
    @NotNull
    @NotBlank
    private String name;
    private String description;
    private String rarity;
    private int powerLevel;
    private int cooldownSeconds;
    private boolean cursed;
    private String universalNature;
    private Instant createdAt;
}
