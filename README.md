# kuzzle-oauth-android-example
An example of integration of oauth authentication with Kuzzle (https://kuzzle.io) on Android

## Prerequisite

- Clone Kuzzle from github

```sh
git clone git@github.com:kuzzleio/kuzzle.git
```

- Clone the [Kuzzle oauth plugin](https://github.com/kuzzleio/kuzzle-plugin-auth-passport-oauth) inside `plugins/available`:

```sh
cd kuzzle/plugins/available
git clone git@github.com:kuzzleio/kuzzle-plugin-auth-passport-oauth.git
```

- Make a symbolic link to `plugins/enabled`

```sh
cd ../enabled
ln -s ../available/kuzzle-plugin-auth-passport-oauth .
```

- Modify the .kuzzlerc in Kuzzle to add the oauth plugin configuration in the `plugins` entry.

For example you can add a strategy like this (facebook example):

```json
"plugins": {
  "kuzzle-plugin-auth-passport-oauth": {
        "strategies": {
          "github": {
              "credentials": {
                "clientID": "<your client id>",
                "clientSecret": "<your client secret>",
                "callbackURL": "http://<kuzzle host>:7512/_login/github"
              },
              "persist": [
                "id",
                "name",
                "avatar_url",
                "email",
                "bio"
              ],
              "scope": [
                "email"
              ],
              "identifierAttribute": "id",
              "defaultProfiles": ["default"]
            },
            "facebook": {
                "credentials": {
                    "clientID": "<your client id>",
                    "clientSecret": "<your client secret>",
                    "callbackURL": "http://<kuzzle host>:7512/_login/facebook",
                    "profileFields": ["id", "first_name", "last_name", "picture.type(large)", "email", "about"]
                },
                "persist": [
                    "id",
                    "email",
                    "picture",
                    "about",
                    "first_name",
                    "last_name"
		        ],
                "scope": [
                    "email",
                    "public_profile"
                ],
                "identifierAttribute": "id",
                "defaultProfiles": [
                    "default"
                ]
            }
        }
    }
}
```

Note: This Android application uses facebook and github as an example.

- Run Kuzzle using docker

```sh
cd ../../
docker-compose -f docker-compose/dev.yml up
```

## Android application

This application make you login through facebook and github then show some information about your profile.

![](https://github.com/jenow/kuzzle-oauth-android-example/raw/master/assets/main.png)
![](https://github.com/jenow/kuzzle-oauth-android-example/raw/master/assets/facebook.png)
![](https://github.com/jenow/kuzzle-oauth-android-example/raw/master/assets/post_facebook.png)
![](https://github.com/jenow/kuzzle-oauth-android-example/raw/master/assets/github.png)
![](https://github.com/jenow/kuzzle-oauth-android-example/raw/master/assets/post_github.png)
