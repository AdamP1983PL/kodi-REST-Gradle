package com.crude.tasks.client;

import com.crude.tasks.config.TrelloConfig;
import com.crude.tasks.domain.CreatedTrelloCardDto;
import com.crude.tasks.domain.TrelloBoardDto;
import com.crude.tasks.domain.TrelloCardDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class TrelloClientTest {

    @InjectMocks
    private TrelloClient trelloClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TrelloConfig trelloConfig;

    @Test
    public void scheduleFetchTrelloBoards() throws URISyntaxException {
        // given
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");
        when(trelloConfig.getTrelloUser()).thenReturn("test");

        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_id", "Kodilla", new ArrayList<>());

        URI uri = new URI("http://test.com/members/test/boards?key=test&token=test&fields=name,id&lists=all");

        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(trelloBoards);

        // when
        List<TrelloBoardDto> fetchTrelloBoards = trelloClient.getTrelloBoards();

        // then
        assertEquals(1, fetchTrelloBoards.size());
        assertEquals("test_id", fetchTrelloBoards.get(0).getId());
        assertEquals("Kodilla", fetchTrelloBoards.get(0).getName());
        assertEquals(new ArrayList<>(), fetchTrelloBoards.get(0).getLists());
    }

    @Test
    public void scheduleCreateCard() throws URISyntaxException {
        // given
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "Test task",
                "Test Description",
                "top",
                "test_id"
        );

        URI uri = new URI("http://test.com/cards?key=test&token=test&name=Test%20task&desc=Test%20Description&pos=top&idList=test_id");

        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "1",
                "test task",
                "http://test.com"
        );

        when(restTemplate.postForObject(uri, null, CreatedTrelloCardDto.class)).thenReturn(createdTrelloCardDto);

        // when
        CreatedTrelloCardDto newCard = trelloClient.createNewCard(trelloCardDto);

        // then
        assertEquals("1", newCard.getId());
        assertEquals("test task", newCard.getName());
        assertEquals("http://test.com", newCard.getShortUrl());
    }

    @Test
    public void shouldReturnEmptyList() throws URISyntaxException {
        // Given
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");
        when(trelloConfig.getTrelloUser()).thenReturn("test");
//        String url = "http://test.com/members/test/boards?key=test&token=test&fields=name,id&lists=all";
        URI uri = new URI("http://test.com/members/test/boards?key=test&token=test&fields=name,id&lists=all");
        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(new TrelloBoardDto[0]);

        // When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();

        // Then
        assertEquals(0, fetchedTrelloBoards.size());
    }

}
