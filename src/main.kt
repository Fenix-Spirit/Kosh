import commands.*
fun main() {
    val shell= Shell()
    while (true) {
        print("Kösh>>")
        val input:String = readln().trim()
        val cmd: Command = parse(input)
        val res:ShellCommand=shell.prepare(cmd)
        shell.execute(res,cmd)
    }
}