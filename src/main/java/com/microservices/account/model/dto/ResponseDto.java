package com.microservices.account.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data@AllArgsConstructor
@Schema(
        name = "Response",
        description = "Schema to hold a successfully response information"
)
public class ResponseDto {

    private String statusCode;

    private String statusMsg;
}
