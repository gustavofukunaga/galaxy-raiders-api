package galaxyraiders.core.score
import java.io.File
import java.time.LocalDateTime

data class GameExecution(
  val startTime: String,
  var score: Double,
  var destroyedAsteroids: Int,
  var added: Boolean = false
) {
  fun addToScoreboard() {
    val path = "src/main/kotlin/galaxyraiders/core/score/Scoreboard.json"
    val file = File(path)
    if (!file.exists())
      this.addNewJson(path)
    else
      this.addExecToJson(path)
    this.added = true
  }

  fun addNewJson(path: String) {
    val file = File(path)
    file.createNewFile()
    val json = buildString {
      append("[") // Abre a lista de execuções

      append("{")
      append("\"startTime\": \"${startTime}\", ")
      append("\"score\": ${score}, ")
      append("\"destroyedAsteroids\": ${destroyedAsteroids}")
      append("}")

      append("]") // Fecha a lista de execuções
    }

    // Salvar o JSON em um arquivo
    file.writeText(json)
  }

  fun addExecToJson(path: String) {
    val file = File(path)
    val content = file.readText()
    
    val json = buildString {
      append("{")
      append("\"startTime\": \"${startTime}\", ")
      append("\"score\": ${score}, ")
      append("\"destroyedAsteroids\": ${destroyedAsteroids}")
      append("}")

      append("]") // Fecha a lista de execuções
    }
    
    var newContent = String()
    if (this.added)
      newContent = content.substringBeforeLast("{") + json
    else
      newContent = content.substring(0, content.lastIndexOf("]")) + ", " + json
    // Salvar o JSON em um arquivo
    file.writeText(newContent)
  }

  fun updateScore(newScore: Double, newDestroyedAsteroids: Int) {
    this.score = newScore
    this.destroyedAsteroids = newDestroyedAsteroids
  }

  fun addToLeaderboard() {
    val scoreFile = File("src/main/kotlin/galaxyraiders/core/score/Scoreboard.json")
    val scores = scoreFile.readText()
    
    val gameDataList = parseJson(scores)

    val sortedList = gameDataList.sortedByDescending { it.score }
    val sortedTop3 = sortedList.subList(0, 3)

    var content = "["
    for (gameData in sortedTop3) {
        content += "{\"startTime\": \"${gameData.startTime}\",  \"Score\": ${gameData.score}"
        content += ", \"destroyedAsteroids\": ${gameData.destroyedAsteroids}}, "
    }

    val newContent = content.substring(0, content.lastIndexOf(",")) + "]"

    val leaderboardFile = File("src/main/kotlin/galaxyraiders/core/score/Leaderboard.json")
    leaderboardFile.createNewFile()
    leaderboardFile.writeText(newContent)
  }

  data class GameData(val startTime: String, var score: Double, var destroyedAsteroids: Int)
  
  fun parseJson(json: String): List<GameData> {
    val gameDataList = mutableListOf<GameData>()

    val jsonArray = json.trim().removeSurrounding("[", "]").split("},")
    for (item in jsonArray) {
        val trimmedItem = item.trim().removeSurrounding("{", "}")
        val fields = trimmedItem.split(",")
        val startTime = extractFieldValue(fields[0]).substring(1)
        val score = extractFieldValue(fields[1])
        val destroyedAsteroids = extractFieldValue(fields[2])

        val gameData = GameData(startTime, score.toDouble(), destroyedAsteroids.toInt())
        gameDataList.add(gameData)
    }

    return gameDataList
  }

  fun extractFieldValue(field: String): String {
      val fieldValue = field.split(":")[1]
      return fieldValue.trim().removeSurrounding("\"")
  }
}
