package com.crude.tasks.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CreatedTrelloCardDtoTest {


    @Test
    void shouldCreateTrelloCardDto() {
        // given
        Trello trello1 = new Trello(10, 20);
        AttachmentsByType attachmentsByType = new AttachmentsByType(trello1);
        TrelloBadgesDto trelloBadgesDto = new TrelloBadgesDto(100, attachmentsByType);
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1", "test_name",
                "shortUrl", trelloBadgesDto);

        // when, then
        assertEquals(10, createdTrelloCardDto.getBadges().getAttachmentsByType().getTrello().getBoard());
        assertEquals(20, createdTrelloCardDto.getBadges().getAttachmentsByType().getTrello().getCard());
        assertEquals(100, createdTrelloCardDto.getBadges().getVotes());
        assertEquals("1", createdTrelloCardDto.getId());
        assertEquals("test_name", createdTrelloCardDto.getName());
        assertEquals("shortUrl", createdTrelloCardDto.getShortUrl());

    }

}
