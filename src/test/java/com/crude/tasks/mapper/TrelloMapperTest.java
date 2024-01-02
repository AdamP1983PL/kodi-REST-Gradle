package com.crude.tasks.mapper;

import com.crude.tasks.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrelloMapperTest {

    TrelloMapper trelloMapper = new TrelloMapper();

    @Test
    void shouldMapTrelloBoardDtoListToTrelloBoardList() {
        // given
        TrelloListDto trelloListDto1 = new TrelloListDto("1", "Trello List Dto 1", false);
        TrelloListDto trelloListDto2 = new TrelloListDto("2", "Trello List Dto 2", false);
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("1", "Trello Board 1", new ArrayList<>(
                List.of(trelloListDto1, trelloListDto2)
        ));
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>(List.of(trelloBoardDto));

        // when
        List<TrelloBoard> trelloBoardList = trelloMapper.mapToBoards(trelloBoardDtoList);

        // then
        System.out.println(trelloBoardList);
        assertEquals(1, trelloBoardList.size());
        assertEquals(2, trelloBoardList.get(0).getLists().size());
        assertEquals("Trello List Dto 1", trelloBoardList.get(0).getLists().get(0).getName());
        assertEquals("Trello List Dto 2", trelloBoardList.get(0).getLists().get(1).getName());
        assertFalse(trelloBoardList.get(0).getLists().get(0).isClosed());
        assertFalse(trelloBoardList.get(0).getLists().get(1).isClosed());
    }

    @Test
    void shouldMapTrelloBoardListToTrelloBoardListDto() {
        // given
        TrelloList trelloList1 = new TrelloList("1", "Trello List 1", false);
        TrelloList trelloList2 = new TrelloList("2", "Trello List 2", false);
        TrelloBoard trelloBoard1 = new TrelloBoard("1", "Trello Board 1", new ArrayList<>(
                List.of(trelloList1, trelloList2)
        ));
        List<TrelloBoard> trelloBoardList = new ArrayList<>(List.of(trelloBoard1));

        // when
        List<TrelloBoardDto> trelloBoardListDto = trelloMapper.mapToBoardsDto(trelloBoardList);

        // then
        assertEquals(1, trelloBoardListDto.size());
        assertEquals(2, trelloBoardListDto.get(0).getLists().size());
        assertEquals("1", trelloBoardListDto.get(0).getLists().get(0).getId());
        assertEquals("Trello List 1", trelloBoardListDto.get(0).getLists().get(0).getName());
        assertFalse(trelloBoardListDto.get(0).getLists().get(1).isClosed());
    }

    @Test
    void shouldMapTrelloListDtoToTrelloList() {
        // given
        TrelloListDto trelloListDto1 = new TrelloListDto("1", "Trello list 1", true);
        TrelloListDto trelloListDto2 = new TrelloListDto("2", "Trello list 2", false);
        List<TrelloListDto> trelloListDtoList = new ArrayList<>(List.of(
                trelloListDto1, trelloListDto2
        ));

        // when
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDtoList);

        // then
        assertTrue(trelloLists.get(0).isClosed());
        assertFalse(trelloLists.get(1).isClosed());
        assertEquals("1", trelloLists.get(0).getId());
        assertEquals("Trello list 2", trelloLists.get(1).getName());
        assertEquals(2, trelloLists.size());
    }

    @Test
    void shouldMapTrelloListToTrelloListDto() {
        // given
        TrelloList trelloList1 = new TrelloList("asd1", "xyz1", false);
        TrelloList trelloList2 = new TrelloList("asd2", "xyz2", false);
        List<TrelloList> trelloLists = new ArrayList<>(List.of(
                trelloList1, trelloList2
        ));

        // when
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(trelloLists);

        // then
        assertFalse(trelloListDtos.get(0).isClosed());
        assertFalse(trelloListDtos.get(1).isClosed());
        assertEquals(2, trelloListDtos.size());
        assertEquals("asd1", trelloListDtos.get(0).getId());
        assertEquals("asd2", trelloListDtos.get(1).getId());
        assertEquals("xyz1", trelloListDtos.get(0).getName());
        assertEquals("xyz2", trelloListDtos.get(1).getName());
    }

    @Test
    void shouldMapTrelloCardToTrelloCardDto() {
        // given
        TrelloCard trelloCard = new TrelloCard("test name", "test description", "test", "test id");

        // when
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        // then
        assertEquals("test name", trelloCardDto.getName());
        assertEquals("test description", trelloCardDto.getDescription());
        assertEquals("test", trelloCardDto.getPos());
        assertEquals("test id", trelloCardDto.getListId());
    }

    @Test
    void shouldMapTrelloCardDtoToTrelloCard() {
        // given
        TrelloCardDto trelloCardDto = new TrelloCardDto("test dto name",
                "test dto descr", "test dto pos", "test dto id");

        // when
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        // then
        assertEquals("test dto name", trelloCard.getName());
        assertEquals("test dto descr", trelloCard.getDescription());
        assertEquals("test dto pos", trelloCard.getPos());
        assertEquals("test dto id", trelloCard.getListId());
    }

}
