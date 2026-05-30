import commands.Command

class Shell {
    private val history: MutableList<String> = mutableListOf()
    private var currentDir: String = "/"
    fun execute(cmd: Command) {
        history.add(cmd.name+" "+cmd.args.joinToString(" "))
        return when (cmd.name) {
            "ChangeDir" -> {
                currentDir = cmd.args[0]
            }
            "ListDir" -> {

            }
            else -> println("unknown command")
        }
    }
}