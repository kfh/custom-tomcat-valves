Custom Tomcat valves
====================

## NotificationPollingValveBase
Base valve class that support adding notifications to a queue to be emailed to a specified recipient after a given polling delay.

Attributes:  
**emailRecipient** (String)  
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
    emailRecipient="example@email.com" 
    smtpHost="example.host" 
    />
```

### ErrorReportNotificationValve
Valve that sends notifications (emails) about responses with a status code of 500 (internal server error)

## Building and usage
Run the following command to build the jar with dependencies:  
`ant clean ivy jar`

Copy the resulting jar from `dist/` to `$TOMCAT_HOME/lib/`

Add the valves to your `server.xml` (see StuckThreadNotificationValve example above)