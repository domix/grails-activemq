import org.apache.activemq.ActiveMQConnectionFactory
import org.apache.activemq.broker.TransportConnector
import org.apache.activemq.command.ActiveMQQueue
import org.apache.activemq.xbean.XBeanBrokerService

import org.springframework.jms.core.JmsTemplate
import org.springframework.jms.connection.SingleConnectionFactory
import org.springframework.jms.listener.DefaultMessageListenerContainer

class ActivemqGrailsPlugin {
    def version = 0.1
    def dependsOn = [:]

    // TODO Fill in these fields
    def author = "Domingo Suarez Torres"
    def authorEmail = "domingo.suarez@gmail.com"
    def title = "Grails Activemq Plugin"
    def description = '''\
Plugin to integrate ActiveMQ in a Grails application.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/Activemq+Plugin"

    def doWithSpring = {
		jmsBroker(XBeanBrokerService) {
			useJmx = 'false'
			persistent = 'false'
			transportConnectors = [new TransportConnector(uri: new URI('tcp://localhost:61616'))]
		}

		connectionFactory(ActiveMQConnectionFactory) {
			brokerURL = 'vm://localhost'
		}

		jmsTemplate(JmsTemplate) {
			connectionFactory =  { SingleConnectionFactory cf ->
				targetConnectionFactory = ref('connectionFactory')
			}
		}
		
    }
   
    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)		
    }

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional)
    }
	                                      
    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }
	
    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }
}
