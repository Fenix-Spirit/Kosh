import commands.Command
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
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
        "print" ->ShellCommand.ShellPrint(cmd.args.joinToString(" "))
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
            is ShellCommand.ShellPrint -> println(prepared.msg)
            is ShellCommand.UnknownCommand -> println("Unknown command: ${prepared.cmd}")
        }
        history[(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())).toString()] = cmd.raw
    }
}