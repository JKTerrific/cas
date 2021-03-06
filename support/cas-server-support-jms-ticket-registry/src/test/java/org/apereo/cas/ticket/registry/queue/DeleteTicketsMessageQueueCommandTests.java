package org.apereo.cas.ticket.registry.queue;

import org.apereo.cas.StringBean;
import org.apereo.cas.authentication.CoreAuthenticationTestUtils;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.ticket.TicketGrantingTicketImpl;
import org.apereo.cas.ticket.support.NeverExpiresExpirationPolicy;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This is {@link DeleteTicketsMessageQueueCommandTests}.
 *
 * @author Misagh Moayyed
 * @since 5.2.0
 */
@Slf4j
public class DeleteTicketsMessageQueueCommandTests extends AbstractTicketMessageQueueCommandTests {

    @Test
    public void verifyDeleteTickets() {
        final TicketGrantingTicket ticket = new TicketGrantingTicketImpl("TGT", CoreAuthenticationTestUtils.getAuthentication(),
            new NeverExpiresExpirationPolicy());
        ticketRegistry.addTicket(ticket);
        val cmd = new DeleteTicketsMessageQueueCommand(new StringBean());
        cmd.execute(ticketRegistry);
        assertTrue(ticketRegistry.getTickets().isEmpty());
    }
}
