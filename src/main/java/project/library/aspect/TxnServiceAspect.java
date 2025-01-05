package project.library.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import project.library.dto.txn.TxnCreateRequest;

@Aspect
@Component
public class TxnServiceAspect
{
    private static final Logger log = LoggerFactory.getLogger(TxnServiceAspect.class);

    @Pointcut("execution(String project.library.service.impl.TxnService.create(project.library.dto.txn.TxnCreateRequest))")
    public void createMethod(){}

    @Before(value = "createMethod() && args(req)") // In @Before of ControllerAspect, we don't require args() coz there the target object is fetched from RequestContextHolder. But here to get the params of create method, we need it in the mentioned format.
    public void logRequest(TxnCreateRequest req) {
        log.info("Txn Request is: {}", req);
    }
}
