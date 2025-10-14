package ru.itmo.cs.dandadan.magicitems.dto.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MagicItemRequestDto {
    @NotNull
    @NotBlank
    private String name;
    private String description;
    @NotBlank
    private String rarity;
    private int powerLevel;
    private int cooldownSeconds;
    private boolean cursed;
    @NotBlank
    private String universalNature;
}
