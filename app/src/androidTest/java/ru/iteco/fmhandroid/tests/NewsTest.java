package ru.iteco.fmhandroid.tests;

import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.iteco.fmhandroid.data.LoginData;
import ru.iteco.fmhandroid.helper.LogoutHelper;
import ru.iteco.fmhandroid.pages.LoginPage;
import ru.iteco.fmhandroid.pages.NewsPage;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.utils.SimpleIdlingResource;
import ru.iteco.fmhandroid.utils.WaitUtils;
import ru.iteco.fmhandroid.utils.RecyclerViewScrollTo;

import java.util.Random;

@RunWith(AndroidJUnit4.class)
public class NewsTest {

    @Rule
    public ActivityScenarioRule<AppActivity> activityRule = new ActivityScenarioRule<>(AppActivity.class);

    private final LoginPage loginPage = new LoginPage();
    private SimpleIdlingResource idlingResource;
    private NewsPage newsPage;

    private String randomTitle;

    @Before
    public void setup() {
        idlingResource = new SimpleIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);

        WaitUtils.waitFor(7000);
        loginPage.enterLogin(LoginData.VALID_LOGIN);
        WaitUtils.waitFor(2000);
        loginPage.enterPassword(LoginData.VALID_PASSWORD);
        loginPage.clickEnterButton();

        WaitUtils.waitFor(5000);

        randomTitle = "Тестовая новость " + new Random().nextInt(1000);
        newsPage = new NewsPage(idlingResource);
    }

    @DisplayName("Создание новости")
    @Description("Тест проверяет создание новой новости.")
    @Test
    public void createNewsTest() {
        // Переход к разделу "Все новости"
        newsPage.navigateToAllNews();

        WaitUtils.waitFor(4000); // Ожидание перехода на следующую страницу

        // Нажимаем на иконку в виде карандаша для редактирования новости
        newsPage.clickEditNewsButton();

        WaitUtils.waitFor(4000); // Ожидание выполнения действий после редактирования

        // Нажимаем на кнопку добавления новости
        newsPage.clickAddNewsButton();

        WaitUtils.waitFor(4000); // Ожидание перехода на страницу создания новости

        // Заполнение информации о новости
        newsPage.fillInNewsDetails("Объявление", randomTitle, "17.12.2028", "19:00", "Описание тестовой новости");

        WaitUtils.waitFor(4000); // Добавила

        // Сохранение новости
        newsPage.saveNews();

        WaitUtils.waitFor(4000); // Добавила

        // Проверка наличия созданной новости в списке
        newsPage.verifyNewsDisplayed(randomTitle);

        WaitUtils.waitFor(4000);

        // Выполняем выход из системы только после успешной авторизации
        LogoutHelper.performLogout();
    }




    @DisplayName("Удаление новости")
    @Description("Тест проверяет удаление новости из раздела 'Панель управления'.")
    @Test
    public void deleteNewsTest2() {
        // Генерация случайного заголовка для новости
        String randomTitle = "Тестовая новость " + System.currentTimeMillis();  // Используем текущее время для генерации уникального названия

        // Создаем новость
        newsPage.navigateToAllNews();
        WaitUtils.waitFor(4000);

        // Нажимаем на иконку в виде карандаша для редактирования новостей
        newsPage.clickEditNewsButton();
        WaitUtils.waitFor(4000);

        // Нажимаем на кнопку добавления новости
        newsPage.clickAddNewsButton();
        WaitUtils.waitFor(4000);

        // Заполняем информацию о новости
        newsPage.fillInNewsDetails("Объявление", randomTitle, "18.12.2028", "12:00", "Описание тестовой новости");

        // Сохраняем новость
        newsPage.saveNews();

        // Прокручиваем до созданной новости
        newsPage.scrollToNewsWithTitle(randomTitle);
        WaitUtils.waitFor(4000);

        // Удаляем созданную новость по заголовку
        newsPage.deleteNewsByTitle(randomTitle);

        WaitUtils.waitFor(4000);

        // Выполняем выход из системы только после успешной авторизации
        LogoutHelper.performLogout();
    }


    @DisplayName("Изменение новости")
    @Description("Тест проверяет изменение существующей новости.")
    @Test
    public void editNewsTest() {
        // Генерация случайного заголовка для новости
        String randomTitle = "Тестовая новость " + System.currentTimeMillis();

        // Создаем новость
        newsPage.navigateToAllNews();
        WaitUtils.waitFor(4000);

        // Нажимаем на иконку в виде карандаша для редактирования новостей
        newsPage.clickEditNewsButton();
        WaitUtils.waitFor(4000);

        // Нажимаем на кнопку добавления новости
        newsPage.clickAddNewsButton();
        WaitUtils.waitFor(4000);

        // Заполняем информацию о новости
        newsPage.fillInNewsDetails("Объявление", randomTitle, "19.12.2028", "12:00", "Описание тестовой новости");

        // Сохраняем новость
        newsPage.saveNews();
        WaitUtils.waitFor(4000);

        // Прокручиваем до созданной новости
        newsPage.scrollToNewsWithTitle(randomTitle);
        WaitUtils.waitFor(4000);

        /// Открываем новость для редактирования
        newsPage.clickEditNewsButton(randomTitle);  // Передаем заголовок новости для поиска

        // Изменяем заголовок и описание новости
        String newTitle = "Измененная новость " + System.currentTimeMillis();
        newsPage.fillInNewsDetails("Объявление", newTitle, "31.12.2028", "15:00", "Новое описание тестовой новости");

        // Сохраняем изменения
        newsPage.saveNews();
        WaitUtils.waitFor(4000);

        // Проверяем, что измененная новость отображается
        newsPage.verifyNewsDisplayed(newTitle);
        newsPage.verifyNewsNotDisplayed(randomTitle);  // Проверяем, что старая новость больше не отображается

        WaitUtils.waitFor(4000);

        // Выполняем выход из системы только после успешной авторизации
        LogoutHelper.performLogout();
    }


}