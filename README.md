# kuzzle-oauth-android-example
An example of integration of oauth authentication with Kuzzle on Android

## Prerequisite

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

- Modify the .kuzzlerc in Kuzzle to add the oauth plugin configuration in the `plugins` entry or cp the one which is present here.

For example you can add a strategy like this (facebook example):

```json
"plugins": {
    "kuzzle-plugin-auth-passport-oauth": {
            "strategies": {
                "facebook": {
                    "credentials": {
                        "clientID": "<your client id>",
                        "clientSecret": "<your client secret>",
                        "callbackURL": "http://localhost:8888/#!/facebook"
                    },
                    "persist": [
                        "login",
                        "public_profile",
                        "name",
                        "email"
                    ],
                    "scope": [
                        "email",
                        "public_profile"
                    ]
                }
            }
        }
}
```

Note: This Android application uses facebook, github as an example.

- Run Kuzzle using docker

```sh
cd ../../
docker-compose -f docker-compose/dev.yml up
```

## Android application