package com.crude.tasks.trello.validator;

import com.crude.tasks.domain.TrelloBoard;
import com.crude.tasks.domain.TrelloList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TrelloValidatorTest {

    @Autowired
    private TrelloValidator trelloValidator;

    @Test
    void validateTrelloBoardsShouldReturnOneBoard() {
        // given
        TrelloList list1 = new TrelloList("1", "list1 name", false);
        TrelloList list2 = new TrelloList("2", "list2 name", false);
        TrelloBoard board1 = new TrelloBoard("1", "board1 name", List.of(list1));
        TrelloBoard board2 = new TrelloBoard("2", "test", List.of(list2));
        List<TrelloBoard> boardsList = List.of(board1, board2);

        // when
        List<TrelloBoard> validatedList = trelloValidator.validateTrelloBoards(boardsList);

        // then
        assertEquals(1, validatedList.size());
        assertEquals(board1.getName(), validatedList.get(0).getName());
        assertNotNull(validatedList.get(0));
    }

}
