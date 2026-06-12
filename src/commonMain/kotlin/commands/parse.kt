package commands

fun String.parse(): Command {
    val parts = this.split(" ")
    val name = parts[0]
    val args = parts.drop(1)
    val flags = mutableListOf<String>()
    for (arg in args) {
        if (arg.startsWith("-")) {
          flags.add(arg)
        }
    }
    return Command(name, flags, args, this)
}