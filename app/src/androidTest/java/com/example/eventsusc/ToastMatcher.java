package com.example.eventsusc;

import android.os.IBinder;
import android.view.WindowManager;
import androidx.test.espresso.Root;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ToastMatcher extends TypeSafeMatcher<Root> {

    public static Matcher<Root> isToast() {
        return new ToastMatcher();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("is a toast");
    }

    @Override
    public boolean matchesSafely(Root root) {
        IBinder windowToken = root.getDecorView().getWindowToken();
        IBinder appToken = root.getDecorView().getApplicationWindowToken();
        return windowToken == appToken && isToastWindow(root);
    }

    private boolean isToastWindow(Root root) {
        WindowManager.LayoutParams layoutParams =
                (WindowManager.LayoutParams) root.getDecorView().getLayoutParams();
        return layoutParams.type == WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
    }
}

