package codechamp.flashcard;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {
    private Handler _handler = new Handler();

    public void startApp() {
        final Context _this = this;
        Runnable switchActivity = new Runnable() {
            @Override
            public void run() {
                Intent welcomeActivity = new Intent(_this, WelcomeActivity.class);
                _this.startActivity(welcomeActivity);
            }
        };

        _handler.postDelayed(switchActivity, 3000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        this.startApp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        finish();
    }
}
