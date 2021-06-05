package com.joypatel.smalltasks.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import static com.joypatel.smalltasks.common.MyUtils.UUID_LEN;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class MyUtilsTests {

    @Mock
    @SuppressWarnings("unused")
    private MessageSource messageSource;

    @InjectMocks
    private MyUtils myUtils;

    @Test
    void MyUtils_newUid() {
        // when, then
        assertEquals(UUID_LEN, myUtils.newUid().length());
    }
}
