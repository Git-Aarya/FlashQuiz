package model;

// Tracks the elapsed time during a study session.
// Records the start and stop time, and provides the elapsed time in minutes and seconds.
public class StudyTimer {

    // EFFECTS: Returns the elapsed time in a formatted string (minutes and seconds).
    public String getElapsedTime(long startTime, long endTime) {
        long elapsedTimeMillis = endTime - startTime;

        if (elapsedTimeMillis < 1000) {
            return elapsedTimeMillis + " milliseconds";
        } else if (elapsedTimeMillis < 60000) {
            long seconds = elapsedTimeMillis / 1000;
            return seconds + " seconds";
        } else {
            long minutes = elapsedTimeMillis / 60000;
            long remainingSeconds = (elapsedTimeMillis % 60000) / 1000;
            return minutes + " minutes " + remainingSeconds + " seconds";
        }
    }
}




