package com.example.benki.epicture.ImgurAPI;

import com.example.benki.epicture.ImgurAPI.Instances.Album;
import com.example.benki.epicture.ImgurAPI.Instances.Base;
import com.example.benki.epicture.ImgurAPI.Instances.GalleryInfo;
import com.example.benki.epicture.ImgurAPI.Instances.Managers.AccountBaseManager;
import com.example.benki.epicture.ImgurAPI.Instances.Managers.AccountSettingsManager;
import com.example.benki.epicture.ImgurAPI.Instances.Managers.AlbumManager;
import com.example.benki.epicture.ImgurAPI.Instances.Managers.GalleryInfoManager;
import com.example.benki.epicture.ImgurAPI.Instances.Managers.StatusManager;
import com.example.benki.epicture.ImgurAPI.Instances.Managers.VotesManager;
import com.example.benki.epicture.ImgurAPI.Instances.Picture;
import com.example.benki.epicture.ImgurAPI.Instances.Managers.PictureManager;
import com.example.benki.epicture.ImgurAPI.Instances.Settings;
import com.example.benki.epicture.ImgurAPI.Instances.Tag;
import com.example.benki.epicture.ImgurAPI.Instances.Managers.TagsManager;
import com.example.benki.epicture.ImgurAPI.Instances.User;
import com.example.benki.epicture.ImgurAPI.Instances.Votes;
import com.example.benki.epicture.Utils.UrlRequester;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * DEV_epicture_2018
 * Created by Lucas Benkemoun on 31/10/18.
 * Copyright © 2018 Lucas Benkemoun. All rights reserved.
 */
public class ImgurAPI {

    public static List<Picture> getUserPictures(RequestManager manager) {
        PictureManager pm = (PictureManager) manager.newRequest("https://api.imgur.com/3/account/me/images", new RequestManager.RequestProcessing() {
            @Override
            public String setAuthorisation(RequestManager.UserSettings settings) {
                return "Bearer " + settings.getAccess();
            }
        }, PictureManager.class);

        return pm.getPictures();
    }

    public static List<Picture> getViralPictures(RequestManager manager, String type, String date, boolean viral, int index) {
        PictureManager pm = (PictureManager) manager.newRequest("https://api.imgur.com/3/gallery/@1/@2/@3?realtime_results=@4&showViral=@5", new RequestManager.RequestProcessing() {
            @Override
            public String setAuthorisation(RequestManager.UserSettings settings) {
                return "Client-ID " + settings.getId();
            }
        }, PictureManager.class, type, date, String.valueOf(index), "false", String.valueOf(viral));

        return pm.getPictures();
    }

    public static List<Picture> getSearchPictures(RequestManager manager, String type, String search, int index) {
        PictureManager pm = (PictureManager) manager.newRequest("https://api.imgur.com/3/gallery/search/@1/@2?q=@3", new RequestManager.RequestProcessing() {
            @Override
            public String setAuthorisation(RequestManager.UserSettings settings) {
                return "Client-ID " + settings.getId();
            }
        }, PictureManager.class, type, String.valueOf(index), search);

        return pm.getPictures();
    }

    public static TagsManager getSearchTagPictures(RequestManager manager, String type, String tag, int index) {
        return (TagsManager) manager.newRequest("https://api.imgur.com/3/gallery/t/@1/@2/@3", new RequestManager.RequestProcessing() {
            @Override
            public String setAuthorisation(RequestManager.UserSettings settings) {
                return "Client-ID " + settings.getId();
            }
        }, TagsManager.class, tag, type, String.valueOf(index));
    }

