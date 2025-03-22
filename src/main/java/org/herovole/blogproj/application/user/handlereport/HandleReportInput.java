package org.herovole.blogproj.application.user.handlereport;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IntegerId;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HandleReportInput {

    public static HandleReportInput ofFormContent(long reportIdConfirmation, FormContent formContent) {
        IntegerId reportId = IntegerId.fromFormContentReportId(formContent);
        if (reportId.longMemorySignature() != reportIdConfirmation) {
            throw new IllegalArgumentException("Report ID does not match");
        }
        GenericSwitch hides = GenericSwitch.fromFormContentIsHandled(formContent);
        return new HandleReportInput(reportId, hides);
    }

    private final IntegerId reportId;
    private final GenericSwitch handles;

}

