package com.synccafe.icafe.sales.domain.model.events;

import com.synccafe.icafe.sales.domain.model.aggregates.Sale;
import com.synccafe.icafe.shared.domain.model.events.DomainEvent;
import lombok.Getter;

import java.time.Instant;

@Getter
public final class SaleCreatedEvent implements DomainEvent {
    private final Sale sale;
    private final Instant occurredOn;

    public SaleCreatedEvent(Sale sale) {
        this.sale = sale;
        this.occurredOn = Instant.now();
    }

    @Override
    public Instant occurredOn() {
        return occurredOn;
    }
}