object RulesOutput {
  def showRules(): Unit = {
    println(
      """
        |GAME RULES:
        |Before starting game you need to register users for playing.
        |For start registration process - put 'play' to console.
        |Then - you need to set number of players and names for players.
      """.stripMargin)
  }
}
