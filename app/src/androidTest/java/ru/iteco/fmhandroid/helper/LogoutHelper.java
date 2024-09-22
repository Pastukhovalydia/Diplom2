package ru.iteco.fmhandroid.helper;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.NoMatchingViewException;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.utils.WaitUtils;

public class LogoutHelper {

    // Метод для выполнения логаута
    public static void performLogout() {
        try {
            // Проверяем, видна ли кнопка с ID authorization_image_button
            onView(withId(R.id.authorization_image_button)).check(matches(isDisplayed()));

            // Если кнопка видна, выполняем нажатие на неё
            onView(withId(R.id.authorization_image_button)).perform(click());

            // Нажимаем на кнопку "Выйти"
            onView(allOf(withText("Выйти"), isDisplayed())).perform(click());
        } catch (NoMatchingViewException e) {
            // Если кнопка не видна, логаут не выполняется
            System.out.println("Кнопка выхода не отображается, пропускаем логаут.");
        }
    }
}