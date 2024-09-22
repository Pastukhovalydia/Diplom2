package ru.iteco.fmhandroid.pages;

import androidx.test.espresso.Espresso;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;

// Класс представляет главную страницу после авторизации
public class MainPage {

    // Метод для проверки наличия заголовка на главной странице
    public void checkNewsTitleDisplayed() {
        Espresso.onView(allOf(withText("Новости"), isDisplayed())) // Поиск по тексту заголовка
                .check(matches(isDisplayed())); // Проверяем, что заголовок новостей отображается
    }
}