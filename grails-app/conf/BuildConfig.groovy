/*
 * Copyright 2010 Grails Plugin Collective
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

def activemqVersion = '5.6.0'

grails.project.dependency.resolution = {
  inherits("global")
  log "warn"
  repositories {
    grailsPlugins()
    grailsHome()
    mavenCentral()
    mavenRepo "http://download.java.net/maven/2/"
    mavenRepo "http://repository.jboss.org/nexus/content/groups/public-jboss/"
  }

  dependencies {
    compile("org.apache.activemq:activemq-core:${activemqVersion}") {
      transitive = false
    }
    compile("org.apache.activemq:kahadb:$activemqVersion") {
      transitive = false
    }
    compile("org.apache.activemq.protobuf:activemq-protobuf:1.1") {
      transitive = false
    }
    compile("org.apache.activemq:activeio-core:3.1.4") {
      transitive = false
    }
    compile "org.apache.geronimo.specs:geronimo-j2ee-management_1.1_spec:1.0.1"
    compile "org.apache.geronimo.specs:geronimo-jms_1.1_spec:1.1.1"
  }
}
