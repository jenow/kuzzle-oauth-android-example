package example.jenow.org.kuzzle_oauth_android_example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KuzzleSingleton.getKuzzle(getResources().getString(R.string.kuzzle_host));

        findViewById(R.id.fb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);

                loginIntent.putExtra("strategy", "facebook");
                startActivity(loginIntent);
            }
        });

        findViewById(R.id.fb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);

                loginIntent.putExtra("strategy", "twitter");
                startActivity(loginIntent);
            }
        });
    }
}
