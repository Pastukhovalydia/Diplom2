package ru.iteco.fmhandroid.pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import ru.iteco.fmhandroid.utils.WaitUtils;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.utils.SimpleIdlingResource; // Импортируем SimpleIdlingResource

public class NewsPage {

    private final SimpleIdlingResource idlingResource; // Переменная для работы с IdlingResource

    public NewsPage(SimpleIdlingResource idlingResource) {
        this.idlingResource = idlingResource; // Инициализируем idlingResource
    }

    public void navigateToAllNews() {
        onView(withText("Все новости"))
                .check(matches(isDisplayed())) // Проверяем, что кнопка отображается
                .perform(click()); // Выполняем нажатие
    }

    public void clickAddNewsButton() {
        // Нажимаем на иконку с плюсом для создания новости по контенту описания
        onView(withContentDescription("Кнопка добавления новости")) // Поиск кнопки по контенту
                .check(matches(isDisplayed())) // Проверка видимости иконки
                .perform(click()); // Нажатие на иконку добавления

        idlingResource.setIdleState(false); // Устанавливаем ресурс в состояние "неактивно"

        idlingResource.setIdleState(true); // Устанавливаем ресурс в состояние "активно"
    }

    public void fillInNewsDetails(String category, String title, String date, String time, String description) {
        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .check(matches(isDisplayed())) // Проверяем отображение поля
                .perform(replaceText(category), closeSoftKeyboard()); // Заполняем текст

        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed())) // Проверяем отображение поля
                .perform(replaceText(title), closeSoftKeyboard()); // Заполняем текст

        onView(withId(R.id.news_item_publish_date_text_input_edit_text))
                .check(matches(isDisplayed())) // Проверяем отображение поля
                .perform(replaceText(date), closeSoftKeyboard()); // Заполняем текст

        onView(withId(R.id.news_item_publish_time_text_input_edit_text))
                .check(matches(isDisplayed())) // Проверяем отображение поля
                .perform(replaceText(time), closeSoftKeyboard()); // Заполняем текст

        onView(withId(R.id.news_item_description_text_input_edit_text))
                .check(matches(isDisplayed())) // Проверяем отображение поля
                .perform(replaceText(description), closeSoftKeyboard()); // Заполняем текст
    }

    public void saveNews() {
        onView(withText("Сохранить"))
                .check(matches(isDisplayed())) // Проверяем отображение кнопки
                .perform(click()); // Выполняем нажатие
    }

    public void verifyNewsDisplayed(String newsTitle) {
        onView(withText(newsTitle))
                .check(matches(isDisplayed())); // Проверяем, что новость отображается
    }

    public void verifyNewsNotDisplayed(String newsTitle) {
        onView(withText(newsTitle))
                .check(doesNotExist()); // Проверяем, что новость не отображается
    }

    public void clickEditNewsButton() {
        onView(withId(R.id.edit_news_material_button))
                .perform(click()); // Выполняем нажатие
    }

    public void navigateToControlPanel() {
        onView(withText("Панель управления"))
                .check(matches(isDisplayed())) // Проверяем, что кнопка отображается
                .perform(click()); // Выполняем нажатие
    }

    public void scrollToNewsWithTitle(String title) {
        onView(withId(R.id.news_list_recycler_view))
                .perform(RecyclerViewActions.scrollTo(
                        hasDescendant(withText(title))
                ));
    }

    public void deleteNewsByTitle(String title) {
        // Прокручиваем список до элемента с заголовком новости
        onView(withId(R.id.news_list_recycler_view))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText(title))));

        // Добавляем ожидание, чтобы убедиться, что элемент видим
        WaitUtils.waitFor(2000);

        // Поиск `ImageView` удаления по ID и проверка, что оно находится в нужной иерархии
        onView(allOf(withId(R.id.delete_news_item_image_view),
                withParent(hasDescendant(withText(title)))))  // Ищем иерархию с нужным заголовком
                .check(matches(isDisplayed())) // Проверяем, что картинка видима
                .perform(click()); // Нажимаем на изображение удаления

        // Подтверждаем удаление
        onView(withText("OK")).perform(click());

        // Добавляем ожидание после нажатия на кнопку "OK"
        WaitUtils.waitFor(3000); // Подождем, чтобы удалить успело завершиться

        // Проверяем, что новость больше не отображается
        verifyNewsNotDisplayed(title);
    }


    public void clickEditNewsButton(String newsTitle) {
        // Прокручиваем список до элемента с заголовком новости
        onView(withId(R.id.news_list_recycler_view))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText(newsTitle))));

        // Нажимаем на изображение редактирования, которое находится под заголовком новости
        onView(allOf(withId(R.id.edit_news_item_image_view),
                withParent(hasDescendant(withText(newsTitle)))))  // Ищем иерархию с нужным заголовком
                .check(matches(isDisplayed())) // Проверяем, что картинка видима
                .perform(click()); // Нажимаем на изображение редактирования
    }

    private String getNewsTitleAtPosition(int position) {
        final String[] title = new String[1];

        // Прокручиваем RecyclerView к нужной позиции
        onView(withId(R.id.news_list_recycler_view))
                .perform(RecyclerViewActions.scrollToPosition(position));

        // Получаем текст заголовка новости по позиции
        onView(atPositionOnView(R.id.news_list_recycler_view, position, R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return isAssignableFrom(TextView.class);
                    }

                    @Override
                    public String getDescription() {
                        return "Get text from a TextView in RecyclerView.";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        if (view instanceof TextView) {
                            title[0] = ((TextView) view).getText().toString();
                        }
                    }
                });

        return title[0];
    }

    public static Matcher<View> atPositionOnView(final int recyclerViewId, final int position, final int targetViewId) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("RecyclerView at position: " + position);
            }

            @Override
            protected boolean matchesSafely(RecyclerView recyclerView) {
                RecyclerView.Adapter adapter = recyclerView.getAdapter();
                if (adapter == null || position >= adapter.getItemCount()) {
                    return false; // Проверяем корректность позиции
                }

                View view = recyclerView.getChildAt(position);
                if (view == null) {
                    return false; // Если элемент не найден
                }

                View targetView = view.findViewById(targetViewId);
                return targetView != null && targetView.getVisibility() == View.VISIBLE; // Проверяем видимость целевого элемента
            }
        };
    }
}