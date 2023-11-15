package com.crude.tasks.controller;

import com.crude.tasks.client.TrelloClient;
import com.crude.tasks.domain.CreatedTrelloCard;
import com.crude.tasks.domain.TrelloBoardDto;
import com.crude.tasks.domain.TrelloCardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

        List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();

        trelloBoards.forEach(trelloBoardDto -> {
            System.out.println(trelloBoardDto.getId() + " - " + trelloBoardDto.getName());
            System.out.println("This board contains lists: ");
            trelloBoardDto.getLists().forEach(trelloList -> {
                System.out.println(trelloList.getName() + " - " + trelloList.getId() + " - " + trelloList.isClosed());
            });
        });

        System.out.println("Boards with title containing \"Kodilla\":");
        List<String> trelloBoardNamesWithKodilla = trelloBoards.stream()
                .map(s -> s.getId() + " : " + s.getName())
                .filter(s -> s.contains("Kodilla"))
                .toList();

        System.out.println(trelloBoardNamesWithKodilla);
    }

    @PostMapping("cards")
    public CreatedTrelloCard createTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        return trelloClient.createNewCard(trelloCardDto);
    }
}
