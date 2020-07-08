package com.web;

import com.service.MessageService;
import com.service.search.SearchCriteriaList;
import com.view.MessageTicketDto;
import com.view.MessageView;
import com.view.SimplePageResponse;
import com.view.SendMessageDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/message")
@Api(value = "message")
public class MessageController {

    @Autowired
    private MessageService messageSrv;

    @PostMapping("/send-message")
    @ApiParam(value = "only for send from outside app")
    public String sendMessageOut(HttpServletRequest request, @Valid @RequestBody SendMessageDto dto) {
        dto.setIp(request.getRemoteAddr());
        return messageSrv.sendMessageOut(dto);
    }

    @PostMapping("/load-by-ticket")
    @ApiParam(value = "only for send from outside app")
    public String loadByTicket(@Valid @RequestBody MessageTicketDto dto) {
        return messageSrv.loadByTicket(dto);
    }

    @PostMapping("/chat")
    public void chat(@Valid @RequestBody MessageView view) {
        messageSrv.save(view);
    }

    @PostMapping("/list")
    public SimplePageResponse<MessageView> list(@Valid @RequestBody SearchCriteriaList search) {
        return messageSrv.list(search);
    }
}
