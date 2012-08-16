log4j = {
    error  'org.codehaus.groovy.grails.web.servlet',
           'org.codehaus.groovy.grails.web.pages',
           'org.codehaus.groovy.grails.web.sitemesh',
           'org.codehaus.groovy.grails.web.mapping.filter',
           'org.codehaus.groovy.grails.web.mapping',
           'org.codehaus.groovy.grails.commons',
           'org.codehaus.groovy.grails.plugins',
           'org.codehaus.groovy.grails.orm.hibernate',
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'

    error 'grails.app.service.grails.plugins.reloadconfig.ReloadConfigService'

    warn   'org.mortbay.log'
}

environments {

    development {
        grails.config.locations = ['file:./TestConfig.groovy']
        dynamic.parameters.base.url = 'http://##SERVER##:8080/dynamic-parameters/dynamicParameters/save'
    }

    test {
        grails.config.locations = ['file:./TestConfig.groovy']
        dynamic.parameters.base.url = 'http://##SERVER##:8080/dynamic-parameters/dynamicParameters/save'
    }

    production {
        dynamic.parameters.base.url = 'http://##SERVER##:8080/dynamicParameters/save'
    }

}

dynamic.parameters.security.on = false
dynamic.parameters.security.token = ''
dynamic.parameters.pools.on = true
dynamic.parameters.pools.configuration = [
    mla: ['127.0.0.1'],
    mlb: ['127.0.0.1'],
    mlm: ['127.0.0.1'],
    rest: ['127.0.0.1'],
    test: ['127.0.0.1']
]
dynamic.parameters.rest.default.connection.timeout = 60000
dynamic.parameters.rest.default.socket.timeout = 60000
dynamic.parameters.file.path = './TestConfig.groovy'