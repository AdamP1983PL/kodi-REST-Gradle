package com.crude.tasks.service;

import com.crude.tasks.client.TrelloClient;
import com.crude.tasks.config.AdminConfig;
import com.crude.tasks.domain.CreatedTrelloCardDto;
import com.crude.tasks.domain.Mail;
import com.crude.tasks.domain.TrelloCardDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrelloServiceTest {

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService simpleEmailService;

    @Mock
    private AdminConfig adminConfig;

    @InjectMocks
    private TrelloService trelloService;

    @Test
    void testCreateTrelloCard() {
        // given
        TrelloCardDto trelloCardDto = new TrelloCardDto();
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto();
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);

        // when
        CreatedTrelloCardDto resultTrelloCard = trelloService.createTrelloCard(trelloCardDto);

        // then
        verify(simpleEmailService, times(1)).send(any(Mail.class));
    }

}
