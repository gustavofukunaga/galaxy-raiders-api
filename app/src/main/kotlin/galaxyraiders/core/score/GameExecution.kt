package galaxyraiders.core.score
import java.io.File

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
}
