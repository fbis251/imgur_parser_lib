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

package com.fernandobarillas.albumparser.giphy;

import com.fernandobarillas.albumparser.giphy.api.GiphyApi;
import com.fernandobarillas.albumparser.util.ParseUtils;

import java.net.URL;

/**
 * Utilities for Giphy URLs
 */
public class GiphyUtils {

    /**
     * @param giphyUrl The Giphy URL to get the hash from
     * @return A Giphy hash if the URL contained it, null if no hash could be found.
     */
    public static String getHash(String giphyUrl) {
        URL url = ParseUtils.getUrlObject(giphyUrl, GiphyApi.BASE_DOMAIN);
        if (url == null) return null; // Passed in String wasn't a valid URL
        String path = url.getPath();

        if (path.startsWith("/gifs/")) {
            return ParseUtils.hashRegex(path, "/gifs/(?:.*-)?(\\w+)\\/?.*$");
        }

        if (path.startsWith("/embed/")) {
            return ParseUtils.hashRegex(path, "/embed/(\\w+)");
        }

        if (path.startsWith("/media/")) {
            return ParseUtils.hashRegex(path, "/media/(\\w+)");
        }

        return ParseUtils.hashRegex(path, "/(\\w+)");
    }
}
