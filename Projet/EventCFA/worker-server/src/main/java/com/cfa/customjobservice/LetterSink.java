package com.cfa.customjobservice;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface LetterSink {
    String INPUT = "letterInput";

    @Input(INPUT)
    SubscribableChannel input();
}
