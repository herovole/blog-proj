package org.herovole.blogproj.domain.publicuser;

import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.IntegerId;

@ToString
public class IntegerPublicUserId extends IntegerId implements PublicUserId {

    public static IntegerPublicUserId fromFormContentUserId(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_USER_ID);
        return valueOf(child.getValue());
    }

    public static IntegerPublicUserId valueOf(String id) {
        return new IntegerPublicUserId(Long.parseLong(id));
    }

    public static IntegerPublicUserId valueOf(Long id) {
        return new IntegerPublicUserId(id);
    }

    public static IntegerPublicUserId empty() {
        return valueOf((Long) null);
    }

    private static final String API_KEY_USER_ID = "userId";

    private IntegerPublicUserId(Long id) {
        super(id);
    }

    @Override
    public Long longMemorySignature() {
        return this.id;
    }

}
