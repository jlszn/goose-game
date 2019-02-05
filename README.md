# goose-game

## How ro run
 1. Open a console inside of the root folder(goose-game)
 2. Run `sbt run` to start the application
 3. Run `sbt test` to start tests
 
## Sources
All sources are placed inside of the `./src/main/scala/` folder

## Internal Structure

 1. CommandProcessor - util class that includes processing of initial commands
 2. GooseGame - the main class. Includes methods used for playing(play, move roll)
 3. PlayerRegistrationUtil - util class that includes user creation logic
 4. RandomUtil - util class that includes dice logic
 5. Rules - contains game configurations.
 6. game package object contains aliases for types
 7. Input contains user input processing classification