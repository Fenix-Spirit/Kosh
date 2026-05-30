package tokens
enum class tokenType {
    WORD,       // "ls"
    FLAG,       // "-l"
    STRING,     // "hello world" <- quoted string
    PIPE        // "|"
}