import commands.Command
import kotlinx.cinterop.ExperimentalForeignApi
import kotlin.experimental.ExperimentalNativeApi
import kotlinx.cinterop.toKString
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readLine
import kotlinx.io.writeString
import platform.posix.getenv
import kotlin.system.exitProcess
@OptIn( ExperimentalForeignApi::class,ExperimentalNativeApi::class)
class Shell {
    private val root: Path? = when (Platform.osFamily) {
        OsFamily.LINUX, OsFamily.MACOSX -> getenv("HOME")?.toKString()?.let { Path("$it/.kosh") }
        OsFamily.WINDOWS -> getenv("APPDATA")?.toKString()?.let { Path("$it/.kosh") }
        else -> null
    }
    private val histPath=Path(root?: Path("./"),".kosh-history")
    private val history: MutableMap<String, String> = mutableMapOf()
    private var currentDir: String = "/"
    init {
        if (SystemFileSystem.exists(root?: Path("./"))) {
            if(SystemFileSystem.exists(histPath)){
                try {
                    SystemFileSystem.source(histPath).buffered().use {source ->
                        while (!source.exhausted()){
                            val line=source.readLine()
                            if (line!=null) {
                                history[line.split("-->")[0]] = line.split("-->").drop(1).joinToString(" ")
                            }
                        }
                        source.close()
                    }
                }
                catch (e:Exception){
                    println("Error reading history file")
                }
            }
            else{
                SystemFileSystem.createDirectories(histPath)
            }
        }
        else {
            SystemFileSystem.createDirectories(root?: Path("./.kosh"))
        }
    }
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
                val path=Path(prepared.path)
                SystemFileSystem.list(path).forEach { println(it.name) }
            }
            is ShellCommand.ShellDir -> println(currentDir)
            is ShellCommand.ShellPrint -> println(prepared.msg)
            is ShellCommand.UnknownCommand -> println("Unknown command: ${prepared.cmd}")
        }
        val currTime=Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        history[currTime.toString()] = cmd.raw
        SystemFileSystem.sink(histPath,true).buffered().use {sink ->
            sink.writeString(currTime.toString()+" --> ${cmd.raw}\n")
        }
    }
}