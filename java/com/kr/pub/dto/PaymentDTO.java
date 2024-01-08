package com.kr.pub.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
	private String paymentId;
	private String paymentTypeCode;
	private String paymentMethodCode;
	private Date paymentDate;
	private String orderId;
	
	@JsonValue
    public String getPaymentMethodCode() {
        return paymentMethodCode;
    }

    @JsonCreator
    public static PaymentDTO fromCode(String code) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPaymentMethodCode(code);
        return paymentDTO;
    }
}
