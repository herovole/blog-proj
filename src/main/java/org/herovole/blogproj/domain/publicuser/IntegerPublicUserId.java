package org.herovole.blogproj.domain.publicuser;

import org.herovole.blogproj.domain.IntegerId;

public class IntegerPublicUserId extends IntegerId implements PublicUserId {

    public static IntegerPublicUserId valueOf(Long id) {
        return new IntegerPublicUserId(id);
    }

    public static IntegerPublicUserId empty() {
        return valueOf((Long) null);
    }

    private IntegerPublicUserId(Long id) {
        super(id);
    }

    @Override
    public Long longMemorySignature() {
        return this.id;
    }
}
