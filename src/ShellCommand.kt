//this is where commands are defined
sealed class ShellCommand {
    //object
    object ShellHistory:ShellCommand()
    object ShellExit:ShellCommand()
    object ShellDir:ShellCommand()
    //class
    data class ShellChangeDir(val path:String):ShellCommand()
    data class ShellListDir(val path:String):ShellCommand()
    data class UnknownCommand(val cmd:String):ShellCommand()
}