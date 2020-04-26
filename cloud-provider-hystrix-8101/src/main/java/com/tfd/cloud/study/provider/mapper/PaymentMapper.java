package com.tfd.cloud.study.provider.mapper;


import com.tfd.cloud.study.common.api.Payment;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @since TangFD 2020-04-18
 **/
@RegisterMapper
public interface PaymentMapper extends Mapper<Payment> {
}
