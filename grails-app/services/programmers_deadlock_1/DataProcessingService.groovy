package programmers_deadlock_1

import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional

import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedTransferQueue

@Transactional
@GrailsCompileStatic
class DataProcessingService {
    public final static int MAX_BUFFER_SIZE = 5
    public final static String DEFAULT_HASH = '0'

    private Map<String, Block> datastore = new LinkedHashMap<>()
    private BlockingQueue<String> dataBuffer = new LinkedTransferQueue()

    def addData(String data) {
        if (!data)
            return false
        dataBuffer.put(data)
        if (dataBuffer.size() >= MAX_BUFFER_SIZE) {
            def rows = []
            dataBuffer.drainTo(rows, MAX_BUFFER_SIZE)
            flushCacheToDatastore(rows)
        }
        return true
    }

    private void flushCacheToDatastore(List<String> rows) {
        Block lastBlock = null
        if (!datastore.isEmpty())
            lastBlock = datastore.entrySet().last().value
        def key = sha256Hash(rows)
        Block newBlock = new Block(
                previousBlockHash: lastBlock?.blockHash ?: DEFAULT_HASH,
                rows: rows,
                blockHash: key
        )
        datastore.put(key, newBlock)
    }

    private String sha256Hash(List<String> rows) {
        rows.join('').encodeAsSHA256().toString()
    }

    List<Block> lastBlocks(int count) {
        if (count <= 0 || datastore.isEmpty())
            return []
        List<Block> result = []
        def last = datastore.entrySet()?.last()?.value
        if (last) {
            result << last
            while (last.previousBlockHash != DEFAULT_HASH) {
                result << (last = datastore.get(last.previousBlockHash))
            }
        }
        return result.reverse()
    }
}
