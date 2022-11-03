package de.appplant.cordova.plugin.notification.receiver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


/**
 * Android 12 does not allow BroadcastReceiver or Services to start Activities from background.
 * This is known as Notification Trampoline
 * https://developer.android.com/about/versions/12/behavior-changes-12#notification-trampolines
 * <p>
 * This plugin sends click actions to be handled by IntentService, which then starts an activity.
 * This class replaces the IntentService as the base class for ClickReceiver.
 * This way the click immediately starts an activity.
 * <p>
 * Similar approach to PushHandlerActivity from push plugin
 */
public class NotificationTrampolineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.launchApp();
    }

    /**
     * Launch main intent from package.
     */
    private void launchApp() {
        Context context = getApplicationContext();
        String pkgName = context.getPackageName();

        Intent intent = context.getPackageManager().getLaunchIntentForPackage(pkgName);
        if (intent == null)
            return;

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_FROM_BACKGROUND);
        context.startActivity(intent);
    }
}