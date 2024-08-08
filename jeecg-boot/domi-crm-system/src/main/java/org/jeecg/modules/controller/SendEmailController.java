package org.jeecg.modules.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.dto.message.MessageDTO;
import org.jeecg.modules.message.handle.impl.EmailSendMsgHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("发送邮箱")
@Slf4j
@RestController
@RequestMapping("/send_email")
public class SendEmailController {
    
    @Autowired
    private EmailSendMsgHandle emailSendMsgHandle;
    
    @ApiOperation("发送邮件")
    @GetMapping("/send")
    public String sendEmail() {
        MessageDTO messageDTO = new MessageDTO();
        emailSendMsgHandle.sendMsg("geogreyyyy@gmail.com","你好","你好");
        return "success";
    }
}
