package com.synccafe.icafe.sales.domain.services;

import com.synccafe.icafe.sales.domain.model.aggregates.Sale;
import com.synccafe.icafe.sales.domain.model.commands.CreateSaleCommand;

import java.util.Optional;

public interface SaleCommandService {
    Optional<Sale> handle(CreateSaleCommand command);
    Optional<Sale> completeSale(Long saleId);
    Optional<Sale> cancelSale(Long saleId);
}