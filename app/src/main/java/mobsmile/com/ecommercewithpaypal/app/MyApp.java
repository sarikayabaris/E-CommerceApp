package mobsmile.com.ecommercewithpaypal.app;

import android.app.Application;

import com.rey.material.app.ThemeManager;

/**
 * Created by Baris on 03.9.2015.
 * Application class of Android Project
 * @author Baris Sarikaya
 * @version 1.0
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ThemeManager.init(this, 2, 0, null);
    }
}
