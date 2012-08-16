package grails.plugin.dynamic.parameters.services

import grails.converters.JSON
import grails.plugin.dynamic.parameters.http.RestClientRequest
import grails.plugin.dynamic.parameters.http.RestClientResponse
import grails.plugin.dynamic.parameters.http.client.RestClient
import grails.plugin.dynamic.parameters.utils.DynamicParametersUtils

/**
 * @author mlabarinas
 */
class RestDynamicParametersService extends RestClient {

    def doGetServersToMeliCloudApiGet(pool) {
        log.debug("Do get to get servers to reload parameters from pool $pool")

        try {
            RestClientRequest request = new RestClientRequest(DynamicParametersUtils.getMeliCloudApiBaseUrl().replace('##POOL##', pool))
            RestClientResponse response = super.doGet(request)

            switch(response.getStatusCode()) {
                case 200:
                    return DynamicParametersUtils.convertJsonToNativeObject(response.getJson())

                default:
                    return []
            }


        } catch(Exception e) {
            log.error("Error doing get to get servers to reload parameters from pool $pool", e)
            return []
        }

    }

    def doReloadParametersPost(server, body) {
        log.debug("Do post to reload parameters to server $server")

        try {
            RestClientRequest request = new RestClientRequest(DynamicParametersUtils.getParametersBaseUrl().replace('##SERVER##', server), (body as JSON).toString())

            if(DynamicParametersUtils.isSecurityOn()) {
                request.addParameter("token", DynamicParametersUtils.getSecurityToken())
            }

            RestClientResponse response = super.doPost(request)

            switch(response.getStatusCode()) {
                case 200:
                    return true

                default:
                    return false
            }

        } catch(Exception e) {
            log.error("Error doing post to reload parameter to server $server", e)
            return false
        }
    }

}