package com.example.benki.epicture;

import com.example.benki.epicture.ImgurAPI.Enums.SectionEnum;
import com.example.benki.epicture.ImgurAPI.Enums.SortEnum;
import com.example.benki.epicture.ImgurAPI.ImgurAPI;
import com.example.benki.epicture.ImgurAPI.Instances.*;
import com.example.benki.epicture.ImgurAPI.Instances.Managers.PictureManager;
import com.example.benki.epicture.ImgurAPI.RequestManager;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RequestUrlTests {

    private final static String img = "iVBORw0KGgoAAAANSUhEUgAAAQAAAAEACAIAAADTED8xAAADMElEQVR4nOzVwQnAIBQFQYXff81RUkQCOyDj1YOPnbXWPmeTRef+/3O/OyBjzh3CD95BfqICMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMO0TAAD//2Anhf4QtqobAAAAAElFTkSuQmCC\n";

    private final String token = "ec3f76e3cffb6afa7963432578b647da057debbd";
    private final String cliendid = "f98676cf23e9ab9";

    private final RequestManager manager = new RequestManager("lucas99063000", token, cliendid);

    @Test
    public void gettingUserImage() {
        assertNotNull(ImgurAPI.getUserPictures(manager));
    }

    @Test
    public void gettingViralImagesByImgurAPI_HOT_TIME() {
        assertNotNull(
                ImgurAPI.getViralPictures(
                        manager,
                        SectionEnum.HOT.getName(),
                        SortEnum.TIME.getName(),
                        true,
                        0
                )
        );
    }

    @Test
    public void gettingViralImagesByImgurAPI_TOP_TIME() {
        assertNotNull(
                ImgurAPI.getViralPictures(
                        manager,
                        SectionEnum.USER.getName(),
                        SortEnum.TIME.getName(),
                        true,
                        0
                )
        );
    }

    @Test
    public void gettingViralImagesByImgurAPI_TOP_RISING() {
        assertNotNull(
                ImgurAPI.getViralPictures(
                        manager,
                        SectionEnum.USER.getName(),
                        SortEnum.RISING.getName(),
                        true,
                        0
                )
        );
    }

    @Test
    public void gettingViralImagesByImgurAPI_HOT_VIRAL() {
        assertNotNull(
                ImgurAPI.getViralPictures(
                        manager,
                        SectionEnum.HOT.getName(),
                        SortEnum.VIRAL.getName(),
                        true,
                        0
                )
        );
    }

    @Test
    public void gettingViralImagesByImgurAPI_HOT_TOP() {
        assertNotNull(
                ImgurAPI.getViralPictures(
                        manager,
                        SectionEnum.HOT.getName(),
                        SortEnum.TOP.getName(),
                        true,
                        0
                )
        );
    }

    @Test
    public void gettingViralImagesByEnumAdapter_HOT() {
        assertNotNull(
                SectionEnum.HOT.getAdapter().adapting(manager, SortEnum.RISING, true, 0)
        );

        assertNotNull(
                SectionEnum.HOT.getAdapter().adapting(manager, SortEnum.TIME, true, 0)
        );

        assertNotNull(
                SectionEnum.HOT.getAdapter().adapting(manager, SortEnum.TOP, true, 0)
        );

        assertNotNull(
                SectionEnum.HOT.getAdapter().adapting(manager, SortEnum.VIRAL, true, 0)
        );
    }

    @Test
    public void gettingViralImagesByEnumAdapter_TOP() {
        assertNotNull(
                SectionEnum.USER.getAdapter().adapting(manager, SortEnum.RISING, true, 0)
        );

        assertNotNull(
                SectionEnum.USER.getAdapter().adapting(manager, SortEnum.TIME, true, 0)
        );

        assertNotNull(
                SectionEnum.USER.getAdapter().adapting(manager, SortEnum.TOP, true, 0)
        );

        assertNotNull(
                SectionEnum.USER.getAdapter().adapting(manager, SortEnum.VIRAL, true, 0)
        );
    }

    @Test
    public void gettingViralImagesByEnumAdapter_RANDOM() {
        assertNotNull(
                SectionEnum.USER.getAdapter().adapting(manager, SortEnum.RISING, true, 0)
        );

        assertNotNull(
                SectionEnum.HOT.getAdapter().adapting(manager, SortEnum.TIME, false, 1)
        );

        assertNotNull(
                SectionEnum.RANDOM.getAdapter().adapting(manager, SortEnum.RISING, false, 3)
        );

        assertNotNull(
                SectionEnum.USER.getAdapter().adapting(manager, SortEnum.VIRAL, false, 9)
        );
    }

    @Test
    public void getting_tags() {
        assertNotNull(ImgurAPI.getSearchTagPictures(manager, SectionEnum.HOT.getName(), "cat", 0).getPictures());
    }

    @Test
    public void uploadImage() {
        assertNotNull(ImgurAPI.sendPicture(manager, new PictureManager.Uploader(img, "test upload 1", "base64", null)));
    }

    @Test
    public void deleteImage() {
        assertTrue(ImgurAPI.removePicture(manager, "ZYZKALs8J5H8fk2"));
    }

    @Test
    public void searchImage() {
        assertNotNull(ImgurAPI.getSearchPictures(manager, SectionEnum.HOT.getName(), "cat", 0));
        assertNotNull(ImgurAPI.getSearchPictures(manager, SectionEnum.HOT.getName(), "dog", 0));
    }

    @Test
    public void getAllTags() {
        assertNotNull(ImgurAPI.getAllTags(manager));
    }

    @Test
    public void getFirstsTags() {
        List<Tag> tags = ImgurAPI.getAllTags(manager);
        Tag tag1 = tags.get(0);
        Tag tag2 = tags.get(1);

        System.out.println(tag1.getName());
        System.out.println(tag2.getName());

        Picture p1 = ImgurAPI.getSearchTagPictures(manager, SectionEnum.HOT.getName(), tag1.getName(), 0).getPictures().get(0);
        Picture p2 = ImgurAPI.getSearchTagPictures(manager, SectionEnum.HOT.getName(), tag2.getName(), 0).getPictures().get(0);
        assertNotEquals(p1, p2);
    }

    @Test
    public void getBaseUser() {
        Base base = ImgurAPI.getUserBase(manager, "lucas99063000");
        assertNotNull(base);
        assertEquals(base.getId(), 96590317);
        assertEquals(base.getUrl(), "lucas99063000");
    }

    @Test
    public void getSettingsUser() {
        Settings settings = ImgurAPI.getUserSettings(manager);
        assertNotNull(settings);
        assertEquals(settings.getAccount(), "lucas99063000");
        assertEquals(settings.getEmail(), "lucas99.06@gmail.com");
    }

    @Test
    public void testAlreadyFollow() {
        assertFalse(ImgurAPI.followTagByString(manager, "dog"));
    }

    @Test
    public void unFollowAndFollowFirstTag() {
        List<Tag> tags = ImgurAPI.getAllTags(manager);

        assertNotNull(tags);

        Tag tag = null;

        for (int i = 0; i < tags.size(); i++)
            if (tags.get(i).getFollowName().equals("pets"))
                tag = tags.get(i);

        assertNotNull(tag);

        assertTrue(ImgurAPI.unFollowTagByTag(manager, tag));
        assertTrue(ImgurAPI.followTagByTag(manager, tag));
    }

    @Test
    public void getAllUserInfo() {
        User user = ImgurAPI.getAllUserInfo(manager);

        assertNotNull(user);

        assertTrue(user.getInfo().getTrophies().size() >= 1);
    }

    @Test
    public void changeBio() {
        Date now = new Date(System.currentTimeMillis());

        Base base = ImgurAPI.getUserBase(manager,"lucas99063000");

        base.setBio(now.toString());

        ImgurAPI.changeSettings(manager, base);

        base = ImgurAPI.getUserBase(manager, "lucas99063000");


        assertEquals(base.getBio(), now.toString());
    }

    @Test
    public void getVotes() {
        Votes votes = ImgurAPI.getVotesForImage(manager, "0ygD2Tj");

        assertNotNull(votes);
        assertTrue(votes.getDowns() > 69);
    }

    @Test
    public void voteUp() {
        assertTrue(ImgurAPI.voteForImage(manager, "0ygD2Tj", Votes.VoteType.Unvote));

        Votes vote = ImgurAPI.getVotesForImage(manager, "0ygD2Tj");

        assertNotNull(vote);

        int old = vote.getUps();

        assertTrue(ImgurAPI.voteForImage(manager, "0ygD2Tj", Votes.VoteType.Up));

        vote = ImgurAPI.getVotesForImage(manager, "0ygD2Tj");

        assertNotNull(vote);

        assertTrue(old >= vote.getUps());
    }

    @Test
    public void favoriteImage() {

        boolean contains = false;

        assertTrue(ImgurAPI.favoriteAnImage(manager, "dQQO2dP")); // unfavorite 'cause previus tests

        assertTrue(ImgurAPI.favoriteAnImage(manager, "dQQO2dP"));

        for (Picture picture : ImgurAPI.getFavorites(manager, 0))
            if (picture.getId().equalsIgnoreCase("dQQO2dP"))
                contains = true;

        assertTrue(contains);

    }

    @Test
    public void getFavorites() {
        for (Picture picture : ImgurAPI.getFavorites(manager, 0)) {
            assertNotNull(picture);
        }
    }

    @Test
    public void getPicture() {
        assertNotNull(ImgurAPI.getPicture(manager, "ILiOaNX"));
    }

    @Test
    public void uploadOnExistingAlbum() {
        assertNotNull(ImgurAPI.sendPicture(manager, new PictureManager.Uploader(img, "test upload 1", "base64", "My dank meme album")));
    }

    @Test
    public void uploadOnNonExistantAlbum() {
        String date = String.valueOf(new Date().getTime());
        assertNotNull(ImgurAPI.sendPicture(manager, new PictureManager.Uploader(img, "test upqload 1", "base64",
                "je suis " + date)));

        assertNotNull(ImgurAPI.getAlbumByString(manager, "je suis " + date));

    }

    @Test
    public void favAlbum() {

        boolean contains = false;

        assertTrue(ImgurAPI.favoriteAnImage(manager, "T0j84To")); // unfavorite 'cause previus tests

        assertTrue(ImgurAPI.favoriteAnImage(manager, "T0j84To"));

        for (Picture picture : ImgurAPI.getFavorites(manager, 0))
            if (picture.getId().equalsIgnoreCase("T0j84To"))
                contains = true;

        assertTrue(contains);
    }

    @Test
    public void getPicTag() {
        List<Tag> tags = ImgurAPI.getAllTags(manager);

        Picture pic = ImgurAPI.getPicture(manager, tags.get(0).getHash());
        assertNotNull(pic.getUrl());
    }

}