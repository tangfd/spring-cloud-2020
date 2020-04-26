package com.tfd.cloud.study.provider.service;

import com.tfd.cloud.study.common.api.Payment;
import com.tfd.cloud.study.common.utils.IDUtils;
import com.tfd.cloud.study.provider.mapper.PaymentMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * @since TangFD 2020-04-18
 **/
@Service
public class PaymentService {

    @Resource
    private PaymentMapper paymentMapper;

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public String save(Payment payment) {
        if (StringUtils.isBlank(payment.getId())) {
            payment.setId(IDUtils.getUUId32());
        }

        paymentMapper.insertSelective(payment);
        return payment.getId();
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public Payment get(String id) {
        Example example = new Example(Payment.class);
        example.createCriteria().andEqualTo(Payment.ID, id);
        return paymentMapper.selectOneByExample(example);
    }

}
