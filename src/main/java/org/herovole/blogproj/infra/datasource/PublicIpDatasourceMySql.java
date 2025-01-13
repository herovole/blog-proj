package org.herovole.blogproj.infra.datasource;

import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.time.Timestamp;
import org.herovole.blogproj.domain.publicuser.PublicIpDatasource;
import org.herovole.blogproj.infra.jpa.entity.EPublicIp;
import org.herovole.blogproj.infra.jpa.repository.EPublicIpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("publicIpDatasource")
public class PublicIpDatasourceMySql implements PublicIpDatasource {
    protected final EPublicIpRepository ePublicIpRepository;

    @Autowired
    public PublicIpDatasourceMySql(EPublicIpRepository ePublicIpRepository) {
        this.ePublicIpRepository = ePublicIpRepository;
    }

    @Override
    public boolean isRecorded(IPv4Address ip) {
        if (ip.isEmpty()) throw new IllegalArgumentException("empty IP");
        EPublicIp ePublicIp = ePublicIpRepository.findByAton(ip.aton());
        return ePublicIp != null;
    }

    @Override
    public Timestamp isBannedUntil(IPv4Address ip) {
        if (ip.isEmpty()) throw new IllegalArgumentException("empty IP");
        EPublicIp ePublicIp = ePublicIpRepository.findByAton(ip.aton());
        return ePublicIp == null ? Timestamp.empty() : ePublicIp.getBannedUntil();
    }
}
