package wq.study.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class FileController {

    @RequestMapping("/file/index")
    public String fileUpload(){
        return "fileUpload";
    }
}
