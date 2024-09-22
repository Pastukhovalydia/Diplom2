package ru.iteco.fmhandroid.utils;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import androidx.test.espresso.matcher.BoundedMatcher;

public class RecyclerViewUtils {

    // Метод для получения Matcher для элемента в RecyclerView по позиции
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
                    return false; // Проверка  позиции
                }

                View view = recyclerView.getChildAt(position);
                if (view == null) {
                    return false; // Если элемент не найден
                }

                View targetView = view.findViewById(targetViewId);
                return targetView != null && targetView.getVisibility() == View.VISIBLE; // Проверка видимости
            }
        };
    }
}