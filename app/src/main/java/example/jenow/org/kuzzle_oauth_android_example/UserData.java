package example.jenow.org.kuzzle_oauth_android_example;

import org.json.JSONObject;

public class UserData {
    private String name;
    private String avatar;
    private String email;
    private String bio;

    public UserData(final String name, final String avatar, final String email, final String bio) {
        this.name = name;
        this.avatar = avatar;
        this.email = email;
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }
}
