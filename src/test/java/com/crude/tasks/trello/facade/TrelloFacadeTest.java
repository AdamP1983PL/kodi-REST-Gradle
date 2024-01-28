package com.crude.tasks.trello.facade;

import com.crude.tasks.domain.*;
import com.crude.tasks.mapper.TrelloMapper;
import com.crude.tasks.service.TrelloService;
import com.crude.tasks.trello.validator.TrelloValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrelloFacadeTest {

    @InjectMocks
    private TrelloFacade trelloFacade;

    @Mock
    private TrelloService trelloService;

    @Mock
    private TrelloValidator trelloValidator;

    @Mock
    private TrelloMapper trelloMapper;

    @Test
    void shouldFetchEmptyList() {
        // given
        List<TrelloListDto> trelloLists = List.of(new TrelloListDto("1", "test_list", false));
        List<TrelloBoardDto> trelloBoards = List.of(new TrelloBoardDto("1", "test", trelloLists));
        List<TrelloList> mappedTrelloLists = List.of(new TrelloList("1", "test_list", false));
        List<TrelloBoard> mappedTrelloBoards = List.of(new TrelloBoard("1", "test", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(List.of());
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(List.of());

        // when
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();

        // then
        assertNotNull(trelloBoardDtos);
        assertEquals(0, trelloBoardDtos.size());
    }

    @Test
    void shouldFetchTrelloBoards() {
        // Given
        List<TrelloListDto> trelloLists =
                List.of(new TrelloListDto("1", "test_list", false));

        List<TrelloBoardDto> trelloBoards =
                List.of(new TrelloBoardDto("1", "test", trelloLists));

        List<TrelloList> mappedTrelloLists =
                List.of(new TrelloList("1", "test_list", false));

        List<TrelloBoard> mappedTrelloBoards =
                List.of(new TrelloBoard("1", "test", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(trelloBoards);
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(mappedTrelloBoards);

        // When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();

        // Then
        assertNotNull(trelloBoardDtos);
        assertEquals(1, trelloBoardDtos.size());

        trelloBoardDtos.forEach(trelloBoardDto -> {

            assertEquals("1", trelloBoardDto.getId());
            assertEquals("test", trelloBoardDto.getName());

            trelloBoardDto.getLists().forEach(trelloListDto -> {
                assertEquals("1", trelloListDto.getId());
                assertEquals("test_list", trelloListDto.getName());
                assertFalse(trelloListDto.isClosed());
            });
        });
    }

    @Test
    void shouldCreateTrelloCard() {
        // given
        TrelloCard trelloCard = new TrelloCard("test_name", "test_description",
                "test_pos", "test_listId");
        TrelloCardDto trelloCardDto = new TrelloCardDto("test_name", "test_description",
                "test_pos", "test_listId");

        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "test_id", "created_trello_card_test_name","test_shortUrl",
                new TrelloBadgesDto(
                        999, new AttachmentsByType(
                                new Trello(20, 10))
        ));
        when(trelloMapper.mapToCard(trelloCardDto)).thenReturn(trelloCard);
        when(trelloService.createTrelloCard(trelloMapper.mapToCardDto(trelloCard))).thenReturn(createdTrelloCardDto);

        // when
        CreatedTrelloCardDto createdCard = trelloFacade.createCard(trelloCardDto);

        // then
        assertNotNull(createdCard);
        assertEquals("created_trello_card_test_name", createdCard.getName());
        assertEquals("test_shortUrl", createdCard.getShortUrl());
        assertEquals("test_id", createdCard.getId());
        assertEquals(999, createdCard.getBadges().getVotes());
        assertEquals(10, createdCard.getBadges().getAttachmentsByType().getTrello().getCard());
        assertEquals(20, createdCard.getBadges().getAttachmentsByType().getTrello().getBoard());
    }

}
