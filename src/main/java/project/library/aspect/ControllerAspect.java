package project.library.aspect;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;

@Aspect
@Component
public class ControllerAspect
{
    public static final Log log = LogFactory.getLog(ControllerAspect.class);

    @Pointcut("execution(* project.library.controller..*(..))")
    /*
     "* project.library.controller" --> execute for all return types like object, string, etc. in the package (project.library.controller)
     ".." --> includes everything like all classes, sub-packages, etc. in the above package
     "*" --> includes all the functions
     "(..)" --> includes any parameter inside those functions
    */
    public void controllerMethods(){} // here we have defined a common name for point cut in case pointcut is to be used several times or there are multiple advices of the same point cut.

    @Before(value = "controllerMethods()") // If a separate point cut is not defined as above, then we can write like this --> @Before("execution(* project.library.controller..*(..))")
    public void logRequest()
    {
        HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(); // req means when the client (postman) sending data to our server
        log.info("Request: " + req.getMethod() + " " + req.getRequestURI());

        Enumeration<String> headers = req.getHeaderNames();
        while(headers.hasMoreElements()){
            String header = headers.nextElement();
            log.info("Request Headers: " + header + ":" + req.getHeader(header));
        }
    }

    @AfterReturning(value = "controllerMethods()", returning = "res") // Returning is name of the variable to be returned.
    public void logResponse(Object res)
    {
        if(res instanceof HttpServletResponse httpRes) {
            log.info("Response Status: " + httpRes.getStatus());
        } else {
            log.info("Response: " + res.toString());
        }
    }
}
