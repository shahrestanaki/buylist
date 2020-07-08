package com.repository;

import com.model.Message;
import com.view.MessageView;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends GeneralRepository<Message, MessageView, Long> {

    long countByUserIdAndDateStr(Long userId , Long dateStr);

    Message findByMobileAndTicket(String mobile, String ticket);
}
