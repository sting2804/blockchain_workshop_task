package programmers_deadlock_1

class Block {
    String previousBlockHash = null
    String blockHash
    long timestamp = new Date().time
    List<String> rows = []

    static constraints = {
        previousBlockHash nullable: false
        blockHash nullable: false
        timestamp nullable: false
    }
}
