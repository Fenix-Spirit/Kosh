package commands

data class Command(
    val name: String,
    val flags: List<String>,
    val args: List<String>,
    val raw:String
)