package com.example.benki.epicture.ImgurAPI.Enums;

import com.example.benki.epicture.ImgurAPI.ImgurAPI;
import com.example.benki.epicture.ImgurAPI.Instances.Picture;
import com.example.benki.epicture.ImgurAPI.RequestManager;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SectionEnum {

    HOT("hot", new EnumAdapter() {
        @Override
        public List<Picture> adapting(RequestManager manager, SortEnum e, boolean viral, int index) {
            return ImgurAPI.getViralPictures(manager, HOT.getName(), e.getName(), viral, index);
        }
    }),
    RANDOM("random", new EnumAdapter() {
        @Override
        public List<Picture> adapting(RequestManager manager, SortEnum e, boolean viral, int index) {
            return ImgurAPI.getViralPictures(manager, RANDOM.getName(), e.getName(), viral, index);
        }
    }),
    USER("user", new EnumAdapter() {
        @Override
        public List<Picture> adapting(RequestManager manager, SortEnum e, boolean viral, int index) {
            return ImgurAPI.getViralPictures(manager, RANDOM.getName(), e.getName(), viral, index);
        }
    });

    private final String name;
    private final EnumAdapter adapter;
}
