package ru.mirea.fantasyfootballengine.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.fantasyfootballengine.dto.team.AddFootballerRequest;
import ru.mirea.fantasyfootballengine.dto.team.CreateTeamRequest;
import ru.mirea.fantasyfootballengine.dto.team.TeamResponse;
import ru.mirea.fantasyfootballengine.dto.team.UpdateLineupRequest;
import ru.mirea.fantasyfootballengine.service.TeamService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<TeamResponse> createTeam(@RequestBody CreateTeamRequest request,
                                                   @RequestHeader("X-User-Id") UUID userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teamService.createTeam(request, userId));
    }

    @GetMapping
    public ResponseEntity<List<TeamResponse>> getUserTeams(@RequestHeader("X-User-Id") UUID userId) {
        return ResponseEntity.ok(teamService.getTeamsByUser(userId));
    }

    @PostMapping("/{id}/footballers")
    public ResponseEntity<Void> addFootballerToTeam(@PathVariable UUID id,
                                                    @RequestBody AddFootballerRequest request) {
        teamService.addFootballerToTeam(id, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/lineup")
    public ResponseEntity<Void> updateLineup(@PathVariable UUID id,
                                             @RequestBody UpdateLineupRequest request) {
        teamService.updateLineup(id, request);
        return ResponseEntity.ok().build();
    }
}

