package com.nortal.healthcare.tomcat.valves;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorReportNotificationValve extends NotificationPollingValveBase {

    public ErrorReportNotificationValve() {
        super(true);
    }

    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {
        this.getNext().invoke(request, response);
        if (response.getStatus() == HttpServletResponse.SC_INTERNAL_SERVER_ERROR) {
            addNotification(response.getMessage());
        }
    }
}
