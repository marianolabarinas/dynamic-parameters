package grails.plugin.dynamic.parameters.utils

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CFG

import net.sf.json.JSONNull
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject
import org.codehaus.groovy.grails.web.metaclass.BindDynamicMethod

/**
 * @author mlabarinas
 */
class DynamicParametersUtils {

    static def isSecurityOn() {
        return  CFG.config.dynamic.parameters.security.on
    }

    static def getSecurityToken() {
        return  CFG.config.dynamic.parameters.security.token
    }

    static def isPoolsOn() {
        return  CFG.config.dynamic.parameters.pools.on
    }

    static def getPoolConfiguration(pool) {
        return CFG.config.dynamic.parameters.pools.configuration."$pool"
    }

    static Integer getRestDefaultConnectionTimeout() {
        return CFG.config.dynamic.parameters.rest.default.connection.timeout
    }

    static Integer getRestDefaultSocketTimeout() {
        return CFG.config.dynamic.parameters.rest.default.socket.timeout
    }

    static def getParametersFilePath() {
        return CFG.config.dynamic.parameters.file.path
    }

    static def getParametersBaseUrl() {
        return CFG.config.dynamic.parameters.base.url
    }

    static def getMeliCloudApiBaseUrl() {
        return CFG.config.dynamic.parameters.base.melicloud.api.url
    }

    static def getRestCallFailRetries() {
        return CFG.config.dynamic.parameters.rest.call.fail.retries
    }

    static def saveFileParameters(parameters) {
        (new File(DynamicParametersUtils.getParametersFilePath())).withWriter { it << parameters.toString() }
    }

    static def convertJsonToNativeObject(jsonObject) {
        def result = null

        if (jsonObject != null) {
            if ((jsonObject instanceof JSONObject && !jsonObject.equals(JSONObject.NULL)) || jsonObject instanceof Map){
                String c = jsonObject.'class'
                String e = jsonObject.'enumType'

                if(c) {
                    result = Class.forName(c, true, Thread.currentThread().contextClassLoader).newInstance()
                    jsonObject.remove('class')
                    DynamicParametersUtils.bind(result, DynamicParametersUtils.convertJsonToNativeObject(jsonObject))

                } else if(e) {
                    def ec = Class.forName(e, true, Thread.currentThread().contextClassLoader)
                    result = ec.valueOf(jsonObject.name)

                } else {
                    result = [:]
                    for (def m : jsonObject.entrySet()) {
                        result.put(DynamicParametersUtils.convertJsonToNativeObject(m.getKey()), DynamicParametersUtils.convertJsonToNativeObject(m.getValue()))
                    }
                }

            } else if (jsonObject.equals(JSONObject.NULL)) {
                return null

            } else if (jsonObject.equals(JSONNull.getInstance())) {
                return null

            } else if (jsonObject instanceof JSONNull) {
                return null

            } else if (jsonObject instanceof JSONArray || jsonObject instanceof Collection) {
                result = []
                for (def o : jsonObject) {
                    result.add(DynamicParametersUtils.convertJsonToNativeObject(o))
                }

            } else {
                result = jsonObject
            }
        }

        return result
    }

    static def bind(Object obj, Map<String, Object> params, List<String> exceptions = null) {
        BindDynamicMethod bind = new BindDynamicMethod()

        def args = [obj, params]

        if(exceptions) args.add(['exclude': exceptions])

        bind.invoke(obj, 'bind', (Object[]) args)
    }

}