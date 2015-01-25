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
  def version = "0.5" // added by set-version
  def grailsVersion = "2.0.0 > *"
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
  def organization = [name: "Sindicato Source", url: "http://sindica.to/"]
  def issueManagement = [system: "GITHUB", url: "https://github.com/domix/grails-activemq/issues"]
  def scm = [url: "https://github.com/domix/grails-activemq"]

  def doWithSpring = {

    def conf = application.config.grails.activemq ?: [:]
    if (!conf || !conf.active) {
      println '\n\nActiveMQ Embedded is disabled, not loading\n\n'
      return
    }

    xmlns context: 'http://www.springframework.org/schema/context'
    context.'component-scan'('base-package': 'com.domingosuarez.grails.plugin.activemq')

    
  }
}
