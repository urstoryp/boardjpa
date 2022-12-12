package com.example.board.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="board")
@NoArgsConstructor
@Setter
@Getter
public class Board {
    @Id
    @Column(name="board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer boardId;

    @Column(length = 100)
    private String title;

    @Lob
    private String content; // text type

    private int viewCnt;

    @CreationTimestamp // 현재시간이 저장될 때 자동으로 생성.
    private LocalDateTime regdate;

    // 글 작성자.
    @ManyToOne(fetch = FetchType.LAZY) // 게시물 N --- 1 사용자, FetchType.EAGER 무조건데이터를 가지고와라.
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "Board{" +
                "boardId=" + boardId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", viewCnt=" + viewCnt +
                ", regdate=" + regdate +
                '}';
    }
}

/*
'board_id', 'int', 'NO', 'PRI', NULL, 'auto_increment'
'title', 'varchar(100)', 'NO', '', NULL, ''
'content', 'text', 'YES', '', NULL, ''
'user_id', 'int', 'NO', 'MUL', NULL, ''
'regdate', 'timestamp', 'YES', '', 'CURRENT_TIMESTAMP', 'DEFAULT_GENERATED'
'view_cnt', 'int', 'YES', '', '0', ''

 */