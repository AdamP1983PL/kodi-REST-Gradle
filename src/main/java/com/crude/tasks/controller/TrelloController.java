package com.crude.tasks.controller;

import com.crude.tasks.client.TrelloClient;
import com.crude.tasks.domain.TrelloBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/trello")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TrelloController {

    private final TrelloClient trelloClient;

    @GetMapping("boards")
    public void getTrelloBoards() {

        List<TrelloBoardDto> trelloBoard = trelloClient.getTrelloBoard();

        trelloBoard.forEach(trelloBoardDto -> {
            System.out.println(trelloBoardDto.getId() + " " + trelloBoardDto.getName());
        });

        System.out.println("Boards with title containing \"Kodilla\":");
        List<String> trelloBoardNamesWithKodilla = trelloBoard.stream()
                .map(s -> s.getId() + " : " + s.getName())
                .filter(s -> s.contains("Kodilla"))
                .toList();

        System.out.println(trelloBoardNamesWithKodilla);
    }
}
