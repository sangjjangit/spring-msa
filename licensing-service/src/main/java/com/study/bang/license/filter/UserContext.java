package com.study.bang.license.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class UserContext {
    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String AUTH_TOKEN     = "tmx-auth-token";
    public static final String USER_ID        = "tmx-user-id";
    public static final String ORGANIZATION_ID = "tmx-organization-id";

    private String correlationId;
    private String authToken;
    private String userId;
    private String organizationId;

//    private String correlationId= new String();
//    private String authToken= new String();
//    private String userId = new String();
//    private String organizationId = new String();
//
//    public String getCorrelationId() { return correlationId;}
//    public void setCorrelationId(String correlationId) {
//        this.correlationId = correlationId;
//    }
//
//    public String getAuthToken() {
//        return authToken;
//    }
//
//    public void setAuthToken(String authToken) {
//        this.authToken = authToken;
//    }
//
//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }
//    public String getOrganizationId() {
//        return organizationId;
//    }
//    public void setOrganizationId(String organizationId) {
//        this.organizationId = organizationId;
//    }
}
