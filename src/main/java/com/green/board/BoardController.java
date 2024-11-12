package com.green.board;

import com.green.board.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    Controller 의 역할: 요청(request)을 받고 응답(response)을 처리하는 개체만들기
    로직처리는 하지 않는다.

    Annotation (애노테이션)
    @Controller - 응답을 html (데이터로 만든 화면을 응답)
    @RestController - 응답을 json (데이터만 응답)
    @RequestMapping - URL과 Controller or Method 맵핑
                      class에 RequestMapping 전체 메소드 주소가 매핑

    @PostMapping
    요청과 응답은 (Header, body)로 이루어져 있다.
    header에는 목적지url, 방식 인코딩 등등
    body 에는 값 데이터가 담겨져있다.

    브라우저를 통해 요청을 보낼때 URL과 method 를 함께 요청을 보낸다.
    브라우저의 주소창에 주소값을 적고 엔터는 URL + GET 방식 + 데이터보내는 방식(key/Value)으로 요청을 보냄
    get방식과 post방식의 큰 차이는 데이터를 보낼때 보여지나 안보여지나의 차이가있다

    1. 쿼리스트링 방식 (파라미터라고 부름), url에 데이터를 포함하는 방식
    2. body에 담아서 보내는 방식

    쿼리스트링 모양 : url + 쿼리스트링(?로 시작 key=value, 여러개라면 & 구분)
                    www.naver.com?홍길동&age=12&height=172.5
                   https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&ssc=tab.nx.all&query=aespa&oquery=%EC%97%90%EC%8A%A4%ED%8C%8C&tqi=iza1EsqVN8wssR05Zw4ssssstGC-160526

    대용량의 데이터를 보내야 할 때도 body에 데이터를 담아서 보낸다. url은 길이제한이 있기 때문에
    url에 데이터를 포함하는 쿼리스트링은 대용량을 보낼 수 없다.

    JSON은 (JavaScript Object Notation) : 자바스크립트에서 객체을 만들때 사용하는 문법을 이용하여
                                        데이터를 표현하는 포멧형식. key와 Value로 이루어져 있음

    예를들어 이름은 홀길동 나이는 22살 키는 178.2데이터를 JSON으로 표현을 하면

    {
        "name" : "홍길동",
        "age" : 22,
        "height": 178.2
    }

    이렇게  객체를 표현하는 문자열이다
    {}는 객체를 의미하고 []는 배열을 의미한다 ""문자열, 숫자횽은 ""없이표현
    key는 무적권 ""로 감싼다



    Restful 이전에는 get post방식밖에 없었음.
    get방식은 주로 쿼리스트링 방식을 사용하고- 데이터를 읽어올때(간혹 삭제때도 사용함)
    post방식은 body에 데이터를 담아서 보내는 방식을 사용하였다.
    일단 데이터가 있었을때는 get방식이 처리 속도가 빠름, 데이터 처리가 아닌 단순화면을 띄울때도
    get방식을 사용함

    예를들어 로그인을 하는 상황에서 로그인을 하는 화면이 띄워져야 한다

    작업 (1) 로그인 하는 화면은 (get)/login 이렇게 표현하겠다.

    작업 (2) 그다음 아이디랑 비번을 작성하고 로그인버튼을 누르면 (get)/login 이렇게 요청을 보냈다.
             아이디,비번은 body에 담아서 요청을 보냈다.

    url은 같은데 메소드(get/post)로 작업을 구분 . (마치 if문 처럼)

    위 작업은 2가지 밖에 없었기 때문에 같은 주소값으로 메소드를 구분 할 수 있었다
    그런데 CRUD를 해야하는 상황에서는 작업 구분 주소값을 해야했었다.

    (get)/board - 게시판 리스트 보기 화면
    (get)/board_detail - 게시판 글 하나보기 화면
    (get)/board_create - 게시판 글 등록하는 화면
    (post)/board_create - 게시판 글 등록하는 작업 처리
    (get)/board_modify - 게시판 글 수정하는 화면
    (post)/board_modify - 게시판 글 수정하는 작업처리
    (get/post)/board_delete - 게시판 글 삭제하는 작업 처리

    URL 과 메소드를 함께 요청을 보낸다

    첫페이지(index화면)를 띄울 때 소프트웨어가 모두 다운로드됨.
    화면을 띄울 수 있는 소프트웨어가 모두 다운로드됨.
    화면이동은 모두 FE코드가 작동하는것. 화면 만들기는 클라이언트 리소스를 사용하여 그린다.
    (rendering) 화면마다 데이터가 필요하면 BE요청을 한다. 누가? FE작업코드가 요청을 보낸다
    그래서 BE는 이제 화면은 신경쓰지 않아도 된다. FE코드가 요청한 작업에 응답만 잘 해주면 된다.

    client 리소스: client 즉 요청을 보낸 컴퓨터의 자원을 사용한다.(cpu, ram, 하드디스크 등등)

    Restful 방식은 화면은 없고 작업만 신경쓰면 된다
    restful 이해가 필요. 요청은 크게 네가지로 나뉘어진다

    1.POST : Create - Insert 작업
    2.GET :
    3.PUT / PATCH
    4.DELETE

    post, put/patch 방식은 주로 데이터를 body에 담아서 보내고
    get,delete 방식은 query string or path variable 을 사용해서 데이터를 보냄

    FE가 BE한테  url + method + 데이터요청(request)을 하고 BE는 JSON으로 응답(response)

    (post) /board - 글 등록
    (get) /board- 리스트 데이터(row가 여러개)
    (get) /board/ 위에거랑 엄연히 다른 주소값
    (get) /board?page=1 - (get) /board?aaa=2 - 둘다 페이지가 같은 요청이다. url이 같으면 같은요청
    (get) /board/1 튜플 1개 데이터 (row가 한줄),1은 pk (하나만 가져오는거면 pk를 써서 ), path variable
    뒤에 숫자가 있든 없든 끝에 / 만 붙어도 위랑 아래는 다른요청
    (put/ patch) /board -  글 수정
    (delete)/ board - 글 삭제 (Path Variable or Query String 으로 pk값 전달)

 */
