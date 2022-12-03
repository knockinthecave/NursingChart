package com.project.chart.controller;

import com.project.chart.domain.entity.Board;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

import com.project.chart.dto.ChartDto;
import com.project.chart.service.ChartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/* MVC모델에서 컨트롤러 역할을 함 */
@Controller
public class ChartController {
    private ChartService chartService;

    public ChartController(ChartService chartService) {
        this.chartService = chartService;
    }

    /* 메인 페이지 - 첫번째 RequestParam에서는 페이징에 관한 정보를 불러옴. 두번째 RequestParam에서는 검색시 kw를 불러옴.*/
    @GetMapping("/")
    public String list(Model model, @RequestParam(value="page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw) {

        Page<Board> paging = this.chartService.getList(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        //List<ChartDto> chartDtoList = chartService.getChartList();
        //model.addAttribute("postList", chartDtoList);
        return "board/list.html";
    }

    /* 차트작성 메소드 - 차트를 작성한다. */
    @GetMapping("/post")
    public String post() {
        return "board/post.html";
    }

    /* 작성한 차트의 정보들을 ChartService.savePost를 통해 저장한다. */
    @PostMapping("/post")
    public String write(ChartDto chartDto) {
        ChartService.savePost(chartDto);
        return "redirect:/";

    }

    /* 차트를 상세 조회하는 페이지 */
    @GetMapping("/post/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        ChartDto chartDto = chartService.getPost(id);
        model.addAttribute("post", chartDto);
        return "board/detail.html";
    }

    /* 차트를 상세 조회 후 수정하는 메소드 */
    @GetMapping("/post/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        ChartDto chartDto = chartService.getPost(id);
        model.addAttribute("post", chartDto);
        return "board/edit.html";
    }

    /* 수정 후 수정된 내용을 데이터베이스에 저장한다. */
    @PutMapping("/post/edit/{id}")
    public String update(ChartDto chartDto) {
        chartService.savePost(chartDto);
        return "redirect:/";
    }

    /* 차트를 삭제하는 메소드 */
    @DeleteMapping("/post/{id}")
    public String delete(@PathVariable("id") Long id) {
        chartService.deletePost(id);
        return "redirect:/";
    }
}