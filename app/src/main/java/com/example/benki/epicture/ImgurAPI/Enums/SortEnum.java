package com.example.benki.epicture.ImgurAPI.Enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SortEnum {

    VIRAL("viral"),
    TOP("top"),
    TIME("time"),
    RISING("rising"),
    RANDOM("random");

    private final String name;
}
