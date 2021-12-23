package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class PlayerController {

    private PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    /*@GetMapping("/rest/players")
    public ResponseEntity<?> readAll() {
        List<Player> players = playerService.readAll();

        return players != null ? new ResponseEntity<>(players, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/

    @GetMapping("/players")
//    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Player>> readAll(@RequestParam(value = "name", required = false) String name,
                                                @RequestParam(value = "title", required = false) String title,
                                                @RequestParam(value = "race", required = false) Race race,
                                                @RequestParam(value = "profession", required = false) Profession profession,
                                                @RequestParam(value = "after", required = false) Long after,
                                                @RequestParam(value = "before", required = false) Long before,
                                                @RequestParam(value = "banned", required = false) Boolean banned,
                                                @RequestParam(value = "minExperience", required = false) Integer minExperience,
                                                @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
                                                @RequestParam(value = "minLevel", required = false) Integer minLevel,
                                                @RequestParam(value = "maxLevel", required = false) Integer maxLevel,
                                                @RequestParam(value = "order", required = false, defaultValue = "ID") PlayerOrder order,
                                                @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                                @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));

        /*return playerService.readAll(Specification.where(playerService.filterByName(name))
                .and(playerService.filterByTitle(title))
                .and(playerService.filterByRace(race))
                .and(playerService.filterByProfession(profession))
                .and(playerService.filterByDate(after, before))
                .and(playerService.filterByBanned(banned))
                .and(playerService.filterByExperience(minExperience, maxExperience))
                .and(playerService.filterByLevel(minLevel, maxLevel)), pageable).getContent();*/

        List<Player> players = playerService.readAll(Specification.where(playerService.filterByName(name))
                .and(playerService.filterByTitle(title))
                .and(playerService.filterByRace(race))
                .and(playerService.filterByProfession(profession))
                .and(playerService.filterByDate(after, before))
                .and(playerService.filterByBanned(banned))
                .and(playerService.filterByExperience(minExperience, maxExperience))
                .and(playerService.filterByLevel(minLevel, maxLevel)), pageable).getContent();

        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    @GetMapping("/players/count")
    @ResponseStatus(HttpStatus.OK)
    public Integer getCount(@RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "title", required = false) String title,
                            @RequestParam(value = "race", required = false) Race race,
                            @RequestParam(value = "profession", required = false) Profession profession,
                            @RequestParam(value = "after", required = false) Long after,
                            @RequestParam(value = "before", required = false) Long before,
                            @RequestParam(value = "banned", required = false) Boolean banned,
                            @RequestParam(value = "minExperience", required = false) Integer minExperience,
                            @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
                            @RequestParam(value = "minLevel", required = false) Integer minLevel,
                            @RequestParam(value = "maxLevel", required = false) Integer maxLevel) {

        return playerService.readAll(Specification.where(playerService.filterByName(name))
                .and(playerService.filterByTitle(title))
                .and(playerService.filterByRace(race))
                .and(playerService.filterByProfession(profession))
                .and(playerService.filterByDate(after, before))
                .and(playerService.filterByBanned(banned))
                .and(playerService.filterByExperience(minExperience, maxExperience))
                .and(playerService.filterByLevel(minLevel, maxLevel))).size();
    }

    @GetMapping("/players/{id}")
    public ResponseEntity<Player> read(@PathVariable(value = "id") String idString) {
        Long id = playerService.checkId(idString);

        if (id == -1L)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Player player = playerService.read(id);

        return player != null ? new ResponseEntity<>(player, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/players")
    public ResponseEntity<Player> addShip(@RequestBody Player player) {

        Player createdPlayer = playerService.create(player);

        return createdPlayer != null ? new ResponseEntity<>(createdPlayer, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/players/{id}")
    public ResponseEntity<Player> update(@PathVariable(value = "id") String idString, @RequestBody Player player) {
        Long id = playerService.checkId(idString);

        if (id == -1L)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Player updatedPlayer = playerService.update(player, id);

        return player != null ? new ResponseEntity<>(updatedPlayer, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") String idString) {
        Long id = playerService.checkId(idString);

        if (id == -1L)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Player player = playerService.delete(id);

        return player != null ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
