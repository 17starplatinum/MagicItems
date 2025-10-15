package ru.itmo.cs.dandadan.magicitems.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.cs.dandadan.magicitems.dto.data.MagicItemRequestDto;
import ru.itmo.cs.dandadan.magicitems.dto.data.MagicItemResponseDto;
import ru.itmo.cs.dandadan.magicitems.service.MagicItemService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/magic-items")
@RequiredArgsConstructor
public class MagicItemController {

    private final MagicItemService magicItemService;

    @GetMapping
    public ResponseEntity<List<MagicItemResponseDto>> getData() {
        return ResponseEntity.ok(magicItemService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MagicItemResponseDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(magicItemService.getById(id));
    }

    @PostMapping
    public ResponseEntity<MagicItemResponseDto> addData(@RequestBody MagicItemRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(magicItemService.save(dto));
    }
}
