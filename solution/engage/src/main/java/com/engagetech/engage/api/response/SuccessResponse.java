package com.engagetech.engage.api.response;

import javax.ws.rs.core.Response;

public class SuccessResponse {
    
    public static Response create(Object entity) {
        return Response.ok(entity).build();
    } 
   
    public static Response create(String message) {
        return Response.ok(new BaseResponse(0, message)).build();
    }
    
}