    public static List<Tag> getAllTags(RequestManager manager) {
        TagsManager pm = (TagsManager) manager.newRequest("https://api.imgur.com/3/tags", new RequestManager.RequestProcessing() {
            @Override
            public String setAuthorisation(RequestManager.UserSettings settings) {
                return "Client-ID " + settings.getId();
            }
        }, TagsManager.class);

        return pm.getTags();
    }
    public static List<Picture> sendPicture(RequestManager manager, final PictureManager.Uploader uploader) {
        final HashMap<String, String> post = uploader.getHashMap();

        if (uploader.getAlbum() != null) {
            Album album = getAlbumByString(manager, uploader.getAlbum());

            String id = album != null ? String.valueOf(album.getId()) : null;

            if (album == null)
                id = createAlbum(manager, new AlbumManager.Uploader(uploader.getAlbum(), ""));

            if (id != null)
                post.put("album", id);
        }


        PictureManager pm = (PictureManager) manager.newPostRequest("https://api.imgur.com/3/image", new RequestManager.RequestProcessing() {
            @Override
            public String setAuthorisation(RequestManager.UserSettings settings) {
                return "Bearer " + settings.getAccess();
            }
        }, new UrlRequester.actionOnPost() {
            @Override
            public void action(Request.Builder build, RequestBody body) {
                build.post(body);
            }

            @Override
            public RequestBody getRequestBody() {
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

                for (String key : post.keySet())
                    builder.addFormDataPart(key, post.get(key));
                 return builder.build();
            }
        }, PictureManager.class);

        return pm.getPictures();
    }

    public static boolean removePicture(RequestManager manager, final String delete) {
        StatusManager pm = (StatusManager) manager.newPostRequest("https://api.imgur.com/3/image/@1", new RequestManager.RequestProcessing() {
            @Override
            public String setAuthorisation(RequestManager.UserSettings settings) {
                return "Bearer " + settings.getAccess();
            }
        }, new UrlRequester.actionOnPost() {
            @Override
            public void action(Request.Builder build, RequestBody body) {
                build.delete();
            }

            @Override
            public RequestBody getRequestBody() {
                return null;
            }
        }, StatusManager.class, delete);

        return pm.isSuccess();
    }

    public static Base getUserBase(RequestManager manager, String pseudo) {
        AccountBaseManager pm = (AccountBaseManager) manager.newRequest("https://api.imgur.com/3/account/@1", new RequestManager.RequestProcessing() {
            @Override
            public String setAuthorisation(RequestManager.UserSettings settings) {
                return "Client-ID " + settings.getId();
            }
        }, AccountBaseManager.class, pseudo);

        return pm.getBase();
    }

    public static Settings getUserSettings(RequestManager manager) {
        AccountSettingsManager pm = (AccountSettingsManager) manager.newRequest("https://api.imgur.com/3/account/me/settings", new RequestManager.RequestProcessing() {
            @Override
            public String setAuthorisation(RequestManager.UserSettings settings) {
                return "Bearer " + settings.getAccess();
            }
        }, AccountSettingsManager.class);

        return pm.getSettings();
    }

    public static boolean followTagByTag(RequestManager manager, Tag tag) {
        return followTagByString(manager, tag.getFollowName());
    }

    public static boolean followTagByString(RequestManager manager, String tag) {
        StatusManager pm = (StatusManager) manager.newPostRequest("https://api.imgur.com/3/account/me/follow/tag/@1", new RequestManager.RequestProcessing() {
            @Override
            public String setAuthorisation(RequestManager.UserSettings settings) {
                return "Bearer " + settings.getAccess();
            }
        }, new UrlRequester.actionOnPost() {
            @Override
            public void action(Request.Builder build, RequestBody body) {
                build.post(body);
            }

            @Override
            public RequestBody getRequestBody() {
                return RequestBody.create(null, new byte[]{});
            }
        }, StatusManager.class, tag);

        return pm.isSuccess();
    }



    public static boolean unFollowTagByString(RequestManager manager, String tag) {
        StatusManager pm = (StatusManager) manager.newPostRequest("https://api.imgur.com/3/account/me/follow/tag/@1", new RequestManager.RequestProcessing() {
            @Override
            public String setAuthorisation(RequestManager.UserSettings settings) {
                return "Bearer " + settings.getAccess();
            }
        }, new UrlRequester.actionOnPost() {
            @Override
            public void action(Request.Builder build, RequestBody body) {
                build.delete();
            }

            @Override
            public RequestBody getRequestBody() {
                return null;
            }
        }, StatusManager.class, tag);

        return pm.isSuccess();
    }

