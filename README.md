# goose-game

## How ro run
 1. Open a console inside of the root folder(goose-game)
 2. Run `sbt run` to start the application
 
## Sources
All sources are placed inside of the `./src/main/scala/` folder


(un)expected behaviour to handle:

- DONE check if name doesn't yet exist
- ? check if moving input is correct, else give a hint: "To move a player type "Move name""
- ? check if "move $name" is a registered name
- ? check if it's the turn of the user from the input, else give user a hint
- NOT DONE add comments

additional features if we still have time tomorrow:

- provide a way to finish the game anytime - ask if the user wants to restart or just quit the app
- DONE provide a game description/rules on "about" input
- ? tests
- DONE randomize who would be the first to roll
- DONE support from 2 to 6 players

- ? validate with Eithers
