package com.green.board;

import com.green.board.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
/*
src>main>resource>mappers 폴더 아래에 이름이 같은 xml라이를 안
(관리상 용이하게 같은이름을 쓰는게 좋다)

xml + interface 파일을 이용해서 implements 한  class 파일을 만들 것이고 빈등록까지 해준다
스프링 컨테이너가 빈등록한 class 파일을 객체화 한다. 여기서 만든 주소값을 BoardService객체화
 할떄 DI해준다

 insert, delete , update의 리턴타입은  int 하묜 됨
 */

@Mapper
public interface BoardMapper {
    // 임플리먼트한것을 마이바티스 프레임워크가 만들어낸다.
    int insBoard(BoardInsReq p);
    List<BoardSelRes> selBoardList();
    BoardSelOneRes selBoardOne(int p);
    int updBoard(BoardUpdReq p);
     // 모델에서 만든것을 끌고옴 정보를 수정하는지를
    int delBoard(BoardDelReq p);
}