    public static boolean unFollowTagByTag(RequestManager manager, Tag tag) {
        return unFollowTagByString(manager, tag.getFollowName());
    }

    public static boolean changeSettings(RequestManager manager, Base base) {
        final HashMap<String, String> hash = base.getHash();

        StatusManager pm = (StatusManager) manager.newPostRequest("https://api.imgur.com/3/account/@1/settings", new RequestManager.RequestProcessing() {
            @Override
            public String setAuthorisation(RequestManager.UserSettings settings) {
                return "Bearer " + settings.getAccess();
            }
        }, new UrlRequester.actionOnPost() {
            @Override
            public void action(Request.Builder build, RequestBody body) {
                build.put(body);
            }

            @Override
            public RequestBody getRequestBody() {
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

                for (String key : hash.keySet())
                    builder.addFormDataPart(key, hash.get(key));
                return builder.build();
            }
        }, StatusManager.class, manager.getUsername());

        getUserBase(manager, base.getUrl());

        return pm.isSuccess();
    }

    public static GalleryInfo getGalleryInfo(RequestManager manager) {
        GalleryInfoManager pm = (GalleryInfoManager) manager.newRequest("https://api.imgur.com/3/account/me/gallery_profile", new RequestManager.RequestProcessing() {
            @Override
            public String setAuthorisation(RequestManager.UserSettings settings) {
                return "Bearer " + settings.getAccess();
            }
        }, GalleryInfoManager.class);

        return pm.getGallery();
    }

    public static User getAllUserInfo(RequestManager manager) {
        Base base = getUserBase(manager, manager.getUsername());
        Settings settings = getUserSettings(manager);
        GalleryInfo info = getGalleryInfo(manager);

        return new User(base, settings, info);
    }

    public static Votes getVotesForImage(RequestManager manager, String imageid) {
        VotesManager pm = (VotesManager) manager.newRequest("https://api.imgur.com/3/gallery/@1/votes", new RequestManager.RequestProcessing() {
            @Override
            public String setAuthorisation(RequestManager.UserSettings settings) {
                return "Client-ID " + settings.getId();
            }
        }, VotesManager.class, imageid);

        return pm.getVotes();
    }

    public static boolean voteForImage(RequestManager manager, String imageid, Votes.VoteType type) {
        StatusManager pm = (StatusManager) manager.newPostRequest("https://api.imgur.com/3/gallery/image/@1/vote/@2", new RequestManager.RequestProcessing() {
            @Override
            public String setAuthorisation(RequestManager.UserSettings settings) {
                return "Bearer " + settings.getAccess();
            }
        }, new UrlRequester.actionOnPost() {
            @Override
            public void action(Request.Builder build, RequestBody body) {
                build.post(body);
            }

            @Override
            public RequestBody getRequestBody() {
                return RequestBody.create(null, new byte[]{});
            }
        }, StatusManager.class, imageid, type.getType());

        return pm.isSuccess();
    }

    public static boolean favoriteAnAlbum(RequestManager manager, String albumid) {
        StatusManager pm = (StatusManager) manager.newPostRequest("https://api.imgur.com/3/album/@1/favorite", new RequestManager.RequestProcessing() {
            @Override
            public String setAuthorisation(RequestManager.UserSettings settings) {
                return "Bearer " + settings.getAccess();
            }
        }, new UrlRequester.actionOnPost() {
            @Override
            public void action(Request.Builder build, RequestBody body) {
                build.post(body);
            }

            @Override
            public RequestBody getRequestBody() {
                return RequestBody.create(null, new byte[]{});
            }
        }, StatusManager.class, albumid);

        getAlbum(manager, albumid);

        return pm.isSuccess();
    }

