package dev.dowell.chess.backend

import dev.dowell.chess.Game
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@RestController
@RequestMapping("/games")
class GameController(private val gameRepository: GameRepository) {

    @PostMapping
    @RequestMapping("/new")
    fun createNewGame(): ResponseEntity<String> {
        return ResponseEntity.ok(gameRepository.createGame())
    }

    @GetMapping("/{id}")
    fun getGameWithId(@PathVariable id: String): ResponseEntity<Game> {
        gameRepository.findGameWithId(id)?.let {
            return ResponseEntity.ok(it)
        }

        return ResponseEntity.notFound().build()
    }
}

@Component
class MemoryGameRepository : GameRepository {
    private val database: ConcurrentHashMap<String, Game> = ConcurrentHashMap()

    override fun findGameWithId(id: String): Game? {
        return database[id]
    }

    override fun createGame(): String {
        val uuid = UUID.randomUUID().toString()
        database[uuid] = Game()
        return uuid
    }
}

interface GameRepository {

    fun findGameWithId(id: String): Game?
    fun createGame(): String
}
