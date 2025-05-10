package ru.mirea.fantasyfootballengine.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.fantasyfootballengine.dto.footballer.FootballerDTO;
//import ru.mirea.fantasyfootballengine.entity.elasticsearch.FootballerDocument;
import ru.mirea.fantasyfootballengine.entity.elasticsearch.FootballerDocument;
import ru.mirea.fantasyfootballengine.entity.jdbc.Footballer;
//import ru.mirea.fantasyfootballengine.repository.elasticsearch.FootballerSearchRepository;
import ru.mirea.fantasyfootballengine.repository.elasticsearch.FootballerSearchRepository;
import ru.mirea.fantasyfootballengine.repository.jdbc.FootballerRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FootballerService {

    private final FootballerRepository footballerRepository;
    private final FootballerSearchRepository footballerSearchRepository;

    @Transactional
    public FootballerDTO save(FootballerDTO dto) {
        Footballer footballer = Footballer.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .position(dto.position())
                .goals(dto.goals())
                .assists(dto.assists())
                .ownGoals(dto.ownGoals())
                .yellowCards(dto.yellowCards())
                .redCards(dto.redCards())
                .minutesPlayed(dto.minutesPlayed())
                .cleanSheets(dto.cleanSheets())
                .bestPlayerAwards(dto.bestPlayerAwards())
                .cost(dto.cost())
                .build();

        footballerRepository.save(footballer);

        FootballerDocument footballerDocument = FootballerDocument.builder()
                .id(UUID.randomUUID())
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .position(dto.position())
                .goals(dto.goals())
                .assists(dto.assists())
                .ownGoals(dto.ownGoals())
                .yellowCards(dto.yellowCards())
                .redCards(dto.redCards())
                .minutesPlayed(dto.minutesPlayed())
                .cleanSheets(dto.cleanSheets())
                .bestPlayerAwards(dto.bestPlayerAwards())
                .build();

        footballerSearchRepository.save(footballerDocument);

        return dto;
    }

    public List<FootballerDTO> searchByName(String name) {
        return footballerSearchRepository.searchByName(name).stream()
                .map(f -> new FootballerDTO(
                        f.getFirstName(), f.getLastName(), f.getPosition(),
                        f.getGoals(), f.getAssists(), f.getOwnGoals(), f.getYellowCards(),
                        f.getRedCards(), f.getMinutesPlayed(), f.getCleanSheets(), f.getBestPlayerAwards(), f.getCost()))
                .collect(Collectors.toList());
    }
}

