sealed class ShellCommand {
    abstract val description:String
    //object
    object ShellHistory:ShellCommand() {
        override val description: String="history: show command history"
    }
    object ShellExit:ShellCommand(){
        override val description: String="exit/quit: exit the shell"
    }
    object ShellDir:ShellCommand(){
        override val description: String="pwd: show current directory"
    }
    object ShellHelp:ShellCommand(){
        override val description: String="help: show this help message"
    }
    //class
    data class ShellChangeDir(val path:String):ShellCommand(){
        override val description: String="cdir <path>: change current directory"
    }
    data class ShellListDir(val path:String):ShellCommand(){
        override val description: String="ldir <path?>: list directory contents"
    }
    data class UnknownCommand(val cmd:String):ShellCommand(){
        override val description: String="unknown command"
    }
    data class CommandError(val msg:String):ShellCommand(){
        override val description: String="command error"
    }
    data class ShellPrint(val msg:String):ShellCommand(){
        override val description: String="print <msg>: prints message"
    }
}