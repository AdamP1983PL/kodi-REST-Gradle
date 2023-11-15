package com.crude.tasks.client;

import com.crude.tasks.domain.TrelloBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Component
@RequiredArgsConstructor
public class TrelloClient {

    private final RestTemplate restTemplate;

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;
    @Value("${trello.app.key}")
    private String trelloAppKey;
    @Value("${trello.app.token}")
    private String trelloToken;
    @Value("${trello.app.username}")
    private String trelloUsername;

//    public List<TrelloBoardDto> getTrelloBoards() {
//        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(
//                trelloApiEndpoint + "/members/adampodlaski/boards"
//                        + "?key" + trelloAppKey
//                        + "@token" + trelloToken,
//                TrelloBoardDto[].class
//        );
//    }

    public List<TrelloBoardDto> getTrelloBoard(){
        URI url = getUri();

        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(url, TrelloBoardDto[].class);

//        if (boardsResponse != null) {
//            return Arrays.asList(boardsResponse);
//        }
//        return new ArrayList<>();

        return Optional.ofNullable(boardsResponse)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }

    private URI getUri() {
        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/adampodlaski/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", "id,name")
                .build()
                .encode()
                .toUri();
        return url;
    }
}
