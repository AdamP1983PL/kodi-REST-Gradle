package com.crude.tasks.service;

import com.crude.tasks.client.TrelloClient;
import com.crude.tasks.config.AdminConfig;
import com.crude.tasks.domain.CreatedTrelloCardDto;
import com.crude.tasks.domain.Mail;
import com.crude.tasks.domain.TrelloBoardDto;
import com.crude.tasks.domain.TrelloCardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class TrelloService {

    public static final String SUBJECT = "Tasks: new Trello card";
    private final TrelloClient trelloClient;
    private final SimpleEmailService emailService;
    private final AdminConfig adminConfig;

    public List<TrelloBoardDto> fetchTrelloBoards() {
        return trelloClient.getTrelloBoards();
    }

    public CreatedTrelloCardDto createTrelloCard(final TrelloCardDto trelloCardDto) {
        CreatedTrelloCardDto newCard = trelloClient.createNewCard(trelloCardDto);
        ofNullable(newCard).ifPresent(card -> emailService.send(
                new Mail(
                        adminConfig.getAdminMail(),
                        SUBJECT,
                        "New card: " + trelloCardDto.getName() + " has been created on your Trello account",
                        null
                )));
        return newCard;
    }

//    public CreatedTrelloCard createTrelloCard(final TrelloCardDto trelloCardDto) {
//        CreatedTrelloCard newCard = trelloClient.createNewCard(trelloCardDto);
//        ofNullable(newCard).ifPresent(card -> emailService.send(
//                new Mail(
//                        adminConfig.getAdminMail(),
//                        SUBJECT,
//                        "New card: " + trelloCardDto.getName() + " has been created on your Trello account"
//                )));
//        return newCard;
//    }



}
