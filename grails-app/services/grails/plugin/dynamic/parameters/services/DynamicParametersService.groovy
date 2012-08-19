package grails.plugin.dynamic.parameters.services

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CFG

import grails.converters.JSON
import grails.plugin.dynamic.parameters.utils.DynamicParametersUtils

/**
 * @author mlabarinas
 */
class DynamicParametersService {

    def restDynamicParametersService
    def reloadConfigService

    def reloadParameters(request) {
        log.debug("Start to process request to reload parameters")

        try {
           Map jsonMap = DynamicParametersUtils.convertJsonToNativeObject(request.JSON)

           log.debug("Json to reload: ${jsonMap.toString()}")

           if(!jsonMap.empty) {
               DynamicParametersUtils.saveFileParameters("parameters = ${DynamicParametersUtils.getMapString(jsonMap as JSON)}".toString())
           }

           log.debug("Force reload parameters now")
           reloadConfigService.checkNow()

           return true

        } catch(Exception e) {
            log.error('Error reload parameters from json', e)
            return false
        }
    }

    def reloadParameters(Closure action) {
        reloadParameters(action, System.getenv("SCOPE"))
    }

    def reloadParameters(Closure action, String pool) {
        reloadParameters(action, [pool])
    }

    def reloadParameters(Closure action, List pools) {
        log.debug("Start to process closure to reload parameters in pools ${pools.toString()}")

        try {
            action()

            log.debug("Parameters load in runtime config OK")

            Map parameters = CFG.config.parameters

            def result = noticeReloadParametersToServers(parameters, pools)

            if(evaluateResult(result)) {
                return 'Success reload parameters'

            } else {
                return "Error reload parameters, servers: ${result.toString()}"
            }

        } catch(Exception e) {
            log.error('Error reload parameters from closure', e)
            return 'Error reload parameters from closure'
        }
    }

    def noticeReloadParametersToServers(Map parameters, List pools) {
        def result = []

        log.debug("Start notice to servers")

        try {
            if(DynamicParametersUtils.isPoolsOn()) {
                pools.each { pool ->
                    restDynamicParametersService.doGetServersToMeliCloudApiGet(pool).each { server ->
                        def attempt = 0
                        def retry = true
                        def reload = true

                        while(retry && (attempt < DynamicParametersUtils.getRestCallFailRetries())) {
                            if(restDynamicParametersService.doReloadParametersPost(server, parameters)) {
                                reload = true
                                retry = false

                            } else {
                                reload = false
                                attempt++
                            }
                        }

                        result << ([server: server] << [reload: reload])
                    }
                }
            }

        } catch(Exception e) {
            log.error('Error reload parameters from map', e)
        }

        return result
    }

    private def evaluateResult(result) {
        def r = true

        result.each {
            if(!it.reload) {
                r = false
            }
        }

        return r
    }

}