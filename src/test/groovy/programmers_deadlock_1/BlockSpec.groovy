package programmers_deadlock_1

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class BlockSpec extends Specification implements DomainUnitTest<Block> {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}
