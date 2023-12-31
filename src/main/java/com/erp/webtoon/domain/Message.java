package com.erp.webtoon.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Message {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "msg_id")
    private Long id;

    private String msgType;     // 메세지 타입

    private String content;     // 메세지 내용

    private Long refId; // 참조 ID

    private char stat; // 상태값

    private String programId;    // 참조 프로그램ID

    @CreatedDate
    private LocalDateTime createdDate;  // 발신일시

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "user_id" , name = "rcv_user_id")
    private User rcvUser;   // 수신자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "user_id", name = "send_user_id")
    private User sendUser;    // 발신자

    @Builder
    public Message(Long id, String msgType, String content, Long refId, char stat, String programId, User rcvUser, User sendUser) {
        this.id = id;
        this.msgType = msgType;
        this.content = content;
        this.refId = refId;
        this.stat = stat;
        this.programId = programId;
        this.rcvUser = rcvUser;
        this.sendUser = sendUser;
    }

    public void changeStat(char stat) {
        this.stat = stat;
    }
}
