package ru.iteco.fmhandroid.pages;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.Espresso;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


import ru.iteco.fmhandroid.R;

public class LoginPage {

    public static ViewInteraction loginFieldText = Espresso.onView(withHint("Логин"));
    public static ViewInteraction passwordFieldId = Espresso.onView(withHint("Пароль"));
    public static ViewInteraction enterButton = Espresso.onView(withId(R.id.enter_button));

    public void enterLogin(String login) {
        loginFieldText.perform(replaceText(login));
    }

    public void enterPassword(String password) {
        passwordFieldId.perform(replaceText(password));
    }

    public void clickEnterButton() {
        enterButton.perform(click());
    }
}