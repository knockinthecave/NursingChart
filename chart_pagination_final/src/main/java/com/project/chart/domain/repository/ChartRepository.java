package com.project.chart.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.chart.domain.entity.Board;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChartRepository extends JpaRepository<Board, Long> {

    /* findAll을 통해 모든 데이터들을 불러오고 Pageable을 통해 페이징 처리 */
    Page<Board> findAll(Pageable pageable);

    /* Specification<Board>를 통해 kw(키워드)를 받아서 키워드와 일치하는 부분들만 불러와 페이징 처리 */
    Page<Board> findAll(Specification<Board> spec, Pageable pageable);



}
