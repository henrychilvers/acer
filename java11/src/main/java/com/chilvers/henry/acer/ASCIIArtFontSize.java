package com.chilvers.henry.acer;

public enum ASCIIArtFontSize {
    ART_SIZE_SMALL(12),
    ART_SIZE_MEDIUM(18),
    ART_SIZE_LARGE(24),
    ART_SIZE_HUGE(32);

    private final Integer value;

    public Integer getValue() {
        return value;
    }

    ASCIIArtFontSize(Integer value) {
        this.value = value;
    }
}
