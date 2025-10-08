package com.synccafe.icafe.product.domain.services;

import com.synccafe.icafe.product.domain.model.commands.*;

public interface ProductCommandService {
    Long handle(CreateProductCommand command);
    void handle(UpdateProductCommand command);
    void handle(ArchiveProductCommand command);
    void handle(ActivateProductCommand command);
    void handle(DeleteProductCommand command);
}