    public static boolean favoriteAnImage(RequestManager manager, String imageid) {

        if (albumExists(manager, imageid))
            return favoriteAnAlbum(manager, imageid);


        StatusManager pm = (StatusManager) manager.newPostRequest("https://api.imgur.com/3/image/@1/favorite", new RequestManager.RequestProcessing() {
            @Override
            public String setAuthorisation(RequestManager.UserSettings settings) {
                return "Bearer " + settings.getAccess();
            }
        }, new UrlRequester.actionOnPost() {
            @Override
            public void action(Request.Builder build, RequestBody body) {
                build.post(body);
            }

            @Override
            public RequestBody getRequestBody() {
                return RequestBody.create(null, new byte[]{});
            }
        }, StatusManager.class, imageid);

        getPicture(manager, imageid);

        return pm.isSuccess();
    }

    public static List<Picture> getFavorites(RequestManager manager, int index) {
        PictureManager pm = (PictureManager) manager.newRequest("https://api.imgur.com/3/account/me/favorites/@1", new RequestManager.RequestProcessing() {
            @Override
            public String setAuthorisation(RequestManager.UserSettings settings) {
                return "Bearer " + settings.getAccess();
            }
        }, PictureManager.class, String.valueOf(index));

        return pm.getPictures();
    }

    public static Picture getPicture(RequestManager manager, String hash) {
        PictureManager pm = (PictureManager) manager.newRequest("https://api.imgur.com/3/image/@1", new RequestManager.RequestProcessing() {
            @Override
            public String setAuthorisation(RequestManager.UserSettings settings) {
                return "Client-ID " + settings.getId();
            }
        }, PictureManager.class, hash);

        return pm.getPictures().get(0);
    }

    public static String createAlbum(RequestManager manager, AlbumManager.Uploader uploader) {
        final HashMap<String, String> post = uploader.getHashMap();
        StatusManager pm = (StatusManager) manager.newPostRequest("https://api.imgur.com/3/album", new RequestManager.RequestProcessing() {
            @Override
            public String setAuthorisation(RequestManager.UserSettings settings) {
                return "Bearer " + settings.getAccess();
            }
        }, new UrlRequester.actionOnPost() {
            @Override
            public void action(Request.Builder build, RequestBody body) {
                build.post(body);
            }

            @Override
            public RequestBody getRequestBody() {
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

                for (String key : post.keySet())
                    builder.addFormDataPart(key, post.get(key));
                return builder.build();
            }
        }, StatusManager.class);

        System.out.println(pm.getData().getString("id"));

        try {
            return pm.getData().getJSONObject("data").getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean albumExists(RequestManager manager, String album) {
        StatusManager pm = (StatusManager) manager.newRequest("https://api.imgur.com/3/album/@1", new RequestManager.RequestProcessing() {
            @Override
            public String setAuthorisation(RequestManager.UserSettings settings) {
                return "Client-ID " + settings.getId();
            }
        }, StatusManager.class, album);

        return pm.isSuccess();
    }

    public static Album getAlbum(RequestManager manager, String album) {
        if (!albumExists(manager, album))
            return null;

        AlbumManager pm = (AlbumManager) manager.newRequest("https://api.imgur.com/3/album/@1", new RequestManager.RequestProcessing() {
            @Override
            public String setAuthorisation(RequestManager.UserSettings settings) {
                return "Client-ID " + settings.getId();
            }
        }, AlbumManager.class, album);

        return pm.getAlbum();
    }

    public static Album getAlbumByString(RequestManager manager, String title) {
        List<Album> albums = getAllAlbums(manager);

        for (Album album : albums)
            if (album.getTitle().equalsIgnoreCase(title))
                return album;

        return null;
    }

    public static List<Album> getAllAlbums(RequestManager manager) {

        AlbumManager pm = (AlbumManager) manager.newRequest("https://api.imgur.com/3/account/me/albums/", new RequestManager.RequestProcessing() {
            @Override
            public String setAuthorisation(RequestManager.UserSettings settings) {
                return "Bearer " + settings.getAccess();
            }
        }, AlbumManager.class);

        return pm.getAlbums();
    }
}
