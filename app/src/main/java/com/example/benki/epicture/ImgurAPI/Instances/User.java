package com.example.benki.epicture.ImgurAPI.Instances;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class User {

    private final Base base;

    private final Settings settings;

    private final GalleryInfo info;

}
