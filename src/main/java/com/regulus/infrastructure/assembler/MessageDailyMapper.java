/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.infrastructure.assembler;

import com.regulus.domain.core.message.model.MessageDaily;
import com.regulus.infrastructure.persistence.MessageDailyDo;
import org.mapstruct.Mapper;

/** MessageDaily 类型转换器 */
@Mapper(componentModel = "spring")
public interface MessageDailyMapper {
    /**
     * MessageDaily → MessageDailyDo
     *
     * @param model MessageDaily
     * @return MessageDailyDo
     */
    MessageDailyDo modelToDo(MessageDaily model);
}
