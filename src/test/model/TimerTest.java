package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimerTest {
    private StudyTimer studyTimer;

    @BeforeEach
    void setUp() {
        studyTimer = new StudyTimer();
    }
    @Test
    void testStartAndStopMsLowerBound() {
        String elapsedTime = studyTimer.getElapsedTime(0,500);
        assertTrue(elapsedTime.equals("500 milliseconds"));
    }

    @Test
    void testStartAndStopMsMidBound() {
        String elapsedTime = studyTimer.getElapsedTime(1020,1954);
        assertTrue(elapsedTime.equals("934 milliseconds"));
    }

    @Test
    void testStartAndStopMsUpperBound() {
        String elapsedTime = studyTimer.getElapsedTime(2000,2999);
        assertTrue(elapsedTime.equals("999 milliseconds"));
    }

    @Test
    void testStartAndStopSecondLowerBound() {
        String elapsedTime = studyTimer.getElapsedTime(0,1000);
        assertTrue(elapsedTime.equals("1 seconds"));
    }

    @Test
    void testStartAndStopSecondMidBound() {
        String elapsedTime = studyTimer.getElapsedTime(21402,75476);
        assertTrue(elapsedTime.equals("54 seconds"));
    }

    @Test
    void testStartAndStopSecondUpperBound() {
        String elapsedTime = studyTimer.getElapsedTime(50000,109999);
        assertTrue(elapsedTime.equals("59 seconds"));
    }

    @Test
    void testStartAndStopMinuteLowerBound() {
        String elapsedTime = studyTimer.getElapsedTime(0,60000);
        assertTrue(elapsedTime.equals("1 minutes 0 seconds"));
    }

    @Test
    void testStartAndStopMinuteMidBound() {
        String elapsedTime = studyTimer.getElapsedTime(1000,70000);
        assertTrue(elapsedTime.equals("1 minutes 9 seconds"));
    }


}
