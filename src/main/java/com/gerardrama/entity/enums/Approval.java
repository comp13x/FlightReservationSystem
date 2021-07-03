package com.gerardrama.entity.enums;

public enum Approval {
    CREATED ("CREATED"),
    WAITING ("WAITING FOR APPROVAL"),
    APPROVED ("APPROVED"),
    DENIED ("DENIED");

    private final String approvalName;

    private Approval(String approvalName) {
        this.approvalName = approvalName;
    }

    @Override
    public String toString() {
        return this.approvalName;
    }
}
