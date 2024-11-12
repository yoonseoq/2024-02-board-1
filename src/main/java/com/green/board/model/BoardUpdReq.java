package com.green.board.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString // 값 제대로 넘어오고 있는지 toString 애노테이션 사용


public class BoardUpdReq {
    private int boardId;
    private String title;
    private String contents;
    private String writer;
}
