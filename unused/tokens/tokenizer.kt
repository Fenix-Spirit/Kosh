package tokens

fun tokenize(input: String): List<token> {
    val tokens= mutableListOf<token>()
    var i=0
    while (i<input.length) {
        val c=input[i]
        when{
            c.isWhitespace() -> i++
            c=='-' -> {
                val end=input.indexOfFirst { it.isWhitespace() }.takeIf { it != -1 } ?: input.length
                val strAdd=input.substring(i+1,end)
                tokens.add(token(tokenType.FLAG,strAdd))
                i=end
            }
            c=='"' -> {
                val end=input.indexOf('"',i+1)
                val strAdd=input.substring(i+1,end)
                tokens.add(token(tokenType.STRING,strAdd))
                i=end+1
            }
            c=='|'->{
                tokens.add(token(tokenType.PIPE,"|"))
                i++
            }
            else -> {
                val end=input.indexOfFirst { it.isWhitespace() }.takeIf { it != -1 } ?: input.length
                val strAdd=input.substring(i,end)
                tokens.add(token(tokenType.WORD,strAdd))
                i=end
            }
        }
    }
    return tokens
}