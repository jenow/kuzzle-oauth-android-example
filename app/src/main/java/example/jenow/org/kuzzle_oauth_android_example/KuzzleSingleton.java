package example.jenow.org.kuzzle_oauth_android_example;

import java.net.URISyntaxException;

import io.kuzzle.sdk.core.Kuzzle;

public class KuzzleSingleton {
    private static Kuzzle kuzzle;

    protected KuzzleSingleton(){}

    public static Kuzzle getKuzzle(String host) {
        if (kuzzle == null) {
            try {
                kuzzle = new Kuzzle(host);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return kuzzle;
    }
}
