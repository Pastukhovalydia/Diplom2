package ru.iteco.fmhandroid.utils;

import android.os.SystemClock;

public class WaitUtils {
    // Метод для ожидания заданного количества миллисекунд
    public static void waitFor(long millis) {
        SystemClock.sleep(millis);
    }
}