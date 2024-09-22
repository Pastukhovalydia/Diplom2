package ru.iteco.fmhandroid.pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import androidx.test.espresso.contrib.RecyclerViewActions;
import static org.hamcrest.core.StringContains.containsString;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.utils.WaitUtils;

public class QuotesPage {

    // Метод для нажатия на кнопку с бабочкой
    public void clickButterflyImageButton() {
        WaitUtils.waitFor(2000); // Ожидание перед нажатием
        onView(withId(R.id.our_mission_image_button))
                .check(matches(isDisplayed())) // Проверяем, что кнопка отображается
                .perform(click()); // Выполняем нажатие
    }

    // Метод для проверки отображения заголовка миссии с конкретным текстом
    public void checkMissionTitleIsDisplayed(String expectedText) {
        WaitUtils.waitFor(2000); // Ожидание перед проверкой текста
        onView(allOf(withId(R.id.our_mission_item_title_text_view), withText(expectedText)))
                .check(matches(isDisplayed())); // Проверяем, что текст с ожидаемым значением отображается
    }

    // Метод для проверки отображения конкретной цитаты по тексту в RecyclerView
    public void checkQuoteIsDisplayed(String quoteText) {
        WaitUtils.waitFor(2000); // Ожидание перед проверкой цитаты
        onView(withId(R.id.our_mission_item_list_recycler_view))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText(containsString(quoteText)))))
                .check(matches(isDisplayed())); // Проверяем, что цитата отображается
    }

    // Метод для разворачивания цитаты
    public void expandQuote(String quoteText) {
        onView(withId(R.id.our_mission_item_list_recycler_view))
                .perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(containsString(quoteText))), click()));
    }

    // Метод для сворачивания цитаты
    public void collapseQuote(String quoteText) {
        onView(withId(R.id.our_mission_item_list_recycler_view))
                .perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(containsString(quoteText))), click()));
    }
}