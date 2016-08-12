package com.nortal.healthcare.tomcat.valves;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorReportNotificationValve extends NotificationPollingValveBase {

    public ErrorReportNotificationValve() {
        super(true);
    }

    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {
        this.getNext().invoke(request, response);
        if (response.getStatus() == HttpServletResponse.SC_INTERNAL_SERVER_ERROR) {
            StringBuilder sb = new StringBuilder();
            sb.append("Internal server error (500) detected for request: ");
            sb.append(request.getRequestURL());
            sb.append("\n\nResponse message:\n");
            sb.append(response.getMessage());
            sb.append("\nRequest parameters:\n");
            sb.append(RequestInspectionUtil.formatParameters(request.getParameterMap()));
            Throwable throwable = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
            if (throwable != null) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                throwable.printStackTrace(pw);
                String stackTraceString = sw.toString();
                sb.append("\nStack trace:\n");
                sb.append(stackTraceString);
            }
            addNotification(sb.toString());
        }
    }
}
