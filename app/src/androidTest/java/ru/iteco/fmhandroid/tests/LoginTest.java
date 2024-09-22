package ru.iteco.fmhandroid.tests;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.junit4.DisplayName;

import ru.iteco.fmhandroid.pages.LoginPage;
import ru.iteco.fmhandroid.data.LoginData;
import ru.iteco.fmhandroid.pages.MainPage;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.utils.WaitUtils;
import ru.iteco.fmhandroid.helper.LogoutHelper;






@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Rule
    public ActivityScenarioRule<AppActivity> activityRule = new ActivityScenarioRule<>(AppActivity.class);

    private final LoginPage loginPage = new LoginPage();


    // Тест для авторизации с пустым полем "Логин"
    @Test
    @DisplayName("Авторизация с пустым полем 'Логин'")
    @Description("Тест проверяет авторизацию без заполнения поля 'Логин'")
    public void loginWithEmptyLoginField() {
        WaitUtils.waitFor(7000);
        loginPage.enterLogin(LoginData.EMPTY_STRING); // Оставляем логин пустым
        WaitUtils.waitFor(7000);
        loginPage.enterPassword(LoginData.VALID_PASSWORD); // Вводим валидный пароль
        WaitUtils.waitFor(7000);
        loginPage.clickEnterButton(); // Нажимаем кнопку "Войти"
        WaitUtils.waitFor(7000);

        // Проверка, что кнопка входа по-прежнему доступна
        loginPage.enterButton.check(matches(isDisplayed()));
    }


    // Тест для авторизации с пустым полем "Пароль"
    @Test
    @DisplayName("Авторизация с пустым полем 'Пароль'")
    @Description("Тест проверяет авторизацию без заполнения поля 'Пароль'")
    public void loginWithEmptyPasswordField() {
        WaitUtils.waitFor(7000);
        loginPage.enterLogin(LoginData.VALID_LOGIN); // Вводим валидный логин
        WaitUtils.waitFor(7000);
        loginPage.enterPassword(LoginData.EMPTY_STRING); // Оставляем пароль пустым
        WaitUtils.waitFor(7000);
        loginPage.clickEnterButton(); // Нажимаем кнопку "Войти"
        WaitUtils.waitFor(7000);

        // Проверка, что кнопка входа по-прежнему доступна
        loginPage.enterButton.check(matches(isDisplayed()));
    }

    // Тест для авторизации с невалидными данными
    @Test
    @DisplayName("Авторизация с невалидными данными")
    @Description("Тест проверяет авторизацию с невалидными данными")
    public void loginWithInvalidCredentials() {
        WaitUtils.waitFor(7000);
        loginPage.enterLogin(LoginData.INVALID_LOGIN); // Вводим невалидный логин
        WaitUtils.waitFor(7000);
        loginPage.enterPassword(LoginData.INVALID_PASSWORD); // Вводим невалидный пароль
        WaitUtils.waitFor(7000);
        loginPage.clickEnterButton(); // Нажимаем кнопку "Войти"
        WaitUtils.waitFor(7000);

        // Проверка, что кнопка входа по-прежнему доступна
        loginPage.enterButton.check(matches(isDisplayed()));
    }


    // Тест для успешной авторизации с валидными данными
    @Test
    @DisplayName("Авторизация с валидными данными")
    @Description("Тест проверяет успешную авторизацию зарегистрированного пользователя")
    public void successfulLoginWithValidCredentials() {
        WaitUtils.waitFor(7000); // Ожидание 7 секунд перед вводом логина
        loginPage.enterLogin(LoginData.VALID_LOGIN); // Вводим валидный логин

        WaitUtils.waitFor(7000); // Ожидание 7 секунд перед вводом пароля
        loginPage.enterPassword(LoginData.VALID_PASSWORD); // Вводим валидный пароль

        WaitUtils.waitFor(7000); // Ожидание 7 секунд перед нажатием кнопки
        loginPage.clickEnterButton(); // Нажимаем кнопку "Войти"

        WaitUtils.waitFor(7000); // Ожидание 7 секунд перед нажатием кнопки
        new MainPage().checkNewsTitleDisplayed(); // Проверка, что главный экран открылся успешно


        // Выполняем выход из системы только после успешной авторизации
        LogoutHelper.performLogout();
        }

    }
