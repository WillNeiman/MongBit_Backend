package com.MongMoong.MongBitProject.controller;

import com.MongMoong.MongBitProject.model.Member;
import com.MongMoong.MongBitProject.model.Test;
import com.MongMoong.MongBitProject.service.TestService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class HomeController {

    private final TestService testService;

    public HomeController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/")
    public ResponseEntity<String> homeView(HttpServletRequest request) {
        // HttpSession에서 SecurityContext 조회
        SecurityContext securityContext = (SecurityContext) request.getSession()
                .getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

        System.out.println("세션에 저장된 회원 정보 getPrincipal = " + securityContext.getAuthentication().getPrincipal());
        System.out.println("세션에 저장된 회원 정보 getAuthorities = " + securityContext.getAuthentication().getAuthorities());
        System.out.println("세션에 저장된 회원 정보 getDetails = " + securityContext.getAuthentication().getDetails());
        System.out.println("세션에 저장된 회원 정보 getCredentials = " + securityContext.getAuthentication().getCredentials());

        // SecurityContext에서 회원 정보 조회
        if (securityContext != null && securityContext.getAuthentication() != null) {
            Object principal = securityContext.getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                System.out.println("회원 정보: " + userDetails.getUsername());
            }
        }


        List<Test> recentTests = testService.getRecentTests(10);
        String test = "response entity 반환 테스트";
        return ResponseEntity.ok(test);
        /*
        ResponseEntity<List<Test>> response = new ResponseEntity<>(recentTests, HttpStatus.OK);
        return response;

        ResponseEntity.ok(recentTests)는 위 코드의 단축 표기법이다. 새로운 객체를 생성하는 부분(new)이 내부적으로 처리된다.
         */

    }

    @GetMapping("/list")
    public String testList(Model model){
        List<Test> testList = testService.getRecentTests(10);
        model.addAttribute("list", testList);
        return "/testlist";
    }

}
