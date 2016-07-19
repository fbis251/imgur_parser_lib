/*
 * The MIT License (MIT)
 * Copyright (c) 2016 Fernando Barillas (FBis251)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.fernandobarillas.albumparser.deviantart;

import com.fernandobarillas.albumparser.parser.AbstractApiParser;
import com.fernandobarillas.albumparser.parser.ParserResponse;
import com.fernandobarillas.albumparser.deviantart.api.DeviantartApi;
import com.fernandobarillas.albumparser.deviantart.model.DeviantartResponse;
import com.fernandobarillas.albumparser.exception.InvalidMediaUrlException;

import java.io.IOException;
import java.net.URL;

import okhttp3.OkHttpClient;
import retrofit2.Response;

import static com.fernandobarillas.albumparser.util.ParseUtils.isDirectUrl;

/**
 * Created by fb on 5/26/16.
 */
public class DeviantartParser extends AbstractApiParser {
    public DeviantartParser(OkHttpClient client) {
        super(client);
    }

    @Override
    public ParserResponse parse(URL mediaUrl) throws InvalidMediaUrlException, IOException {
        String url = mediaUrl.toString();
        if (url == null) {
            throw new InvalidMediaUrlException();
        }

        if (isDirectUrl(mediaUrl)) {
            DeviantartResponse response = new DeviantartResponse();
            response.url = url;
            return new ParserResponse(response);
        }

        DeviantartApi service = getRetrofit(DeviantartApi.API_URL).create(DeviantartApi.class);
        Response<DeviantartResponse> response = service.getOembed(url).execute();
        DeviantartResponse deviantartResponse = response.body();
        return new ParserResponse(deviantartResponse);
    }
}
