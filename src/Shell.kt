import commands.Command
import java.time.LocalDateTime
import kotlin.system.exitProcess

class Shell {
    private val history: MutableMap<String, String> = mutableMapOf()
    private var currentDir: String = "/"
    fun prepare(cmd: Command):ShellCommand = when (cmd.name) {
        "quit","exit" -> ShellCommand.ShellExit
        "history"->ShellCommand.ShellHistory
        "ldir" ->ShellCommand.ShellListDir(if (cmd.args.isEmpty()) currentDir else cmd.args[0])
        "cdir" ->ShellCommand.ShellChangeDir(cmd.args[0])
        "pwd" ->ShellCommand.ShellDir
        else -> ShellCommand.UnknownCommand(cmd.name)
    }
    fun execute(prepared:ShellCommand,cmd: Command ){
        when(prepared){
            is ShellCommand.ShellExit -> exitProcess(0)
            is ShellCommand.ShellHistory -> {
                for ((key,value) in history) {
                    println("$key --> $value")
                }
            }
            is ShellCommand.ShellChangeDir -> currentDir=prepared.path
            is ShellCommand.ShellListDir -> {
                val dir=java.io.File(prepared.path)
                dir.listFiles()?.forEach { println(it.name) } ?: println("No such directory/Directory is empty")
            }
            is ShellCommand.ShellDir -> println(currentDir)
            is ShellCommand.UnknownCommand -> println("Unknown command: ${prepared.cmd}")
        }
        history[(LocalDateTime.now()).toString()] = cmd.raw
    }
}