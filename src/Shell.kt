import commands.Command
import java.time.LocalDateTime
import kotlin.system.exitProcess

class Shell {
    private val history: MutableList<String> = mutableListOf()
    private var currentDir: String = "/"
    fun prepare(cmd: Command):ShellCommand = when (cmd.name) {
        "quit","exit" -> ShellCommand.ShellExit
        "history"->ShellCommand.ShellHistory
        "ldir" ->ShellCommand.ShellListDir(currentDir)
        "cdir" ->ShellCommand.ShellChangeDir(cmd.args[0])
        else -> ShellCommand.UnknownCommand(cmd.name)
    }
    fun execute(prepared:ShellCommand,cmd: Command ){
        when(prepared){
            is ShellCommand.ShellExit -> exitProcess(0)
            is ShellCommand.ShellChangeDir -> currentDir=prepared.path
            is ShellCommand.ShellHistory -> {
                for (h in history) {
                    println(h)
                }
            }
            is ShellCommand.ShellListDir -> {}
            is ShellCommand.UnknownCommand -> println("Unknown command: ${prepared.cmd}")
        }
        history.add((LocalDateTime.now()).toString()+" >>> "+cmd.raw)
    }
}