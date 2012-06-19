/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

class ActivemqGrailsPlugin {
  def version = "0.4.1" // added by set-version
  def grailsVersion = "1.2 > *"
  def pluginExcludes = [
    "grails-app/views/error.gsp",
    "grails-app/i18n/**",
    "web-app/**"
  ]

  def author = "Domingo Suarez Torres"
  def authorEmail = "domingo.suarez@gmail.com"
  def title = "Grails ActiveMQ Plugin"
  def description = 'Plugin to integrate ActiveMQ in a Grails application.'

  // URL to the plugin's documentation
  def documentation = "http://grails.org/plugin/activemq"

  def license = "APACHE"
  def organization = [name: "Sindicato AHPM", url: "http://sindica.to/"]
  def issueManagement = [system: "GITHUB", url: "https://github.com/domix/grails-activemq/issues"]
  def scm = [url: "https://github.com/domix/grails-activemq"]

  def doWithSpring = {
    def conf = org.codehaus.groovy.grails.plugins.activemq.ActiveMQUtils.config
    if (!conf || !conf.active) {
      println '\n\nActiveMQ Embedded is disabled, not loading\n\n'
      return
    }

    println '\nActiveMQ Embedded...'

    jmsBroker(org.apache.activemq.xbean.XBeanBrokerService) {
      useJmx = conf.useJmx
      persistent = conf.persistent
      transportConnectors = [new org.apache.activemq.broker.TransportConnector(uri: new URI("tcp://localhost:${conf.port}"))]
    }

    jmsConnectionFactory(org.springframework.jms.connection.SingleConnectionFactory) {
      targetConnectionFactory = { org.apache.activemq.ActiveMQConnectionFactory cf ->
        brokerURL = 'vm://localhost'
      }
    }

    defaultJmsTemplate(org.springframework.jms.core.JmsTemplate) {
      connectionFactory = ref("jmsConnectionFactory")
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
