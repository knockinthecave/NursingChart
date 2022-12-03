package com.project.chart.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
public class Board {

    /* 엔티티를 만들고 생성하여 테이블 형태로 저장할 수 있도록 함 */
    /* Primary Key 생성 */
    @Id
    @GeneratedValue
    private Long id;

    /* 환자명 Column 생성 */
    @Column(length = 10, nullable = false)
    private String patientName;

    /* 환자 나이 Column 생성 */
    @Column
    private Long patientAge;

    /* 진단명 Column 생성 */
    @Column(length = 10, nullable = false)
    private String diagnosis;

    /* 담당간호사명 Column 생성 */
    @Column(length = 10, nullable = false)
    private String nurseName;

    /* 담당의사명 Column 생성 */
    @Column(length = 10, nullable = false)
    private String doctorName;

    /* 간호일지 Column 생성 */
    @Column(columnDefinition = "TEXT", nullable = false)
    private String nursingNote;

    /* 관찰일지 Column 생성 */
    @Column(columnDefinition = "TEXT", nullable = false)
    private String viewingNote;

    /* LAB검사결과 Column 생성 */
    @Column(columnDefinition = "TEXT", nullable = false)
    private String LAB;

    /* 생성날짜 Column 생성 */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    /* 마지막 수정 날짜 Column 생성 */
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    /* 위에서 생성한 Column들을 통해 테이블 생성 */
    @Builder
    public Board(Long id, String patientName, Long patientAge, String diagnosis, String nurseName, String doctorName, String nursingNote, String viewingNote, String LAB) {
        this.id = id;
        this.patientName = patientName;
        this.patientAge = patientAge;
        this.diagnosis = diagnosis;
        this.nurseName = nurseName;
        this.doctorName = doctorName;
        this.nursingNote = nursingNote;
        this.viewingNote = viewingNote;
        this.LAB = LAB;
    }
}