@RequiredArgsConstructor// 애노테이션을 붙이면 private 전용 생성자가 자동으로 만들어짐
/*
    public  BoardController (BoardService boardService){
        this.boardService=boardService;
    }

    di를 받고싶으면 @ 빈등록을 해야한다.
 */


// final 붙은 멤머필드 di받을 수 있게
// 에노테이션 생략하면 생성자를 직접만듦

@RestController // 빈등록 + 컨트롤러 임명, 빈등록은 스프링 컨테이너가 직접 객체화를 한다 기
@RequestMapping("/board") // 이렇게 주소값 매핑하는게 중요
public class BoardController {
    private final BoardService boardService;

    // 빈등록만  해놓으면 객체화가 되어서 객체화가 된 주솟값을 위에코드처럼 di받을 수 있다.
    // 타이틀 컨텐츠 라이터 만  필요, 아이디는 자동으로 값 증가하기 때문이다.
    // 모델에 req질문 res답면 수정이 일어 났을때 독립성 보장위해 분리해서 사용


    // insert (create)
    @PostMapping// (post)/ board 요청이 오면 이 메소드가 응답담당자
    //@PostMapping("/board"):@RequestMapping("/board") 이 코드가 없으면 url을 작성해줘야한다
    //@RequestBody 는 요청이 올때 데이터가 JSON 형태로 오니까 맞춰서 데이터를 받자
    public int insBoard(@RequestBody BoardInsReq p) {
        System.out.println();
        System.out.println(p);
        return boardService.insBoard(p);
    }


    // 객체>JSON 바꾸는 직렬화 작업
    @GetMapping
    public List<BoardSelRes> selBoardList() {
        return boardService.selBoardList();
    }

    // 여기 적혀있는 값을 파라미터에 보내준다
    @GetMapping("{boardId}")
    public BoardSelOneRes selOneRes(@PathVariable int boardId) {
        return boardService.selOneRes(boardId);
    }
    @PutMapping
    public int updBoard(@RequestBody BoardUpdReq p) {
        System.out.println(p);
        return boardService.updBoard(p);
    }
    @DeleteMapping
    public int delBoard(@ModelAttribute BoardDelReq p) {
        System.out.println(p);
        return boardService.delBoard(p);
    }

}