package ru.itmo.cs.dandadan.magicitems.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.cs.dandadan.magicitems.dto.data.MagicItemRequestDto;
import ru.itmo.cs.dandadan.magicitems.dto.data.MagicItemResponseDto;
import ru.itmo.cs.dandadan.magicitems.entity.MagicItem;
import ru.itmo.cs.dandadan.magicitems.exception.MagicItemNotFoundException;
import ru.itmo.cs.dandadan.magicitems.mapper.MagicItemMapper;
import ru.itmo.cs.dandadan.magicitems.repository.MagicItemRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MagicItemService {
    private final MagicItemRepository repository;
    private final MagicItemMapper mapper;

    public List<MagicItemResponseDto> getAll() {
        return repository.findAll()
                .stream().map(mapper::mapToResponse)
                .toList();
    }
    public MagicItemResponseDto getById(UUID id) {
        return mapper.mapToResponse(repository.findById(id)
                .orElseThrow(() -> new MagicItemNotFoundException("Предмета с id = " + id + "не существует.")));
    }


    public MagicItemResponseDto save(MagicItemRequestDto dto) {
        MagicItem item = mapper.mapToModel(dto);
        return mapper.mapToResponse(repository.save(item));
    }
}
