package com.teamname.projectname.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//12. 시작페이지를 처리할 수 있게 맵핑처리
//13. 서버를 구동 후 브라우저로 확인
@Controller
public class StartController {
    @GetMapping("/")
    public String start() {
        return "index";
    }
}

//14. 정상적으로 분리작업이 완료가 되면
//index.html 파일에 불필요한 본문내용을 삭제하고
//각 작업파일의 뼈대 사용(index.html 복사본을 하나 만들어서 관리)
