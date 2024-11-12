package com.green.board;

import com.green.board.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/*
    @Service - 빈등록, 서비스가 로직처리 담당, 로직처리가 있다면 여기서 처리한다.
    없으면 연결작업만 연결작업이 controller 와 persistence db연결

    빈등록 : 스프링 컨테이너에게 객체생성을 대리로  맡기는것,
    기본적으로 싱글톤으로 객체화
    싱글톤: 프로젝트에서 객체생성 하나만 되게끔 하는것
 */
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper mapper;
    //DI받을수 있게 - 나는 그냥 생성자 만들래
    // final을 붙여서 외부에서 주입받는 생성자를 만듦

    /*
    public BoardService(BoardMapper mapper) {
        this.mapper = mapper;
    }
빈등록이 되어있는것이 먼저 등록되고 객체화 먼저되야 함
     */

    public int insBoard(BoardInsReq p) {
        return mapper.insBoard(p);
    }

    public List<BoardSelRes> selBoardList() {
        return mapper.selBoardList();
    }

    public BoardSelOneRes selOneRes(int p) {
        return mapper.selBoardOne(p);
    }

    public int updBoard(BoardUpdReq p) {
        return mapper.updBoard(p);
    }

    public int delBoard(BoardDelReq p) {
        return mapper.delBoard(p);
    }
}


