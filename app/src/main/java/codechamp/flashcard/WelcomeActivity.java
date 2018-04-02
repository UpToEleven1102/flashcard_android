package codechamp.flashcard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import codechamp.flashcard.infrastructure.KeyHolder;
import codechamp.flashcard.infrastructure.Mode;
import codechamp.flashcard.infrastructure.SettingValues;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void login(View view) {
        Intent logInIntent = new Intent(this, LoginActivity.class);
        startActivity(logInIntent);
    }

    public void offlineMode(View view) {
        Intent dashboardIntent = new Intent(this, DashboardActivity.class);
        dashboardIntent.putExtra(KeyHolder.onlineModeKey, false);
        SettingValues.mode = Mode.OFFLINE;
        startActivity(dashboardIntent);
    }
}
