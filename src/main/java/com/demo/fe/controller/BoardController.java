package com.demo.fe.controller;

import com.demo.fe.model.Board;
import com.demo.fe.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

/*
    View Controller
 */
@RequiredArgsConstructor
@RequestMapping("/boards")
@Controller
public class BoardController {

    private final BoardService boardService;




    // 게시물 상세보기 페이지 이동
    @GetMapping("/{boardId}")
    private String boardDetail(Model model,@PathVariable Long boardId){
        Board detailBoard = boardService.getDetailBoard(boardId);
        model.addAttribute("board",detailBoard);
        return "board-detail";
    }

    // 게시물 리스트
    @GetMapping("")
    private String boardList(Model model){
        List<Board> boardList = boardService.getBoardList();
        model.addAttribute("list",boardList);
        return "board-list";
    }

    // 게시물 작성페이지로 이동
    @GetMapping("/insert")
    private String goInsertPage(Model model){
        return "board-insert";
    }

    /*
        게시물 insert 후 상세보기 페이지로 이동
     */
    @PostMapping("/insertBoard")
    private String insertBoardAndDetail(Model model,@Valid Board board) {
        Board detailBoard = boardService.insertBoard(board);
        model.addAttribute("board",detailBoard);
        // 상세보기로 이동
        return "board-detail";
    }

    // 수정 페이지로 이동
    @GetMapping("/update/{boardId}")
    private String goUpdatePage (Model model,@PathVariable Long boardId){
        model.addAttribute("board",boardService.getDetailBoard(boardId));
        return "board-update";
    }

}
