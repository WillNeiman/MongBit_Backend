package com.MongMoong.MongBitProject.service;

import com.MongMoong.MongBitProject.model.Answer;
import com.MongMoong.MongBitProject.model.Member;
import com.MongMoong.MongBitProject.model.Question;
import com.MongMoong.MongBitProject.model.TestResult;
import com.MongMoong.MongBitProject.repository.TestRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestServiceTest {

    @Autowired
    private TestRepository testRepository;
    @Autowired
    private TestService testService;

    private com.MongMoong.MongBitProject.model.Test test;
    private Question question;
    private TestResult testResult;

    @BeforeEach
    void setUp() {

//        Answer answer1 = Answer.builder()
//                .index(5)
//                .property("I")
//                .score(-6)
//                .answer("first answer")
//                .build();
//
//
//        Answer answer2 = Answer.builder()
//                .index(2)
//                .property("E")
//                .score(3)
//                .answer("2 answer")
//                .build();
//
//
//        Answer answer3 = Answer.builder()
//                .index(6)
//                .property("J")
//                .score(0)
//                .answer("3 answer")
//                .build();

//        Question mountain1 = Question.builder()
//                .id("mountain1")
//                .index(1)
//                .property("I")
//                .question("등산은 무슨 집에서 쉬자")
//                .build();
//
//        Question mountain2 = Question.builder()
//                .id("mountain2")
//                .index(2)
//                .property("E")
//                .question("그래도 나가보자. 산행의 고통은 동료들과 함께*^^*")
//                .build();
//
//        Question mountain3 = Question.builder()
//                .id("mountain3")
//                .index(3)
//                .property("E")
//                .question("유명한 산으로 가서 새로운 사람들은 만나야지")
//                .build();
//
//        Question mountain4 = Question.builder()
//                .id("mountain4")
//                .index(4)
//                .property("S")
//                .question("저번에 가봤던 산에 다시 갈까")
//                .build();
//
//        Question mountain5 = Question.builder()
//                .id("mountain5")
//                .index(5)
//                .property("N")
//                .question("이렇게 등산하다 보면 엄홍길 주니어가 되겠지?")
//                .build();
//
//        Question mountain6 = Question.builder()
//                .id("mountain6")
//                .index(6)
//                .property("N")
//                .question("사진은 숲 전체가 보이도록 들어가기 전에 찍자")
//                .build();
//
//        Question mountain7 = Question.builder()
//                .id("mountain7")
//                .index(7)
//                .property("T")
//                .question("근처의 누가 넘어졌다! 치료부터!")
//                .build();
//
//        Question mountain8 = Question.builder()
//                .id("mountain8")
//                .index(8)
//                .property("F")
//                .question("이렇게 사람들이 많은 곳에서 넘어지면 마음도 다쳤을 거다. 위로부터 해줘야지.")
//                .build();
//
//        Question mountain9 = Question.builder()
//                .id("mountain9")
//                .index(9)
//                .property("T")
//                .question("t질문~ 창의력 부족")
//                .build();
//
//        Question mountain10 = Question.builder()
//                .id("mountain10")
//                .index(10)
//                .property("P")
//                .question("하지만 근처에 다른 풍경이 더 눈에 들어온다. 들렸다 갈까?")
//                .build();
//
//        Question mountain11 = Question.builder()
//                .id("mountain11")
//                .index(11)
//                .property("J")
//                .question("미리 찾아놓은 코스를 따라 근처 맛집까지 다녀온다.")
//                .build();
//
//        Question mountain12 = Question.builder()
//                .id("mountain12")
//                .index(12)
//                .property("J")
//                .question("맛집이 문을 닫았다! 이럴때를 위해 다른 맛집을 알아놨지!")
//                .build();
//
//        TestResult testResult1 = TestResult.builder()
//                .id("testResult1")
//                .result("ISTJ")
//                .title("원리원칙 계획이 짱이다.")
//                .content("ISTJ 내용")
//                .imageUrl("ISTJ 이미지 올자리")
//                .build();
//
//        TestResult testResult2 = TestResult.builder()
//                .id("testResult2")
//                .result("ISFJ")
//                .title("당신은 원칙주의자입니까?")
//                .content("근데 게으르군요")
//                .imageUrl("ISFJ 이미지 올자리")
//                .build();
//
//        TestResult testResult3 = TestResult.builder()
//                .id("testResult3")
//                .result("INFJ")
//                .title("INFJ 제목")
//                .content("INFJ 내용")
//                .imageUrl("INFJ 이미지 올자리")
//                .build();
//
//        TestResult testResult4 = TestResult.builder()
//                .id("testResult4")
//                .result("INTJ")
//                .title("INTJ 제목")
//                .content("INTJ 내용")
//                .imageUrl("INTJ 이미지 올자리지")
//                .build();
//        TestResult testResult5 = TestResult.builder()
//                .id("testResult5")
//                .result("ISTP")
//                .title("ISTP 제목")
//                .content("ISTP 내용")
//                .imageUrl("ISTP 이미지 올자리")
//                .build();
//        TestResult testResult6 = TestResult.builder()
//                .id("testResult6")
//                .result("ISFP")
//                .title("ISFP 제목")
//                .content("ISFP 내용")
//                .imageUrl("ISFP 이미지 올자리")
//                .build();
//        TestResult testResult7 = TestResult.builder()
//                .id("testResult7")
//                .result("INFP")
//                .title("INFP 제목")
//                .content("INFP 내용")
//                .imageUrl("INFP 이미지 올자리")
//                .build();
//        TestResult testResult8 = TestResult.builder()
//                .id("testResult8")
//                .result("INTP")
//                .title("INTP 제목")
//                .content("INTP 내용")
//                .imageUrl("INTP 이미지 올자리")
//                .build();
//
//        TestResult testResult9 = TestResult.builder()
//                .id("testResult9")
//                .result("ESTP")
//                .title("ESTP 제목")
//                .content("ESTP 내용")
//                .imageUrl("ESTP 이미지 올자리")
//                .build();
//
//        TestResult testResult10 = TestResult.builder()
//                .id("testResult10")
//                .result("ESFP")
//                .title("ESFP 제목")
//                .content("ESFP 내용")
//                .imageUrl("ESFP 이미지 올자리")
//                .build();
//
//        TestResult testResult11 = TestResult.builder()
//                .id("testResult11")
//                .result("ENFP")
//                .title("ENFP 제목")
//                .content("ENFP 내용")
//                .imageUrl("ENFP 이미지 올자리")
//                .build();
//
//        TestResult testResult12 = TestResult.builder()
//                .id("testResult12")
//                .result("ENTP")
//                .title("ENTP 제목")
//                .content("ENTP 내용")
//                .imageUrl("ENTP 이미지 올자리")
//                .build();
//
//        TestResult testResult13 = TestResult.builder()
//                .id("testResult13")
//                .result("ESTJ")
//                .title("ESTJ 제목")
//                .content("ESTJ 내용")
//                .imageUrl("ESTJ 이미지 올자리")
//                .build();
//
//        TestResult testResult14 = TestResult.builder()
//                .id("testResult14")
//                .result("ESFJ")
//                .title("ESFJ 제목")
//                .content("ESFJ 내용")
//                .imageUrl("ESFJ 이미지 올자리")
//                .build();
//
//        TestResult testResult15 = TestResult.builder()
//                .id("testResult15")
//                .result("ENFJ")
//                .title("ENFJ 제목")
//                .content("ENFJ 내용")
//                .imageUrl("ENFJ 이미지 올자리")
//                .build();
//
//        TestResult testResult16 = TestResult.builder()
//                .id("testResult16")
//                .result("ENTJ")
//                .title("ENTJ 제목")
//                .content("ENTJ 내용")
//                .imageUrl("ENTJ 이미지 올자리")
//                .build();
//
//        test = com.MongMoong.MongBitProject.model.Test
//                .builder()
//                .title("등산으로 알아보는 인성")
//                .content("초록초록")
//                .questions(List.of(mountain1,mountain2, mountain3,mountain4,mountain5,mountain6, mountain7,mountain8,mountain9,mountain10, mountain11,mountain12))
//                .results(List.of(testResult1, testResult2, testResult3,testResult4,testResult1, testResult2, testResult3,testResult4,testResult5, testResult6, testResult7,testResult8,testResult9, testResult10, testResult11,testResult12))
//                .createDate(LocalDateTime.now())
//                .imageUrl("https://images.unsplash.com/photo-1617361194384-1852022fe186?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1915&q=80")
//                .playCount(0)
//                .build();
//
//    }

//    @Test
//    @DisplayName(">>> test create section")
//    void createTest(){
//        com.MongMoong.MongBitProject.model.Test createdTest = testService.createTest(test);
//    }


//    @Test
//    @DisplayName(">>> test get section")
//    void getTest() {
//        // given
//        String testId = "3L";
//
//        // when
//        com.MongMoong.MongBitProject.model.Test result = testService.getTest(testId).orElse(null);
//
//        // then
//        Assertions.assertThat(result).isInstanceOf(com.MongMoong.MongBitProject.model.Test.class);
//        Assertions.assertThat("~Test Content~").isEqualTo(result.getContent());
//        Assertions.assertThat("~test image url~").isEqualTo(result.getImageUrl());
//        System.out.println("------------------------------------------------------------");
//        System.out.println("콘솔확인: "+result.toString());
//        //System.out.println("result.getQuestions(): "+result.getQuestions().get(0));
//        //Assertions.assertThat("I").isEqualTo(result.getQuestions().get(0).getProperty());
//    }

//    @Test
//    @DisplayName(">>>question get section")
//    void getQuestions() {
//        // given
//        String testId = "3L";
//
//        // when
//        List<Question> questions = testService.getQuestions(testId);
//
//        // then
//        Assertions.assertThat(questions).contains(question);
//    }

//    @Test
//    @DisplayName(">>>test result get section")
//    void getTestResults() {
//        // given
//        String testId = "3L";
//        String resultValue = "ENFP";
//
//        // when
//        Optional<TestResult> results = testService.getTestResult(testId, resultValue);
//
//        // then
//        assertTrue(results.isPresent());
//        assertEquals(resultValue, results.get().getResult());
//    }



    /*
            // Save the Test object to the database
        testRepository.save(test);

        // Get the Test object from the database
        Test savedTest = testRepository.findById(test.getId()).orElse(null);

        // Assertions
        assertEquals(test.getId(), savedTest.getId());
        assertEquals(test.getTitle(), savedTest.getTitle());
        assertEquals(test.getContent(), savedTest.getContent());
        assertEquals(test.getQuestions().size(), savedTest.getQuestions().size());
        assertEquals(test.getResults().size(), savedTest.getResults().size());
        assertEquals(test.getCreateDate(), savedTest.getCreateDate());
        assertEquals(test.getImageUrl(), savedTest.getImageUrl());
        assertEquals(test.getPlayCount(), savedTest.getPlayCount());
     */


    }
}