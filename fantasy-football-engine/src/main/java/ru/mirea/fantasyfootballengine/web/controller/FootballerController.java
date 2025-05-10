package ru.mirea.fantasyfootballengine.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.mirea.fantasyfootballengine.dto.footballer.FootballerDTO;
import ru.mirea.fantasyfootballengine.service.FootballerService;

import java.util.List;

@RestController
@RequestMapping("/footballers")
@RequiredArgsConstructor
public class FootballerController {

    private final FootballerService footballerService;

    @PostMapping
    public FootballerDTO createFootballer(@RequestBody FootballerDTO dto) {
        return footballerService.save(dto);
    }

   @GetMapping("/search")
   public List<FootballerDTO> searchFootballers(@RequestParam String name) {
       return footballerService.searchByName(name);
   }
}

