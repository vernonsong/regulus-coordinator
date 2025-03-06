// package com.regulus.interfaces.facade.rest;
//
// import com.alibaba.fastjson2.JSONObject;
// import com.regulus.domain.core.chat.service.Bot;
// import jakarta.annotation.Resource;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.web.bind.annotation.*;
//
// import java.util.Map;
//
// @Slf4j
// @RestController
// @RequestMapping("/bot")
// public class BotController {
// @Resource
// private Bot bot;
//
// @PostMapping("/message/callback")
// public JSONObject getMsg(@RequestBody JSONObject event, @RequestHeader
// Map<String, String>
// headers) {
// log.info("POST /bot/message/callback");
// return bot.receiveEvent(event, headers);
// }
//
// @PostMapping("/message/send")
// public JSONObject sendMsg(@RequestBody JSONObject event, @RequestHeader
// Map<String, String>
// headers) {
// log.info("POST /bot/message/send");
// bot.sendMessage(event.getString("message"));
// return null;
// }
// }
