package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player create(Player player) {
        return playerRepository.saveAndFlush(player);
    }

    @Override
    public List<Player> readAll(Specification<Player> specification) {
        return playerRepository.findAll(specification);
    }

    @Override
    public Page<Player> readAll(Specification<Player> specification, Pageable sorted) {
        return playerRepository.findAll(specification, sorted);
    }

    @Override
    public Player read(Long id) {
        return playerRepository.getOne(id);
    }

    @Override
    public void update(Player player, Long id) {
        if (playerRepository.existsById(id)) {
            player.setId(id);
            playerRepository.save(player);
        }
    }

    @Override
    public Player delete(Long id) {
        if (playerRepository.existsById(id)) {
            Player player = playerRepository.getOne(id);
            playerRepository.deleteById(id);
            return player;
        }
        return null;
    }

    @Override
    public Specification<Player> filterByName(String name) {
        return (root, query, cb) -> name == null ? null : cb.like(root.get("name"), "%" + name + "%");
    }

    @Override
    public Specification<Player> filterByTitle(String title) {
        return (root, query, criteriaBuilder) -> title == null ? null : criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }

    @Override
    public Specification<Player> filterByRace(Race race) {
        return (root, query, criteriaBuilder) -> race == null ? null : criteriaBuilder.equal(root.get("race"), race);
    }

    @Override
    public Specification<Player> filterByProfession(Profession profession) {
        return (root, query, criteriaBuilder) -> profession == null ? null : criteriaBuilder.equal(root.get("race"), profession);
    }

    @Override
    public Specification<Player> filterByDate(Long after, Long before) {
        return (root, query, criteriaBuilder) -> {
            if (after == null && before == null)
                return null;
            if (before == null) {
                Date after1 = new Date(after);
                return criteriaBuilder.greaterThanOrEqualTo(root.get("birthday"), after1);
            }
            if (after == null) {
                Date before1 = new Date(before);
                return criteriaBuilder.lessThanOrEqualTo(root.get("birthday"), before1);
            }
            Date after1 = new Date(after);
            Date before1 = new Date(before);

            return criteriaBuilder.between(root.get("birthday"), after1, before1);
        };
    }

    @Override
    public Specification<Player> filterByBanned(Boolean banned) {
        return (root, query, criteriaBuilder) -> {
            if (banned == null)
                return null;
            if (banned)
                return criteriaBuilder.isTrue(root.get("banned"));
            else return criteriaBuilder.isFalse(root.get("banned"));
        };
    }

    @Override
    public Specification<Player> filterByExperience(Integer minExperience, Integer maxExperience) {
        return (root, query, criteriaBuilder) -> {
            if (minExperience == null && maxExperience == null)
                return null;
            if (maxExperience == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("experience"), minExperience);
            }
            if (minExperience == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("experience"), maxExperience);
            }

            return criteriaBuilder.between(root.get("experience"), minExperience, maxExperience);
        };
    }

    @Override
    public Specification<Player> filterByLevel(Integer minLevel, Integer maxLevel) {
        return (root, query, criteriaBuilder) -> {
            if (minLevel == null && maxLevel == null)
                return null;
            if (maxLevel == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("experience"), minLevel);
            }
            if (minLevel == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("experience"), maxLevel);
            }

            return criteriaBuilder.between(root.get("experience"), minLevel, maxLevel);
        };
    }
}
