package com.MongMoong.MongBitProject.aspect;

import com.MongMoong.MongBitProject.config.TokenProvider;
import com.MongMoong.MongBitProject.exception.TokenVerificationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
HandlerInterceptor 인터페이스에는 3가지 메소드가 선언되어 있다.
preHandle :
컨트롤러의 메소드가 실행되기 전에 호출. 인증 검사, 로깅 등의 작업을 수행하며,
이 메소드가 false를 리턴하면 해당 요청은 더 이상 진행되지 않고 바로 응답을 반환한다.

postHandle :
컨트롤러의 메소드가 실행된 이후, 그리고 뷰를 렌더링하기 전에 호출.
컨트롤러에서 처리한 결과를 가공하는 작업 등을 수행한다.

afterCompletion :
모든 처리가 끝나고, 응답이 클라이언트에게 전송된 이후에 호출.
주로 자원 해제 등의 작업을 수행한다.
 */

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final TokenProvider tokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String token = tokenProvider.resolveToken(request);
            tokenProvider.validateToken(token);
            System.out.println("토큰 검증 완료");
            return true;
        } catch (JWTDecodeException ex) {
            response.setContentType("text/plain; charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(ex.getMessage());
            return false;
        } catch (TokenExpiredException ex) {
            response.setContentType("text/plain; charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(ex.getMessage());
            return false;
        } catch (TokenVerificationException ex) {
            response.setContentType("text/plain; charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(ex.getMessage());
            return false;
        }
    }
}
