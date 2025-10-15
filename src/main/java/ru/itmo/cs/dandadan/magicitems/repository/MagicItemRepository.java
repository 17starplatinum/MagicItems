package ru.itmo.cs.dandadan.magicitems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.cs.dandadan.magicitems.entity.MagicItem;

import java.util.UUID;

@Repository
public interface MagicItemRepository extends JpaRepository<MagicItem, UUID> {
}
