# Full stack kmp project template

Kotlin version : 1.6.10

## Project Structure

`models` - holds the shared models between the client and server
`server` - server code using ktor and build as an executeable jar
`shared` - shared client code which holds all the business logic and communication with the server
`sharedCompose` - shared client UI code using jetbrains compose framework and Decompose 
`desktopApp` - Desktop implementation using jetbrains compose
`androidApp` - Android implementation using jetbrains compose
`jsApp` - Web implementation using jetbrains compose* Not fully functioning
`iosApp` - iOS implementation using swift ui *TODO*

### Running the project
#### Android client
place the `server.url` property in your `local.properties`
Use `server.url=mock` to run without the server.
#### Desktop client
TODO: feed server url from build property
`./gradlew desktop:run`
#### Server
`./gradlew server:shadowJar` - TODO started to fail for some reason
