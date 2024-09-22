package ru.iteco.fmhandroid.tests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.allOf;



import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.helper.LogoutHelper;
import ru.iteco.fmhandroid.pages.LoginPage;
import ru.iteco.fmhandroid.pages.QuotesPage;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.data.LoginData;
import ru.iteco.fmhandroid.utils.WaitUtils;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import ru.iteco.fmhandroid.R;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.StringContains.containsString;





@RunWith(AndroidJUnit4.class)
public class QuotesTest {

    @Rule
    public ActivityScenarioRule<AppActivity> activityRule = new ActivityScenarioRule<>(AppActivity.class);

    private final LoginPage loginPage = new LoginPage();
    private QuotesPage quotesPage;

    @Before
    public void setup() {
        // Ожидание загрузки приложения
        WaitUtils.waitFor(7000);

        // Авторизация
        loginPage.enterLogin(LoginData.VALID_LOGIN);
        WaitUtils.waitFor(2000);
        loginPage.enterPassword(LoginData.VALID_PASSWORD);
        WaitUtils.waitFor(2000);
        loginPage.clickEnterButton();

        // Ожидание загрузки после авторизации
        WaitUtils.waitFor(5000);

        quotesPage = new QuotesPage();
    }

    @Test
    @DisplayName("Проверка отображения конкретной цитаты")
    @Description("Тест проверяет отображение конкретной цитаты на странице цитат")
    public void checkSpecificQuoteTest() {
        // Нажимаем на кнопку с бабочкой
        quotesPage.clickButterflyImageButton();

        // Увеличиваем время ожидания для загрузки цитат
        WaitUtils.waitFor(5000);

        // Прокручиваем до цитаты в RecyclerView и проверяем, что она отображается
        quotesPage.checkQuoteIsDisplayed("В хосписе не работают плохие люди");

        WaitUtils.waitFor(5000);

        // Выполняем выход из системы только после успешной авторизации
        LogoutHelper.performLogout();
    }

    @Test
    @DisplayName("Проверка сворачивания и разворачивания цитаты")
    @Description("Тест проверяет, что после сворачивания цитаты текст исчезает, а после разворачивания снова отображается.")
    public void toggleQuoteTest() {
        // Нажимаем на кнопку с бабочкой
        quotesPage.clickButterflyImageButton();

        // Увеличиваем время ожидания для загрузки цитат
        WaitUtils.waitFor(5000);

        // Прокручиваем до цитаты и проверяем её отображение
        String quoteText = "В хосписе не работают плохие люди";
        quotesPage.checkQuoteIsDisplayed(quoteText);

        // Разворачиваем цитату
        quotesPage.expandQuote(quoteText);

        // Увеличиваем время ожидания для отображения текста после разворачивания
        WaitUtils.waitFor(5000);

        // Проверяем, что текст после разворачивания отображается
        String expandedText = "Все сотрудники хосписа - это адвокаты пациента, его прав и потребностей. Поиск путей решения различных задач - это и есть хосписный индивидуальный подход к паллиативной помощи.";
        onView(withText(expandedText))
                .check(matches(isDisplayed()));

        // Сворачиваем цитату обратно
        quotesPage.collapseQuote(quoteText);

        // Ожидание после сворачивания, чтобы дать время интерфейсу обновиться
        WaitUtils.waitFor(5000);

        // Проверяем, что текст после сворачивания не отображается
        onView(withText(expandedText))
                .check(matches(not(isDisplayed())));

        WaitUtils.waitFor(5000);

        // Выполняем выход из системы только после успешной авторизации
        LogoutHelper.performLogout();
    }

}