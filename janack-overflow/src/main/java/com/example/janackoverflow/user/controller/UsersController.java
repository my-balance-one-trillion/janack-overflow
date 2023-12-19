package com.example.janackoverflow.user.controller;

import com.example.janackoverflow.global.security.DTO.MailDTO;
import com.example.janackoverflow.global.security.Service.MailService;
import com.example.janackoverflow.global.security.auth.NowUserDetails;
import com.example.janackoverflow.user.domain.request.UsersRequestDTO;
import com.example.janackoverflow.user.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {
    private final UsersService usersService;

    @Autowired
    private MailService mailService;

    public UsersController(UsersService usersService){
        this.usersService = usersService;
    }

//    회원가입
    @PostMapping("/signup")
    public ResponseEntity createUser(@RequestBody UsersRequestDTO usersRequestDTO){

        if(usersRequestDTO.getEmail().isEmpty()
        || usersRequestDTO.getPassword().isEmpty()
        || usersRequestDTO.getName().isEmpty()
        || usersRequestDTO.getNickname().isEmpty()
        || usersRequestDTO.getDigit().isEmpty()
        || usersRequestDTO.getBirth() == null){
            return new ResponseEntity<>("필수 입력 항목입니다", HttpStatus.FORBIDDEN);
        }

        if (usersService.isDuplicatedNick(usersRequestDTO)) { //닉네임 중복확인
            return new ResponseEntity<>("중복되는 닉네임입니다", HttpStatus.FORBIDDEN);
        } else if(usersService.isDuplicatedEmail(usersRequestDTO)) { //이메일 중복확인
            return new ResponseEntity<>("중복되는 이메일입니다", HttpStatus.FORBIDDEN);
        } else {
            usersRequestDTO.setRole("USER"); //역활
            usersRequestDTO.setStatus("01"); //상태
            usersRequestDTO.setProfileImage("default.png"); //프로필 이미지

            usersService.createUser(usersRequestDTO);

            return new ResponseEntity<>("성공적으로 생성", HttpStatus.OK);
        }

    }

//    이메일 인증
    @PostMapping("/mailPass")
    public ResponseEntity mailPass(@RequestBody UsersRequestDTO usersRequestDTO){

        //메일 주소 유효성 검사
        try {
            usersService.findByEmail(usersRequestDTO.getEmail());
        } catch (Exception e){
            return new ResponseEntity<>("입력하신 메일과 일치하는 회원이 없습니다.", HttpStatus.FORBIDDEN);
        }

        //입력한 메일이 유효하면, 임시 번호를 생성하고
        int randNum = (int) (Math.random() * (9999 - 1000 + 1)) + 1000;
        String tempPass = "temp" + randNum;

        //DB에 업데이트 (서비스에서 암호화)
        usersService.updateRandomPass(usersRequestDTO, tempPass);

        //발급한 임시 번호를 입력받은 메일로 발송
        MailDTO emailMessage = MailDTO.builder()
                .to(usersRequestDTO.getEmail()) //작성한 email
                .subject("임시 비밀번호 입니다")
                .message("<html>\n" +
                        "<head></head>\n" +
                        "<body>\n" +
                        "<div \n" +
                        "style=\"width: 70%; border-style:solid;border-width:thin;border-color:#dadce0;\n" +
                        "border-radius:8px;padding:40px 20px\" align=\"center\" \n" +
                        " class=\"m_-6084306337877423944mdv2rw\">\n" +
                        "    <div style=\"font-family:'Google Sans',Roboto,RobotoDraft,Helvetica,Arial,\n" +
                        "    sans-serif;border-bottom:thin solid #dadce0;color:rgba(0,0,0,0.87);\n" +
                        "    line-height:32px;padding-bottom:24px;text-align:center;word-break:break-word\">\n" +
                        "      <div style=\"font-size:48px\">임시 비밀번호가 발급되었습니다 </div>\n" +
                        "        <table align=\"center\" style=\"margin-top:3%\">\n" +
                        "        <tbody>\n" +
                        "        <tr style=\"line-height:normal\">\n" +
                        "        <td>\n" +
                        "        <a style=\"font-family:'Google Sans',Roboto,RobotoDraft,Helvetica,Arial,sans-serif;color:rgba(0,0,0,0.87);font-size:20px;line-height:20px\">\n" +
                        "        " + usersRequestDTO.getEmail() + "\n" +
                        "        </a>\n" +
                        "        </td>\n" +
                        "        </tr>\n" +
                        "        </tbody>\n" +
                        "        </table> \n" +
                        "      </div>\n" +
                        "       <div style=\"color:rgba(0,0,0,0.87);line-height:20px;padding-top:20px;\">\n" +
                        "\n" +
                        "       <div style=\"font-family:Roboto-Regular,Helvetica,Arial,sans-serif;font-size:14px;color:black;\">\n" +
                        "       janackoverflow에 임시 비밀번호를 발급하지 않았다면 다른 사람이 계정을 사용하는 것일 수 있습니다.<br>\n" +
                        "       지금 계정을 점검하고 비밀번호를 변경하세요!\n" +
                        "       </div>\n" +
                        "\n" +
                        "        <div style=\"margin: 5%; font-size: 25px;text-align:center\">\n" +
                        "          임시비밀번호 : " + tempPass + "\n" +
                        "        </div>\n" +
                        "\n" +
                        "        <div style=\"padding-top:5%;text-align:center\">\n" +
                        "          <a href=\"http://localhost:5173/login\" \n" +
                        "          style=\"font-family:'Google Sans',Roboto,RobotoDraft,Helvetica,Arial,sans-serif;line-height:16px;color:#ffffff;font-weight:400;text-decoration:none;font-size:14px;display:inline-block;padding:10px 24px;background-color: rgba(185, 28, 28);border-radius:5px;min-width:90px\" target=\"_blank\">\n" +
                        "          로그인\n" +
                        "          </a>\n" +
                        "        </div>\n" +
                        "      </div>\n" +
                        "\n" +
                        "    <span class=\"im\">\n" +
                        "      <div style=\"padding-top:20px;font-size:12px;letter-spacing: 0.3px;line-height:16px;color:#5f6368;text-align:center\">\n" +
                        "      다음 페이지에서 내 적금 활동도 확인할 수 있습니다.\n" +
                        "      <br>\n" +
                        "      <a href=\"http://localhost:5173/saving\" style=\"color:rgba(0,0,0,0.87);text-decoration:inherit\">\n" +
                        "      http://janackoverflow/saving\n" +
                        "      </a>\n" +
                        "      </div>\n" +
                        "    </span>\n" +
                        "\n" +
                        "</div>\n" +
                        "</body>\n" +
                        "</html>")
                .build();

        if(mailService.sendMail(emailMessage)){
            return new ResponseEntity<>("회원님의 메일로 임시 비밀번호가 발송되었습니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("메일 발송에 실패했습니다..", HttpStatus.FORBIDDEN);
        }

    }

}
