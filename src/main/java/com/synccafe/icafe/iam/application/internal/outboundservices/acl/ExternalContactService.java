package com.synccafe.icafe.iam.application.internal.outboundservices.acl;

import com.synccafe.icafe.contacs.interfaces.acl.ContactsContextFacade;
import org.springframework.stereotype.Service;

@Service
public class ExternalContactService {
    private final ContactsContextFacade contactsContextFacade;

    public ExternalContactService(ContactsContextFacade contactsContextFacade) {
        this.contactsContextFacade = contactsContextFacade;
    }

    public void CreateContactPortfolio(Long userId) {
        contactsContextFacade.createPortfolio(userId);
    }
}
