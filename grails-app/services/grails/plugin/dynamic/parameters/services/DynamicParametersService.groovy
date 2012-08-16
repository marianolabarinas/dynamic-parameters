package grails.plugin.dynamic.parameters.services

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CFG

import grails.plugin.dynamic.parameters.utils.DynamicParametersUtils

/**
 * @author mlabarinas
 */
class DynamicParametersService {

    def restDynamicParametersService

    def reloadParameters(Closure action) {
        try {
            action()

            return 'Success reload parameters'

        } catch(Exception e) {
            log.error('Error reload parameters from closure', e)
            return 'Error reload parameters from closure'
        }
    }

    def reloadParameters(json) {
        try {
           Map jsonMap = DynamicParametersUtils.convertJsonToNativeObject(json)

           if(!jsonMap.empty) {
               DynamicParametersUtils.saveFileParameters("parameters = ${jsonMap.toString()}".toString())
           }

           return true

        } catch(Exception e) {
            log.error('Error reload parameters from json', e)
            return false
        }
    }

    def reloadParameters(Closure action, pool) {
        try {
            action()

            Map parameters = CFG.config.parameters

            def result = reloadParameters(parameters, pool)

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

    def reloadParameters(Map parameters, pool) {
        def result = []

        try {
            if(DynamicParametersUtils.isPoolsOn()) {
                restDynamicParametersService.doGetServersToMeliCloudApiGet(pool).each {
                    def attempt = 0
                    def retry = true
                    def reload = true

                    while(retry && (attempt < DynamicParametersUtils.getRestCallFailRetries())) {
                        if(restDynamicParametersService.doReloadParametersPost(it, parameters)) {
                            reload = true
                            retry = false

                        } else {
                            reload = false
                            attempt++
                        }
                    }

                    result << ([server: it] << [reload: reload])
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