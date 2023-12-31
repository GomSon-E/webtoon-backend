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
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "atd_id")
    private Long id;

    private int attendMonth; // 기준월

    private String attendDate;  //  기준일

    private String attendType;  // 근태타입

    @CreatedDate
    private LocalDateTime attendTime;  // 시간

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Attendance(int attendMonth, String attendDate, String attendType, LocalDateTime attendTime, User user) {
        this.attendMonth = attendMonth;
        this.attendDate = attendDate;
        this.attendType = attendType;
        this.attendTime = attendTime;
        this.user = user;
    }
}
