package ru.iteco.fmhandroid.utils;

import ru.iteco.fmhandroid.R;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import org.hamcrest.Matcher;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


import static ru.iteco.fmhandroid.utils.RecyclerViewUtils.atPositionOnView;


// Класс для прокрутки RecyclerView до элемента с заданным заголовком
public class RecyclerViewScrollTo implements ViewAction {
    private final String title; // Заголовок новости для поиска

    // Конструктор принимает заголовок новости
    public RecyclerViewScrollTo(String title) {
        this.title = title;
    }

    @Override
    public Matcher<View> getConstraints() {
        return isAssignableFrom(RecyclerView.class); // Проверяем, что элемент является RecyclerView
    }

    @Override
    public String getDescription() {
        return "Scroll RecyclerView to the item with title: " + title; // Описание действия
    }

    @Override
    public void perform(UiController uiController, View view) {
        RecyclerView recyclerView = (RecyclerView) view; // Приводим к RecyclerView
        RecyclerView.Adapter adapter = recyclerView.getAdapter(); // Получаем адаптер

        if (adapter == null) {
            return; // Если адаптер не найден, выходим
        }

        // Проходим по всем элементам адаптера
        for (int i = 0; i < adapter.getItemCount(); i++) {
            // Проверяем, совпадает ли заголовок
            if (getNewsTitleAtPosition(recyclerView, i).equals(title)) {
                // Прокручиваем до позиции с найденным заголовком
                onView(withId(R.id.news_list_recycler_view))
                        .perform(RecyclerViewActions.scrollToPosition(i));
                return; // Завершаем выполнение
            }
        }
    }

    // Получаем заголовок новости по позиции
    private String getNewsTitleAtPosition(RecyclerView recyclerView, int position) {
        final String[] title = new String[1]; // Массив для хранения заголовка
        // Проверяем элемент по позиции
        onView(atPositionOnView(R.id.news_list_recycler_view, position, R.id.news_item_title_text_view))
                .check(matches(isDisplayed())) // Проверяем, что элемент отображается
                .perform(new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return isAssignableFrom(TextView.class); // Проверяем, что элемент является TextView
                    }

                    @Override
                    public String getDescription() {
                        return "Get text from a TextView in RecyclerView."; // Описание действия
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        if (view instanceof TextView) {
                            title[0] = ((TextView) view).getText().toString(); // Получаем текст заголовка
                        }
                    }
                });
        return title[0]; // Возвращаем заголовок
    }
}