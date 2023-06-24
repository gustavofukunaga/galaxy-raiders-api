package galaxyraiders.core.score
import java.io.File

data class GameExecution(
  val startTime: String,
  val score: Double,
  val destroyedAsteroids: Int,
) {
  fun addToScoreboard() {
    val json = buildString {
      append("[") // Abre a lista de execuções

      append("{")
      append("\"startTime\": \"${startTime}\", ")
      append("\"score\": ${score}, ")
      append("\"destroyedAsteroids\": ${destroyedAsteroids}")
      append("},")

      append("]") // Fecha a lista de execuções
    }

  // Salvar o JSON em um arquivo
  val file = File("game_history.json")
  file.writeText(json)
  }
}
