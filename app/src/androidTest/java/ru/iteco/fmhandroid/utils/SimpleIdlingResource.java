package ru.iteco.fmhandroid.utils;

import androidx.test.espresso.IdlingResource;

public class SimpleIdlingResource implements IdlingResource {
    private ResourceCallback resourceCallback;
    private boolean isIdle = true; // Статус ожидания (активно или неактивно)

    // Метод для получения имени ресурса
    @Override
    public String getName() {
        return this.getClass().getName();
    }

    // Метод для проверки статуса ожидания
    @Override
    public boolean isIdleNow() {
        return isIdle;
    }

    // Метод для регистрации колбека, который будет вызван при переходе в неактивное состояние
    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        resourceCallback = callback;
    }

    // Метод для установки состояния ожидания
    public void setIdleState(boolean isIdleNow) {
        isIdle = isIdleNow; // Устанавливаем новое значение
        if (isIdle && resourceCallback != null) {
            resourceCallback.onTransitionToIdle(); // Сообщаем об изменении состояния
        }
    }
}