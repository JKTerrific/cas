package org.apereo.cas.ticket.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @author Misagh Moayyed
 * @since 4.1
 */
@Slf4j
public class HardTimeoutExpirationPolicyTests {

    private static final File JSON_FILE = new File(FileUtils.getTempDirectoryPath(), "hardTimeoutExpirationPolicy.json");
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void verifySerializeANeverExpiresExpirationPolicyToJson() throws IOException {
        val policyWritten = new HardTimeoutExpirationPolicy();
        MAPPER.writeValue(JSON_FILE, policyWritten);
        val policyRead = MAPPER.readValue(JSON_FILE, HardTimeoutExpirationPolicy.class);
        assertEquals(policyWritten, policyRead);
    }
}
