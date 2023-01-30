package com.uqai.demo.app.uqaidemo.util;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomResponse {
    private String type;
    private String message;
    private String cause;
    private String fieldUpdate;
}
