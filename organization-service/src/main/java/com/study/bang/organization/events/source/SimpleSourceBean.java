package com.study.bang.organization.events.source;

import com.study.bang.organization.events.model.OrganizationChangeModel;
//import com.study.bang.organization.filter.ActionEnum;
import com.study.bang.organization.filter.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

// 메시지 발행
@Component
@Slf4j
public class SimpleSourceBean {

//    private Source source;

//    private static final Logger logger = LoggerFactory.getLogger(SimpleSourceBean.class);

//    @Autowired
//    public SimpleSourceBean(Source source){
//        this.source = source;
//    }

//    public void publishOrganizationChange(ActionEnum action, String organizationId){
//        log.debug("Sending Kafka message {} for Organization Id: {}", action, organizationId);
//        OrganizationChangeModel change =  new OrganizationChangeModel(
//                OrganizationChangeModel.class.getTypeName(),
//                action.toString(),
//                organizationId,
//                UserContext.getCorrelationId());
//
//        source.output().send(MessageBuilder.withPayload(change).build());
//    }
}
