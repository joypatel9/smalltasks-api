package com.joypatel.smalltasks.common;

import org.junit.jupiter.api.Test;

import static com.joypatel.smalltasks.common.MyUtils.UUID_LEN;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MyUtilsTests {

    private final MyUtils myUtils = new MyUtils();

    @Test
    void MyUtils_newUid() {
        // when, then
        assertEquals(UUID_LEN, myUtils.newUid().length());
    }
}
