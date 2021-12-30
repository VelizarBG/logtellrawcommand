# Logtellraw Command

`/logtellraw` is a command similar to `/tellraw`. It allows for the same input `/tellraw` does but additionally logs the output as a string to either the server console or the game output. If the mod is installed on the server only the console will receive logs and not the players regardless of them having the mod or not. If the mod is installed on the client the game output will only receive logs in singleplayer worlds.

## Syntax
`/logtellraw (targets <targets>|targetless) <message>`

## Usage
`/logtellraw targets <targets> <message>` allows you to send a message to the selected players and log the output. Logs even when no targets are found.

`/logtellraw targetless <message>` allows you to log the output without sending a message to any players.

## Compiling
* Clone the repository
* Open a command prompt/terminal to the repository directory
* Run 'gradlew build'
* The built jar file will be in build/libs/
