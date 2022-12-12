package com.example.board.service;

import com.example.board.domain.Board;
import com.example.board.domain.User;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addBoard(int userId, String title, String content) {
        User user = userRepository.findById(userId).orElseThrow();
        Board board = new Board();
        board.setUser(user);
        board.setTitle(title);
        board.setContent(content);
        board.setRegdate(LocalDateTime.now());
        boardRepository.save(board);
    }

    @Transactional(readOnly = true) // select할때는
    public long getTotalCount() {
        return boardRepository.getBoardCount();
    }

    @Transactional(readOnly = true)
    public List<Board> getBoards(int page) { // page : 0 - 첫번째페이지.
        Pageable pageable = PageRequest.of(page, 10);
        return boardRepository.findByOrderByRegdateDesc(pageable).getContent();
    }

    @Transactional
    public Board getBoard(int boardId) {
        return getBoard(boardId, true);
    }

    // updateViewCnt가 true면 글의 조회수를 증가, false면 글의 조회수를 증가하지 않도록 한다.
    @Transactional
    public Board getBoard(int boardId, boolean updateViewCnt){
        Board board = boardRepository.findById(boardId).orElseThrow();
//        board.getUser(); // lazy loading
        if(updateViewCnt) {
            board.setViewCnt(board.getViewCnt() + 1); // 해당 메소드가 종료될때 update가 실행된다.
        }
        return board;
    }

    @Transactional
    public void deleteBoard(int userId, int boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        if (board.getUser().getUserId() == userId) {
            boardRepository.delete(board);
        }
    }

    @Transactional
    public void deleteBoard(int boardId) {
        boardRepository.deleteById(boardId);
    }

    @Transactional
    public void updateBoard(int boardId, String title, String content) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        board.setTitle(title);
        board.setContent(content);
    }
}
