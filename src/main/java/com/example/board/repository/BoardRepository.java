package com.example.board.repository;

import com.example.board.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer>{

    // JPQL 을 사용할 수 있다.
    // JPQL 은 SQL과 모양이 비슷하지만, SQL이 아니다.
    // JPQL 은 객체지향 언어이다.
    // JPQL에서 from 뒤에 있는 것은 엔티티 이름.
    // Board 엔티티들을 조회하라. 엔티티는 결국 table과 관계.
    @Query(value = "select b, u from Board b join User u on b.user.userId = u.userId")
    List<Board> getBoards();

    // 페이징 처리로 가지고 오는데 날짜를 desc
    Page<Board> findByOrderByRegdateDesc(Pageable pageable);

    @Query(value = "select count(b) from Board b")
    Long getBoardCount();

    // 관리자가 쓴 글만 목록을 구하는 JPQL을 작성하시오. 이게 가능할까요?
    // SQL : select * from board b, user u, user_role ur, role r where b.user_id = u.user_id
    // and u.user_id = ur.user_id and ur.role_id = r.role_id and r.name = ?;
    @Query(value = "select b, u from Board b inner join b.user u inner join u.roles r where r.name = :roleName")
    List<Board> getBoards(@Param("roleName") String roleName);

}

