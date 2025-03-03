/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.domain.core.message.service;

import static com.regulus.infrastructure.util.UuidUtil.generateUUIDFileName;

import com.regulus.domain.core.ai.model.RecognizeResult;
import com.regulus.domain.core.ai.repository.PositionDailyRepository;
import com.regulus.domain.core.ai.repository.StrategyDailyRepository;
import com.regulus.domain.core.ai.service.AiService;
import com.regulus.domain.core.message.model.MessageDaily;
import com.regulus.domain.core.message.repositpory.MessageDailyRepository;
import com.regulus.infrastructure.util.ImgUtil;
import jakarta.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/** 消息处理类 */
@Service
@Slf4j
public class MessageProcessService {
    private static final String GET_STRATEGY = "今日策略";

    @Value("${message.dir:}")
    private String imgDir;

    @Resource private MessageDailyRepository messageDailyRepository;

    @Resource private StrategyDailyRepository strategyDailyRepository;

    @Resource private PositionDailyRepository positionDailyRepository;

    @Resource private AiService aiService;

    @Resource private ImgUtil imgUtil;

    /**
     * 消息处理
     *
     * @param message 消息内容
     * @param imageUrl 图片url
     * @param botService 机器人类
     * @param userId 用户ID
     * @param messageId 消息ID
     */
    public void processMessage(
            String message, String imageUrl, BotService botService, String userId, String messageId) {
        log.info("获取消息: {}", message);
        if (GET_STRATEGY.equals(message)) {
            String content = strategyDailyRepository.getStrategy(LocalDate.now());
            botService.sendMessage(content, userId, messageId);
        } else if (!Objects.isNull(imageUrl)) {
            try {
                String imgName = generateUUIDFileName("jpg");
                String imgPath = imgDir + "/" + imgName;
                imgUtil.imgUrlToFile(imageUrl, imgPath);
                RecognizeResult recognizeResult = aiService.getRecognizeResult(imgPath);
                log.info("识别结果为: {}", recognizeResult);
                switch (recognizeResult.getType()) {
                    case ANALYZE -> saveMessageDaily(recognizeResult.getContent(), imgPath);
                    case POSITION ->
                            positionDailyRepository.savePosition(recognizeResult.getContent(), LocalDate.now());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            saveMessageDaily(message, null);
        }
    }

    private void saveMessageDaily(String message, String imgPath) {
        MessageDaily messageDaily = new MessageDaily(message, imgPath, LocalDateTime.now());
        messageDailyRepository.saveMessage(messageDaily);
    }
}
