import commands.*
fun main() {
    val shell= Shell()
    while (true) {
        print("Kösh>>")
        val input:String = readln().trim()
        val cmd: Command = parse(input)
        shell.execute(cmd)
    }
}