package com.service;

import com.exception.AppException;
import com.model.Message;
import com.repository.MessageRepository;
import com.service.mapper.MapperGeneral;
import com.service.search.SearchCriteriaList;
import com.tools.CorrectDate;
import com.tools.GeneralTools;
import com.tools.GetResourceBundle;
import com.view.MessageTicketDto;
import com.view.MessageView;
import com.view.SendMessageDto;
import com.view.SimplePageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageService extends GeneralService {

    @Autowired
    private MessageRepository messageRepo;

    public String sendMessageOut(SendMessageDto dto) {
        Message message = new Message();
        message.setId(null);
        message.setMobile(dto.getMobile());
        message.setType(dto.getType());
        message.setComment(dto.getComment());
        message.setIp(dto.getIp());
        String ticket = GeneralTools.createRandom("number", 5);
        message.setTicket(ticket);
        message.setAnswered(false);
        messageRepo.save(message);
        return ticket;
    }

    public void save(MessageView view) {
        Long userId = getUser().getId();
        Long numberInToday = messageRepo.countByUserIdAndDateStr(userId, Long.valueOf(CorrectDate.convertDate(new Date(), "")));

        if (numberInToday >= Long.valueOf(GetResourceBundle.getConfig.getString("MAX_MESSAGE_USER"))) {
            throw new AppException("message.max.limit." + GeneralTools.createRandom(1, 3));
        }
        Message message = new Message();
        message.setId(null);
        message.setComment(view.getComment());
        message.setAnswered(false);
        message.setUserId(userId);
        message.setDateStr(Long.valueOf(CorrectDate.convertDate(new Date(), "")));
        messageRepo.save(message);
    }

    public SimplePageResponse<MessageView> list(SearchCriteriaList search) {
        Message message = new Message();
        message.setUserId(getUser().getId());

        SimplePageResponse<MessageView> result = new SimplePageResponse<>();
        List<MessageView> listView = new ArrayList<>();
        messageRepo.list(message, search).getContent().forEach(item ->
                listView.add(MapperGeneral.mapper(Message.class, MessageView.class).map(item, MessageView.class))
        );
        result.setContent(listView);
        result.setCount(listView.size());
        return result;
    }

    public String loadByTicket(MessageTicketDto dto) {
        Message message = messageRepo.findByMobileAndTicket(dto.getMobile(), String.valueOf(dto.getTicket()));
        if (message == null) {
            throw new AppException("message.ticket.null");
        } else if (message.getAnswered()) {
            return message.getAnswer();
        } else {
            throw new AppException("message.answer.isnull");
        }
    }
}
