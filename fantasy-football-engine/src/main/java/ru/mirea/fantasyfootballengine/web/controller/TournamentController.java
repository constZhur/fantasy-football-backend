package ru.mirea.fantasyfootballengine.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.fantasyfootballengine.dto.tournament.CreateTournamentRequest;
import ru.mirea.fantasyfootballengine.entity.jdbc.Tournament;
import ru.mirea.fantasyfootballengine.service.TournamentService;

@RestController
@RequestMapping("/api/tournaments")
@RequiredArgsConstructor
public class TournamentController {

    private final TournamentService tournamentService;

    @PostMapping
    public ResponseEntity<Tournament> createTournament(@RequestBody CreateTournamentRequest request) {
        Tournament tournament = tournamentService.createTournament(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(tournament);
    }


}

