package com.project.chart.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

import com.project.chart.domain.entity.Board;
import com.project.chart.domain.repository.ChartRepository;
import com.project.chart.dto.ChartDto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;

@Service
public class ChartService {
    private static ChartRepository chartRepository;

    /* ChartRepostitory에서 검색 후 페이징 처리를 하도록 하는 메소드
    환자명, 담당간호사명, 진단명, 담당의사명을 kw(키워드)로 받아서 키워드에 있는 내용들만 출력하게 함.*/
    private Specification<Board> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Board> b, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);

                return cb.or(cb.like(b.get("patientName"), "%" + kw + "%"),
                        cb.like(b.get("patientName"), "%" + kw + "%"),
                        cb.like(b.get("nurseName"), "%" + kw + "%"),
                        cb.like(b.get("diagnosis"), "%" +kw + "%"),
                        cb.like(b.get("doctorName"), "%" + kw + "%"));

            }
        };
    }

    public ChartService(ChartRepository chartRepository) {
        this.chartRepository = chartRepository;
    }

    /* ChatDto에서 엔티티를 불러와 /post/{id} 요청에 맞게 id를 primarykey로 하여 mysql DB에 저장을 하는 메소드 */
    @Transactional
    public static Long savePost(ChartDto chartDto) {
        return chartRepository.save(chartDto.toEntity()).getId();
    }

    /* DB에서 Board로 보내주는 데이터들을 받아 모든 List들을 불러와 chartDtoList에 추가하여 저장. 후에 불러와 메인페이지에서 출력함. */
    @Transactional
    public List<ChartDto> getChartList() {
        List<Board> chartList = chartRepository.findAll();
        List<ChartDto> chartDtoList = new ArrayList<>();

        for(Board board : chartList) {
            ChartDto chartDto = ChartDto.builder()
                    .id(board.getId())
                    .patientName(board.getPatientName())
                    .patientAge(board.getPatientAge())
                    .diagnosis(board.getDiagnosis())
                    .nurseName(board.getNurseName())
                    .doctorName(board.getDoctorName())
                    .nursingNote(board.getNursingNote())
                    .viewingNote(board.getViewingNote())
                    .LAB(board.getLAB())
                    .createdDate(board.getCreatedDate())
                    .build();
            chartDtoList.add(chartDto);

        }
        return chartDtoList;
    }

    /* 차트 상세 조회를 할때 get메소드를 통해 필요한 데이터들을 불러온다. */
    @Transactional
    public ChartDto getPost(Long id) {
        Board board = chartRepository.findById(id).get();

        ChartDto chartDto = ChartDto.builder()
                .id(board.getId())
                .patientName(board.getPatientName())
                .patientAge(board.getPatientAge())
                .diagnosis(board.getDiagnosis())
                .nurseName(board.getNurseName())
                .doctorName(board.getDoctorName())
                .nursingNote(board.getNursingNote())
                .viewingNote(board.getViewingNote())
                .LAB(board.getLAB())
                .createdDate(board.getCreatedDate())
                .build();
        return chartDto;

    }

    /* 차트를 삭제하도록 하는 메소드 */
    @Transactional
    public void deletePost(Long id) {
        chartRepository.deleteById(id);
    }

    /* 메인페이지를 불러올때 생성날짜가 빠른 순으로 불러오고, 페이징처리를 하되, Specification<Board>에 의해 키워드가 있으면 키워드에 맞게 페이징한다. */
    public Page<Board> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("createdDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<Board> spec = search(kw);
        return this.chartRepository.findAll(spec, pageable);
    }
}
