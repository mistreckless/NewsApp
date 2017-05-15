package com.upreckless.support.newsapp.util;

import com.upreckless.support.newsapp.domain.data.Channel;
import com.upreckless.support.newsapp.domain.data.Item;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by @mistreckless on 09.05.2017. !
 */

public class NewsConverterFactory extends Converter.Factory {
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new Converter<ResponseBody, List<Item>>() {
            @Override
            public List<Item> convert(ResponseBody value) throws IOException {
                return convertHtmlToListItem(value.string());
            }
        };
    }

    private List<Item> convertHtmlToListItem(String html) {
        int startIndex = html.indexOf("<channel>");
        int endIndex = html.indexOf("</channel>") + 10;
        String channelLine = html.substring(startIndex, endIndex);
        channelLine = channelLine.replaceAll("&", "&amp;");
//        channelLine=channelLine.replaceAll("?","&#63");
        Serializer serializer = new Persister();
        try {
            Channel channel = serializer.read(Channel.class, channelLine);
            return channel.getItems();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
