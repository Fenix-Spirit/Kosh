package commands

fun parse(input: String): Command {
    val parts = input.split(" ")
    val name = parts[0]
    val args = parts.drop(1)
    val flags = mutableListOf<String>()
    for (arg in args) {
        if (arg.startsWith("-")) {
          flags.add(arg)

        }
    }
    return Command(name, flags, args, input)
}