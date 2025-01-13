package org.herovole.blogproj.domain.publicuser;

import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.time.Date;

import java.security.NoSuchAlgorithmException;

public interface DailyUserIdFactory {
    DailyUserId generate(IPv4Address address, Date date) throws NoSuchAlgorithmException;
}
