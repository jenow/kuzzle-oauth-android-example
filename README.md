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

- Run Kuzzle using docker

```sh
cd ../../
docker-compose -f docker-compose/dev.yml up
```
