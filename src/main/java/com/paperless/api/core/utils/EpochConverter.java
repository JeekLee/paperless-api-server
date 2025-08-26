package com.paperless.api.core.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class EpochConverter {
    public static LocalDateTime convertEpochToDateTime(Long epochTime) {
        return LocalDateTime.ofEpochSecond(epochTime, 0, ZoneId.of("Asia/Seoul").getRules().getOffset(LocalDateTime.now()));
    }
}
