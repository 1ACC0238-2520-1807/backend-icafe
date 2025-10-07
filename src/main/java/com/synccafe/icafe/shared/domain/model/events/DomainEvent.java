package com.synccafe.icafe.shared.domain.model.events;

import java.time.Instant;

/**
 * Base interface for all domain events in the system.
 * Domain events represent something important that happened in the domain.
 */
public interface DomainEvent {
    
    /**
     * Returns the timestamp when this domain event occurred.
     * 
     * @return the instant when the event occurred
     */
    Instant occurredOn();
}