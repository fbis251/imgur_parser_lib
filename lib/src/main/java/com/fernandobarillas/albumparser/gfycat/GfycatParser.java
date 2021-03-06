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

package com.fernandobarillas.albumparser.gfycat;

import com.fernandobarillas.albumparser.exception.InvalidMediaUrlException;
import com.fernandobarillas.albumparser.gfycat.api.GfycatApi;
import com.fernandobarillas.albumparser.gfycat.model.GfycatInfoResponse;
import com.fernandobarillas.albumparser.parser.AbstractApiParser;
import com.fernandobarillas.albumparser.parser.ParserResponse;
import com.fernandobarillas.albumparser.util.ParseUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Set;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import retrofit2.Response;

/**
 * Parser for the Gfycat API
 */
public class GfycatParser extends AbstractApiParser {

    private static final String  GIFS_PATH    = "gifs";
    private static final String  DETAIL_PATH  = "detail";
    private static final Pattern HASH_PATTERN = Pattern.compile("(\\w+)");

    public GfycatParser() {
    }

    public GfycatParser(OkHttpClient client) {
        super(client);
    }

    @Override
    public String getApiUrl() {
        return GfycatApi.API_URL;
    }

    @Override
    public String getBaseDomain() {
        return GfycatApi.BASE_DOMAIN;
    }

    @Override
    public String getHash(URL mediaUrl) {
        if (!isValidDomain(mediaUrl)) {
            throw new InvalidMediaUrlException(mediaUrl);
        }
        String hash = null;
        String[] pathSegments = ParseUtils.getSplitPath(mediaUrl);
        int segmentCount = pathSegments != null ? pathSegments.length : 0;

        if (segmentCount >= 3) {
            // /gifs/details/{hash} URL
            if (GIFS_PATH.equalsIgnoreCase(pathSegments[0]) && DETAIL_PATH.equalsIgnoreCase(
                    pathSegments[1])) {
                hash = ParseUtils.hashRegex(pathSegments[2], HASH_PATTERN);
            }
        } else if (segmentCount >= 1) {
            // /{hash} URL
            hash = ParseUtils.hashRegex(pathSegments[0], HASH_PATTERN);
        }

        if (hash == null) throw new InvalidMediaUrlException(mediaUrl);
        return hash;
    }

    @Override
    public Set<String> getValidDomains() {
        return GfycatApi.VALID_DOMAINS_SET;
    }

    @Override
    public ParserResponse parse(URL mediaUrl) throws IOException, RuntimeException {
        String hash = getHash(mediaUrl);
        if (hash == null) {
            throw new InvalidMediaUrlException(mediaUrl);
        }

        GfycatApi service = getRetrofit().create(GfycatApi.class);
        Response<GfycatInfoResponse> serviceResponse = service.getGfycatInfo(hash).execute();
        GfycatInfoResponse apiResponse = serviceResponse.body();
        return getParserResponse(mediaUrl, apiResponse, serviceResponse);
    }
}
