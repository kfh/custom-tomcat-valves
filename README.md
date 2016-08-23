Custom Tomcat valves
====================

## NotificationPollingValveBase
Base valve class that support adding notifications to a queue to be emailed to a specified recipient after a given polling delay.

Attributes:  
**emailRecipient** (String)  
**emailSender** (String - defaults to emailRecipient)  
**emailSubject** (String - defaults to 'Notification from CLASSNAME')  
**smtpHost** (String)  
**pollingDelay** (Long - 10 seconds default)  

A JavaMail 1.5.5 implementation is repackaged with [Jar Jar Links](https://github.com/shevek/jarjar) within the valves jar for sending emails.


### StuckThreadNotificationValve
Modified version of `org.apache.catalina.valves.StuckThreadDetectionValve` - added support to send notifications (emails)
about stuck threads.

See [parent class specification for attributes and usage](https://tomcat.apache.org/tomcat-7.0-doc/config/valve.html#Stuck_Thread_Detection_Valve).

Example usage in hosts section in **`server.xml`**:  
```
<Valve className="com.nortal.healthcare.tomcat.valves.StuckThreadNotificationValve" 
    threshold="600"
    pollingDelay="300"
    emailRecipient="example1@email.com" 
    emailSender="example2@email.com"
    emailSubject="Stuck thread in ENVIRONMENT"
    smtpHost="example.host" 
    />
```

### ErrorReportNotificationValve
Modified version of `org.apache.catalina.valves.ErrorReportValve` that sends notifications (emails) about responses with a status code of 500 (internal server error)

Example usage in **`server.xml`**:  
(need to override errorReportValveClass in default host element and also add a valve element) 
```
<Host name="localhost"  appBase="webapps"
    errorReportValveClass="com.nortal.healthcare.tomcat.valves.ErrorReportNotificationValve"
    unpackWARs="true" autoDeploy="true">
    
    <Valve className="com.nortal.healthcare.tomcat.valves.ErrorReportNotificationValve" 
        pollingDelay="300"
        emailRecipient="example@email.com" 
        smtpHost="example.host" 
    />
```

## Building and usage
Run the following command to build the jar with dependencies 
(your JAVA_HOME environment variable should point to JDK8):  
* Ant: `ant clean ivy jar`
* Gradle: `./gradlew bundledJar`


Copy the resulting jar from `dist/` to `$TOMCAT_HOME/lib/`

Add the valves to your `server.xml` (see StuckThreadNotificationValve example above)