package uk.gov.hmcts.reform.dev.utils;

import org.springframework.stereotype.Component;

@Component
public class Utils {
    public boolean validateTaskId(int id) {
        return id >= 0;
    }
}
