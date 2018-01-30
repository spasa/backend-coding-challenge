package com.engagetech.engage.api.response;

import javax.ws.rs.core.Response;

public class ErrorResponse {
    
    public static Response create(Object entity) {
        return Response.ok(entity).build();
    }
    
    public static Response create(String message) {
        return Response.ok(new BaseResponse(1, message)).build();
    }
    
}
