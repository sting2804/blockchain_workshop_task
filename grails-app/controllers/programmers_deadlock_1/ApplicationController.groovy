package programmers_deadlock_1

import grails.converters.JSON
import grails.core.GrailsApplication
import grails.plugins.GrailsPluginManager
import grails.plugins.PluginManagerAware

class ApplicationController implements PluginManagerAware {

    GrailsApplication grailsApplication
    GrailsPluginManager pluginManager
    DataProcessingService dataProcessingService

    def index() {
        [grailsApplication: grailsApplication, pluginManager: pluginManager]
    }

    def lastBlocks(int count) {
        render(dataProcessingService.lastBlocks(count) as JSON)
    }

    def addData() {
        render(dataProcessingService.addData(params.get('data').toString()))
    }
}
