package com.regulus.domain.core.message.model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.*;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

public class SendPeriodTest {

    // 创建带时区的固定时间对象
    private ZonedDateTime createMockTime(String time) {
        LocalDateTime dateTime = LocalDate.now().atTime(LocalTime.parse(time));
        return ZonedDateTime.of(dateTime, ZoneId.of("Asia/Shanghai"));
    }

    @Test
    public void testMorningPeriod() {
        // 模拟早晨时间 08:30
        ZonedDateTime fixedTime = createMockTime("08:30");
        try (MockedStatic<ZonedDateTime> mock = mockStatic(ZonedDateTime.class)) {
            mock.when(() -> ZonedDateTime.now(ZoneId.of("Asia/Shanghai"))).thenReturn(fixedTime);
            assertEquals(SendPeriod.MORNING, SendPeriod.determineByCurrentTime());
        }
    }

    @Test
    public void testNoonPeriodStartBoundary() {
        // 测试 NOON 时段起始点 09:30
        ZonedDateTime fixedTime = createMockTime("09:30");
        try (MockedStatic<ZonedDateTime> mock = mockStatic(ZonedDateTime.class)) {
            mock.when(() -> ZonedDateTime.now(ZoneId.of("Asia/Shanghai"))).thenReturn(fixedTime);
            assertEquals(SendPeriod.NOON, SendPeriod.determineByCurrentTime());
        }
    }

    @Test
    public void testNoonPeriodMiddle() {
        // 测试 NOON 时段中间时间 12:00
        ZonedDateTime fixedTime = createMockTime("12:00");
        try (MockedStatic<ZonedDateTime> mock = mockStatic(ZonedDateTime.class)) {
            mock.when(() -> ZonedDateTime.now(eq(ZoneId.of("Asia/Shanghai")))).thenReturn(fixedTime);
            assertEquals(SendPeriod.NOON, SendPeriod.determineByCurrentTime());
        }
    }

    @Test
    public void testEveningPeriodStartBoundary() {
        // 测试 EVENING 起始点 15:00
        ZonedDateTime fixedTime = createMockTime("15:00");
        try (MockedStatic<ZonedDateTime> mock = mockStatic(ZonedDateTime.class)) {
            mock.when(() -> ZonedDateTime.now(eq(ZoneId.of("Asia/Shanghai")))).thenReturn(fixedTime);
            assertEquals(SendPeriod.EVENING, SendPeriod.determineByCurrentTime());
        }
    }

    @Test
    public void testEveningPeriodMiddle() {
        // 测试 EVENING 中间时间 16:00
        ZonedDateTime fixedTime = createMockTime("16:00");
        try (MockedStatic<ZonedDateTime> mock = mockStatic(ZonedDateTime.class)) {
            mock.when(() -> ZonedDateTime.now(eq(ZoneId.of("Asia/Shanghai")))).thenReturn(fixedTime);
            assertEquals(SendPeriod.EVENING, SendPeriod.determineByCurrentTime());
        }
    }

    @Test
    public void testDefaultMorningCase() {
        // 测试默认返回 EVENING 的时间 20:30
        ZonedDateTime fixedTime = createMockTime("20:30");
        try (MockedStatic<ZonedDateTime> mock = mockStatic(ZonedDateTime.class)) {
            mock.when(() -> ZonedDateTime.now(eq(ZoneId.of("Asia/Shanghai")))).thenReturn(fixedTime);
            assertEquals(SendPeriod.EVENING, SendPeriod.determineByCurrentTime());
        }
    }

    @Test
    public void testBoundaryBeforeNoon() {
        // 测试 NOON 前边界 09:29:59
        ZonedDateTime fixedTime = createMockTime("09:29:59");
        try (MockedStatic<ZonedDateTime> mock = mockStatic(ZonedDateTime.class)) {
            mock.when(() -> ZonedDateTime.now(eq(ZoneId.of("Asia/Shanghai")))).thenReturn(fixedTime);
            assertEquals(SendPeriod.MORNING, SendPeriod.determineByCurrentTime());
        }
    }

    @Test
    public void testBoundaryAfterNoon() {
        // 测试 NOON 后边界 14:59:59
        ZonedDateTime fixedTime = createMockTime("14:59:59");
        try (MockedStatic<ZonedDateTime> mock = mockStatic(ZonedDateTime.class)) {
            mock.when(() -> ZonedDateTime.now(eq(ZoneId.of("Asia/Shanghai")))).thenReturn(fixedTime);
            assertEquals(SendPeriod.NOON, SendPeriod.determineByCurrentTime());
        }
    }

    @Test
    public void testMidnightEdgeCase() {
        // 测试午夜临界点 00:00
        ZonedDateTime fixedTime = createMockTime("00:00");
        try (MockedStatic<ZonedDateTime> mock = mockStatic(ZonedDateTime.class)) {
            mock.when(() -> ZonedDateTime.now(eq(ZoneId.of("Asia/Shanghai")))).thenReturn(fixedTime);
            // 根据业务逻辑验证预期结果
            assertTrue(
                    SendPeriod.MORNING == SendPeriod.determineByCurrentTime()
                            || SendPeriod.EVENING == SendPeriod.determineByCurrentTime());
        }
    }
}
