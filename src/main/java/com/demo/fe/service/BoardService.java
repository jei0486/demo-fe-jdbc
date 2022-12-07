package com.demo.fe.service;

import com.demo.fe.config.WebClientConfig;
import com.demo.fe.model.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;


@RequiredArgsConstructor
@Slf4j
@Service
public class BoardService {


    final WebClientConfig webclient;


    // 게시물 작성
    public Board insertBoard(Board board){

        WebClient client = this.webclient.getWebClient();
        return client
                .post()
                .uri("/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(board)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Board>() { })
                .block();
    }

    // 게시물 리스트
    public List<Board> getBoardList(){

        /*
        exchange 메서드는 retrieve 보다 세밀한 컨트롤이 가능한 대신 memory leak 을 주의 해야 한다.
        https://github.com/reactor/reactor-netty/issues/1401
         */
        WebClient client = this.webclient.getWebClient();
        return client
                .get()
                .uri("/boards")
                .exchange()
                .flatMapMany(res -> res.bodyToFlux(Board.class))
                .collectList()
                .block();
    }

    // 게시물 상세보기
    public Board getDetailBoard(Long boardId){


        WebClient client = this.webclient.getWebClient();
        return client
                .get()
                .uri("/boards/" + boardId)
//                .uri(uriBuilder -> uriBuilder
//                        .path("/boards/{id}")
//                        .build(boardId))
                .retrieve()
                .bodyToMono(Board.class)
                .block();
    }

    // 게시물 수정
    public Board updateBoard(Long boardId, Board board){

        WebClient client = this.webclient.getWebClient();
        return client
                .put()
                .uri("/boards/" + boardId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(board)
                .retrieve()
                .bodyToMono(Board.class)
                .block();
    }

    // 게시물 삭제
    public Void deleteBoard(Long boardId){

        WebClient client = this.webclient.getWebClient();
        return client
                .delete()
                .uri("/boards/" + boardId)
                .retrieve()
                // delete() 함수 의 특성상 response는 Void.class 로 처리
                .bodyToMono(Void.class)
                .block();

    }

}
