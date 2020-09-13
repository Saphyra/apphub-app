package com.github.saphyra.apphub.app.web.model.response;

import java.util.Map;

public class ErrorResponse {
    private String errorCode;
    private String localizedMessage;
    private Map<String, String> params;

    public String getErrorCode() {
        return errorCode;
    }

    public String getLocalizedMessage() {
        return localizedMessage;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
