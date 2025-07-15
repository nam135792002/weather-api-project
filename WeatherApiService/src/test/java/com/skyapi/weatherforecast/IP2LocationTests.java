package com.skyapi.weatherforecast;

import com.ip2location.IP2Location;
import com.ip2location.IPResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class IP2LocationTests {
    private String DBPath = "ip2locdb/IP2LOCATION-LITE-DB3.BIN";

    @Test
    public void testInvalidIP() throws IOException {
        IP2Location ip2Location = new IP2Location();
        ip2Location.Open(DBPath);

        String ipAddress = "abc";
        IPResult ipResult = ip2Location.IPQuery(ipAddress);
        Assertions.assertThat(ipResult.getStatus()).isEqualTo("INVALID_IP_ADDRESS");
        System.out.println(ipResult);
    }

    @Test
    public void testValidIP1() throws IOException {
        IP2Location ip2Location = new IP2Location();
        ip2Location.Open(DBPath);

        String ipAddress = "103.48.198.141";
        IPResult ipResult = ip2Location.IPQuery(ipAddress);
        Assertions.assertThat(ipResult.getStatus()).isEqualTo("OK");
        Assertions.assertThat(ipResult.getCity()).isEqualTo("Delhi");
        System.out.println(ipResult);
    }
}
