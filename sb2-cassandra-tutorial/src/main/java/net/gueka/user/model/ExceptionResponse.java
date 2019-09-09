package net.gueka.user.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionResponse {
    private Date timestamp;
    private String mensaje;
    private String code;
}