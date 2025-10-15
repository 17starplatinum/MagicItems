package ru.itmo.cs.dandadan.magicitems.mapper;

import org.springframework.stereotype.Component;
import ru.itmo.cs.dandadan.magicitems.dto.data.MagicItemRequestDto;
import ru.itmo.cs.dandadan.magicitems.dto.data.MagicItemResponseDto;
import ru.itmo.cs.dandadan.magicitems.entity.MagicItem;
import ru.itmo.cs.dandadan.magicitems.entity.Rarity;
import ru.itmo.cs.dandadan.magicitems.entity.UniversalNature;

@Component
public class MagicItemMapper {
    public MagicItem mapToModel(MagicItemRequestDto dto) {
        return MagicItem.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .rarity(Rarity.valueOf(dto.getRarity()))
                .powerLevel(dto.getPowerLevel())
                .cooldownSeconds(dto.getCooldownSeconds())
                .cursed(dto.isCursed())
                .universalNature(UniversalNature.valueOf(dto.getUniversalNature()))
                .build();
    }

    public MagicItemResponseDto mapToResponse(MagicItem magicItem) {
        return MagicItemResponseDto.builder()
                .id(String.valueOf(magicItem.getId()))
                .name(magicItem.getName())
                .description(magicItem.getDescription())
                .rarity(magicItem.getRarity().name())
                .powerLevel(magicItem.getPowerLevel())
                .cooldownSeconds(magicItem.getCooldownSeconds())
                .cursed(magicItem.isCursed())
                .universalNature(magicItem.getUniversalNature().name())
                .createdAt(magicItem.getCreatedAt())
                .build();
    }
}
