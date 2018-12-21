package com.example.benki.epicture.ImgurAPI.Enums;

import com.example.benki.epicture.ImgurAPI.Instances.Picture;
import com.example.benki.epicture.ImgurAPI.RequestManager;

import java.util.List;

public interface EnumAdapter {

    List<Picture> adapting(RequestManager manager, SortEnum e, boolean viral, int index);

}
