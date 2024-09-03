package mykytka235.ms.report.constants;

public enum ServiceName {
    MY_ACCOUNT("be-myaccount"),
    NOTIFICATION("be-notification");

    private String providerName;

    ServiceName(String providerName) {
        this.providerName = providerName;
    }

    public String getName() {
        return providerName;
    }
}
