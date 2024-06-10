package dev.vulcanium.business.services.order.orderproduct;

import dev.vulcanium.business.model.order.orderproduct.OrderProductDownload;
import dev.vulcanium.business.repositories.order.orderproduct.OrderProductDownloadRepository;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityServiceImpl;
import java.util.List;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;




@Service("orderProductDownloadService")
public class OrderProductDownloadServiceImpl  extends SalesManagerEntityServiceImpl<Long, OrderProductDownload> implements OrderProductDownloadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProductDownloadServiceImpl.class);


    private final OrderProductDownloadRepository orderProductDownloadRepository;

    @Inject
    public OrderProductDownloadServiceImpl(OrderProductDownloadRepository orderProductDownloadRepository) {
        super(orderProductDownloadRepository);
        this.orderProductDownloadRepository = orderProductDownloadRepository;
    }
    
    @Override
    public List<OrderProductDownload> getByOrderId(Long orderId) {
    	return orderProductDownloadRepository.findByOrderId(orderId);
    }


}
