package org.herovole.blogproj.domain;

import com.google.common.net.InetAddresses;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.math.BigInteger;
import java.net.InetAddress;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class IPv4Address {

    private static final long MAX_ADDRESS = 256L * 256 * 256 * 256 - 1;

    public static IPv4Address valueOf(Long aton) {
        if (aton == null || aton == 0) return empty();
        if (0L < aton && aton <= MAX_ADDRESS) {
            return new IPv4Address(aton);
        }
        throw new IllegalArgumentException("Invalid IPv4 address: " + aton);
    }

    public static IPv4Address valueOf(String ip) {
        if (ip == null || ip.isEmpty()) return empty();
        InetAddress inetAddress = InetAddresses.forString(ip);
        BigInteger aton = new BigInteger(1, inetAddress.getAddress());
        return valueOf(aton.longValue());
    }

    public static IPv4Address empty() {
        return new IPv4Address(null);
    }

    private final Long aton;

    public boolean isEmpty() {
        return aton == null || aton == 0;
    }

    public String toRegularFormat() {
        return this.isEmpty() ? null : InetAddresses.fromIPv4BigInteger(BigInteger.valueOf(aton)).getHostAddress();
    }

    public long aton() {
        return this.isEmpty() ? 0 : aton;
    }

    public Long memorySignature() {
        return this.isEmpty() ? null : aton;
    }

}
