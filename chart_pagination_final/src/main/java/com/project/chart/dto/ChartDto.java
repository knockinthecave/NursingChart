package com.project.chart.dto;

import com.project.chart.domain.entity.Board;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor

/* Chart에 들어갈 Dto 생성 - 계층 간 데이터 교환을 위해 자바 빈즈 생성 */
public class ChartDto {
    private Long id;
    private String patientName;
    private Long patientAge;
    private String diagnosis;
    private String nurseName;
    private String doctorName;
    private String nursingNote;
    private String viewingNote;
    private String LAB;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;


    /* ChartService에서 toEntity() 함수를 호출하여 저장함. */
    public Board toEntity() {
        Board build = Board.builder()
                .id(id)
                .patientName(patientName)
                .patientAge(patientAge)
                .diagnosis(diagnosis)
                .nurseName(nurseName)
                .doctorName(doctorName)
                .nursingNote(nursingNote)
                .viewingNote(viewingNote)
                .LAB(LAB)
                .build();
        return build;
    }

    /* ChartService와 Controller에서 사용할 ChartDto 생성자들 생성 */
    @Builder
    public ChartDto(Long id, String patientName, Long patientAge, String diagnosis, String nurseName, String doctorName, String nursingNote, String viewingNote, String LAB, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.patientName = patientName;
        this.patientAge = patientAge;
        this.diagnosis = diagnosis;
        this.nurseName = nurseName;
        this.doctorName = doctorName;
        this.nursingNote = nursingNote;
        this.viewingNote = viewingNote;
        this.LAB = LAB;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
