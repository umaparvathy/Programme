package com.uv.programme;

import android.content.Context;
import android.util.Log;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.SearchResultSnippet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by venkatsr on 17/11/15.
 */
public class YoutubeConnector {

    private static String TAG = "YoutubeConnector";
    public YouTube getYoutube() {
        return youtube;
    }

    private YouTube youtube;
    private YouTube.Search.List query;
    YouTube.Channels.List channelRequest;
    private YouTube.PlaylistItems.List playListQuery;

    // Your developer key goes here
    public static final String KEY
            = "AIzaSyAXyt6CMlS_oZEuWzOKS532S0R-6f4rd0k";

    private static String PLAYLIST_ID = "PLI5ma6i7XpgAsu-pCqD7uHYnArp_ZbjFV";
    private static String CHANNEL_ID = "UCcjlxxTFS72ViNvac73y_Xw";

    /*public YoutubeConnector(Context context) {
        Log.e(TAG, "In YoutubeConnector constructor");
        youtube = new YouTube.Builder(new NetHttpTransport(),
                new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest hr) throws IOException {}
        }).setApplicationName(context.getString(R.string.app_name)).build();

        try{
            query = youtube.search().list("id,snippet");
            //query.setChannelId("UCZ_aqpr_IHnkIDEl7lYox3g");
            query.setChannelId("UCcjlxxTFS72ViNvac73y_Xw");
            query.setKey(KEY);
            query.setType("video");
            query.setOrder("date");
            query.setMaxResults(50l);
            query.setFields("items(id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url)");
            //query.setFields("items(id/videoId,snippet/title,snippet/publishedAt,snippet/description,snippet/thumbnails/default/url)");
            Log.e(TAG, "Finished YoutubeConnector constructor");
        }catch(IOException e){
            Log.d("YC", "Could not initialize: " + e);
            e.printStackTrace();
        }
    }*/

