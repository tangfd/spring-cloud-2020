package com.tfd.cloud.study.common.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @since TangFD 2020-04-18
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_cloud_payment")
public class Payment {

    public static final String ID = "id";

    private String id;
    private String code;
    private BigDecimal amount;

}
