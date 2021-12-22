package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PlayerService {
    Player create(Player player);

    List<Player> readAll(Specification<Player> specification);

    Page<Player> readAll(Specification<Player> specification, Pageable sorted);

    Player read(Long id);

    void update(Player player, Long id);

    Player delete(Long id);

    Specification<Player> filterByName(String name);

    Specification<Player> filterByTitle(String title);

    Specification<Player> filterByRace(Race race);

    Specification<Player> filterByProfession(Profession profession);

    Specification<Player> filterByDate(Long after, Long before);

    Specification<Player> filterByBanned(Boolean banned);

    Specification<Player> filterByExperience(Integer minExperience, Integer maxExperience);

    Specification<Player> filterByLevel(Integer minLevel, Integer maxLevel);

}