    public YoutubeConnector(Context context) {
        Log.e(TAG, "In YoutubeConnector constructor");
        youtube = new YouTube.Builder(new NetHttpTransport(),
                new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest hr) throws IOException {}
        }).setApplicationName(context.getString(R.string.app_name)).build();


        try{
            channelRequest = youtube.channels().list("contentDetails");
            channelRequest.setId(CHANNEL_ID);
            channelRequest.setKey(KEY);
            channelRequest.setFields("items/contentDetails,nextPageToken,pageInfo");
        }catch(IOException e){
            Log.d("YC", "Could not initialize: " + e);
        } catch(Exception e){
            Log.e(TAG, "New Exception in Constructor" + e);
            e.printStackTrace();
        }
        Log.e(TAG, "Finished YoutubeConnector constructor");

    }

    public List<VideoItem> search(){
        Log.e(TAG, "In YoutubeConnector.search");
        try{
            SearchListResponse response = query.execute();
            Log.e(TAG, "In YoutubeConnector.search - Executed");

            List<SearchResult> results = response.getItems();

            List<VideoItem> items = new ArrayList<VideoItem>();
            for(SearchResult result:results){
                VideoItem item = new VideoItem();

                SearchResultSnippet snippet = result.getSnippet();
                item.setTitle(snippet.getTitle());
                item.setDescription(snippet.getDescription());
                Log.e(TAG, "Done YoutubeConnector.search VideoURL: " + snippet.getThumbnails().getDefault().getUrl());
                item.setThumbnailURL(snippet.getThumbnails().getDefault().getUrl());
                item.setId(result.getId().getVideoId());
                items.add(item);
            }
            Log.e(TAG, "Done YoutubeConnector.search");
            return items;
        }catch(IOException e){
            Log.d("YC", "Could not search: " + e);
            return null;
        }
    }

    public List<VideoItem> searchPlayList(){
        Log.e(TAG, "In YoutubeConnector.searchPlayList");
        List<VideoItem> items = new ArrayList<VideoItem>();
        try{

            ChannelListResponse channelResult = channelRequest.execute();
            Log.e(TAG, "In YoutubeConnector.searchPlayList - Executed ChannelRequest");
            List<Channel> channelsList = channelResult.getItems();

            if (channelsList != null) {
                // The user's default channel is the first item in the list.
                // Extract the playlist ID for the channel's videos from the
                // API response.
                Log.e(TAG, "In YoutubeConnector.searchPlayList - channelsList is not null");
                String uploadPlaylistId =
                        channelsList.get(0).getContentDetails().getRelatedPlaylists().getUploads();

                Log.e(TAG, "In YoutubeConnector.searchPlayList uploadPlayListId: " + uploadPlaylistId);
                // Retrieve the playlist of the channel's uploaded videos.
                YouTube.PlaylistItems.List playlistItemRequest =
                        youtube.playlistItems().list("id,contentDetails,snippet");
                /*YouTube.PlaylistItems.List playlistItemRequest =
                        youtube.playlistItems().list("id,snippet");*/
                playlistItemRequest.setPlaylistId(PLAYLIST_ID);
                playlistItemRequest.setKey(KEY);



                // Only retrieve data used in this application, thereby making
                // the application more efficient. See:
                // https://developers.google.com/youtube/v3/getting-started#partial
                playlistItemRequest.setFields(
                        "items(contentDetails/videoId,snippet/title,snippet/description,snippet/publishedAt,snippet/thumbnails/default/url),nextPageToken,pageInfo");

                //playlistItemRequest.setFields("items(id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url),nextPageToken,pageInfo");
                playlistItemRequest.setMaxResults(50l);
                String nextToken = "";

                // Call the API one or more times to retrieve all items in the
                // list. As long as the API response returns a nextPageToken,
                // there are still more items to retrieve.
                do {
                    Log.e(TAG, "In YoutubeConnector.searchPlayList do..while loop");
                    playlistItemRequest.setPageToken(nextToken);
                    Log.e(TAG, "In YoutubeConnector.searchPlayList setPageToken");
                    PlaylistItemListResponse playlistItemResult = playlistItemRequest.execute();
                    Log.e(TAG, "In YoutubeConnector.searchPlayList executing playlist request ");
                    List<PlaylistItem> results = playlistItemResult.getItems();
                    for(PlaylistItem result:results){
                        Log.e(TAG, "In YoutubeConnector.searchPlayList inside for loop ");
                        VideoItem item = new VideoItem();

                        PlaylistItemSnippet snippet = result.getSnippet();
                        item.setTitle(snippet.getTitle());
                        item.setDescription(snippet.getDescription());
                        item.setThumbnailURL(snippet.getThumbnails().getDefault().getUrl());
                        Log.e(TAG, "Done YoutubeConnector.search VideoURL: " + snippet.getThumbnails().getDefault().getUrl());
                        item.setId(result.getContentDetails().getVideoId());
                        Log.e(TAG, "Done YoutubeConnector.search ID: " + result.getContentDetails().getVideoId());
                        items.add(item);

                    }
                    nextToken = playlistItemResult.getNextPageToken();
                } while (nextToken != null);
            }
            Log.e(TAG, "Done YoutubeConnector.searchPlayList");
            return items;
        }catch(IOException e){
            Log.d("YC", "Could not search: "+e);
            return null;
        } catch(Exception e){
            Log.e(TAG, "New Exception in searchPlayList" + e);
            e.printStackTrace();
            return null;
        } catch(Throwable e){
            Log.e(TAG, "New Throwable Exception in searchPlayList " + e);
            e.printStackTrace();
            return null;
        }
    }

    /*public List<VideoItem> searchPlayList(){
        Log.e(TAG, "In YoutubeConnector.searchPlayList");
        List<VideoItem> items = new ArrayList<VideoItem>();
        try{

            ChannelListResponse channelResult = channelRequest.execute();
            Log.e(TAG, "In YoutubeConnector.searchPlayList - Executed ChannelRequest");
            List<Channel> channelsList = channelResult.getItems();

            if (channelsList != null) {
                // The user's default channel is the first item in the list.
                // Extract the playlist ID for the channel's videos from the
                // API response.
                Log.e(TAG, "In YoutubeConnector.searchPlayList - channelsList is not null");
                String uploadPlaylistId =
                        channelsList.get(0).getContentDetails().getRelatedPlaylists().getUploads();

                Log.e(TAG, "In YoutubeConnector.searchPlayList uploadPlayListId: " + uploadPlaylistId);
                // Retrieve the playlist of the channel's uploaded videos.
                YouTube.PlaylistItems.List playlistItemRequest =
                        youtube.playlistItems().list("id,contentDetails,snippet");
                playlistItemRequest.setPlaylistId(uploadPlaylistId);
                playlistItemRequest.setKey(KEY);
                // Only retrieve data used in this application, thereby making
                // the application more efficient. See:
                // https://developers.google.com/youtube/v3/getting-started#partial
                playlistItemRequest.setFields(
                        "items(id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url)");
                PlaylistItemListResponse playlistItemResult = playlistItemRequest.execute();
                Log.e(TAG, "In YoutubeConnector.searchPlayList executing playlist request ");
                List<PlaylistItem> results = playlistItemResult.getItems();
                for(PlaylistItem result:results){
                    Log.e(TAG, "In YoutubeConnector.searchPlayList inside for loop ");
                    VideoItem item = new VideoItem();

                    PlaylistItemSnippet snippet = result.getSnippet();
                    item.setTitle(snippet.getTitle());
                    item.setDescription(snippet.getDescription());
                    item.setThumbnailURL(snippet.getThumbnails().getDefault().getUrl());
                    item.setId(result.getId());
                    items.add(item);

                }
            }
            Log.e(TAG, "Done YoutubeConnector.searchPlayList");
            return items;
        }catch(IOException e){
            Log.d("YC", "Could not search: "+e);
            return null;
        } catch(Exception e){
            Log.e(TAG, "New Exception in searchPlayList" + e);
            e.printStackTrace();
            return null;
        } catch(Throwable e){
            Log.e(TAG, "New Throwable Exception in searchPlayList " + e);
            e.printStackTrace();
            return null;
        }
    }*/
}
