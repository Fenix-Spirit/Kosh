# Kösh - Kotlin shell
## Info
Extra arguments will be `ignored`
## Adding a Command
To add a new command to Kösh, you need to update three main areas:

1.  **`src/ShellCommand.kt`**: Add a new `object` or `data class` to the `ShellCommand` sealed class to represent your command and provide a description.
2.  **`src/Shell.kt`**:
    *   Update the `prepare` method to map the command name (from the `Command` object) to your new `ShellCommand`.
    *   Update the `execute` method to implement the logic for your new command and add it to the `help` command's command list.

### Example
If you want to add a `hello` command:
1. In `ShellCommand.kt`, add `object ShellHello : ShellCommand() { override val description: String = "hello: print hello world" }`.
2. In `Shell.kt`'s `prepare`, add `"hello" -> ShellCommand.ShellHello`.
3. In `Shell.kt`'s `execute`:
    * Add `is ShellCommand.ShellHello -> println("Hello World!")`.
    * In the `ShellCommand.ShellHelp` block, add `ShellCommand.ShellHello` to the `commands` list.
