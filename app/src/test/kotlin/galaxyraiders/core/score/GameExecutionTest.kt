package galaxyraiders.core.score

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

import java.time.LocalDateTime
import java.io.File

@DisplayName("Given an game execution")
class GameExecutionTest {
  private val execution = GameExecution(
    LocalDateTime.now().toString(),
    0.0,
    0
  )

  @Test
  fun `it updates score GameExecution `() {
    execution.updateScore(1.0, 1)

    assertEquals(1.0, execution.score)
    assertEquals(1, execution.destroyedAsteroids)
  }

  @Test
  fun `it adds to Scoreboard json`() {
    execution.addToScoreboard()
    val file = File("src/main/kotlin/galaxyraiders/core/score/Scoreboard.json")

    assertNotEquals(file.length(), 0)
  }

  @Test
  fun `it adds the updated Scoreboard json`() {
    execution.updateScore(1.0, 1)
    execution.updateScore(2.0, 2)
    execution.updateScore(11.0, 101)
    execution.addToScoreboard()

    val file = File("src/main/kotlin/galaxyraiders/core/score/Scoreboard.json")
    assertNotEquals(file.length(), 0)
    assertEquals(11.0, execution.score)
    assertEquals(101, execution.destroyedAsteroids)
  }

  @Test
  fun `it adds to Leaderboard json`() {
    execution.updateScore(2.0, 2)
    execution.addToScoreboard()
    execution.addToLeaderboard()
    val file = File("src/main/kotlin/galaxyraiders/core/score/Leaderboard.json")

    assertNotEquals(file.length(), 0)
  }
}
