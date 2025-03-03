/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.infrastructure.repository;

import com.regulus.domain.core.message.model.MessageDaily;
import com.regulus.domain.core.message.repositpory.MessageDailyRepository;
import com.regulus.infrastructure.assembler.MessageDailyMapper;
import com.regulus.infrastructure.repository.mapper.MessageDailyDoMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/** 每日消息 仓储层实现 */
@Service
public class DefaultMessageDailyRepository implements MessageDailyRepository {
    @Resource private MessageDailyDoMapper messageDailyDoMapper;

    @Resource private MessageDailyMapper messageDailyMapper;

    @Override
    public void saveMessage(MessageDaily messageDaily) {
        messageDailyDoMapper.insert(messageDailyMapper.modelToDo(messageDaily));
    }
}
