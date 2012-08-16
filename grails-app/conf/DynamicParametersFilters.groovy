import grails.plugin.dynamic.parameters.utils.DynamicParametersUtils
import grails.util.Environment

/**
 * @author mlabarinas
 */
class DynamicParametersFilters {

    def filters = {
        console(controller: 'dynamicParameters', action: '*') {
            before = {
                if(Environment.current == Environment.PRODUCTION) {
                    if(DynamicParametersUtils.isSecurityOn()) {
                        return DynamicParametersUtils.getSecurityToken().equals(request.token)

                    } else {
                        return true
                    }
                }

                return true
            }
        }
    }

}