package example.jenow.org.kuzzle_oauth_android_example;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import io.kuzzle.sdk.core.Kuzzle;
import io.kuzzle.sdk.listeners.ResponseListener;

public class LoginActivity extends AppCompatActivity {
    private Handler handler = new Handler();
    private String strategy;
    private Kuzzle kuzzle;


    protected class KuzzleWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, final String url) {
            if (url.contains("code=")) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            HttpURLConnection conn = (HttpURLConnection) URI.create(url).toURL().openConnection();
                            conn.setRequestMethod("GET");
                            conn.setUseCaches(false);
                            Log.e("eee", conn.getResponseMessage());

                            if (conn.getResponseCode() != 200) {
                                BufferedReader br2 = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                                StringBuilder sb2 = new StringBuilder();
                                String line2;
                                while ((line2 = br2.readLine()) != null) {
                                    sb2.append(line2);
                                }
                                br2.close();
                                Log.e("e", sb2.toString());
                            }


                            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            StringBuilder sb = new StringBuilder();
                            String line;
                            while ((line = br.readLine()) != null) {
                                sb.append(line);
                            }
                            br.close();

                            JSONObject response = new JSONObject(sb.toString());
                            if (response.isNull("error")) {
                                Log.e("e", response.toString());
                            } else {
                                Log.e("ee", response.toString());
                            }
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } else {
                view.loadUrl(url);
            }
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        strategy = getIntent().getStringExtra("strategy");

        final WebView wv = findViewById(R.id.webview);
        final View userInfoView = findViewById(R.id.userinfo_view);
        kuzzle = KuzzleSingleton.getKuzzle(getResources().getString(R.string.kuzzle_host));
        kuzzle.login(strategy, new ResponseListener<JSONObject>() {
            @Override
            public void onSuccess(final JSONObject object) {
                Log.e("e", object.toString());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (object.has("headers")) {
                                wv.getSettings().setJavaScriptEnabled(true);
                                wv.setWebViewClient(kuzzle.getKuzzleWebViewClient());
                                wv.loadUrl(object.getJSONObject("headers").getString("Location"));
                            } else {
                                wv.setVisibility(View.GONE);
                                displayCredentials(userInfoView);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onError(JSONObject error) {
                Log.e("error", error.toString());
            }
        });
    }

    @Override
    protected void onDestroy() {
        kuzzle.logout();
        super.onDestroy();
    }

    private UserData fillUserInfoFromProvider(final String provider, final JSONObject profile) throws JSONException {
        UserData userData = null;

        if (provider.equals("facebook")) {
            userData = new UserData(profile.getString("first_name") + " " + profile.getString("last_name"), profile.getJSONObject("picture").getJSONObject("data").getString("url"), profile.getString("email"), "");
        } else if (provider.equals("github")) {
            userData = new UserData(profile.getString("name"), profile.getString("avatar_url"), profile.getString("email"), profile.getString("bio"));
        }

        return userData;
    }

    private void displayCredentials(final View userInfoView) {
        try {
            findViewById(R.id.inprogress_layout).setVisibility(View.VISIBLE);
            kuzzle.getMyCredentials(strategy, new ResponseListener<JSONObject>() {
                @Override
                public void onSuccess(final JSONObject response) {
                    try {
                        final UserData userData = fillUserInfoFromProvider(strategy, response);
                        final Drawable drawable = Drawable.createFromStream((InputStream) new URL(userData.getAvatar()).getContent(), "src");

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.inprogress_layout).setVisibility(View.GONE);
                                userInfoView.setVisibility(View.VISIBLE);
                                ((ImageView)findViewById(R.id.picture)).setImageDrawable(drawable);
                                ((TextView) findViewById(R.id.name)).setText(userData.getName());
                                ((TextView) findViewById(R.id.txtEmail)).setText(userData.getEmail());
                                ((TextView) findViewById(R.id.bio)).setText(userData.getBio());
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(final JSONObject error) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Toast.makeText(LoginActivity.this, error.getString("message"), Toast.LENGTH_LONG).show();
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
