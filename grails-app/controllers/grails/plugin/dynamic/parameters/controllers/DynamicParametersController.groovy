package grails.plugin.dynamic.parameters.controllers

import javax.servlet.http.HttpServletResponse

/**
 * @author mlabarinas
 */
class DynamicParametersController {

    def dynamicParametersService

    def save = {
        if(dynamicParametersService.reloadParameters(request.JSON)) {
            render status: HttpServletResponse.SC_OK

        } else {
            render status: HttpServletResponse.SC_INTERNAL_SERVER_ERROR
        }
    }

}