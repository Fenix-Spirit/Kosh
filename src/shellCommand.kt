//this is where commands are defined
sealed class shellCommand {
    //object
    object ShellHistory:shellCommand()
    object ShellClear:shellCommand()
    object ShellExit:shellCommand()
    //class
    data class ShellChangeDir(val path:String):shellCommand()
    data class ShellListDir(val path:String):shellCommand()
    data class UnknownCommand(val cmd:String):shellCommand()
}