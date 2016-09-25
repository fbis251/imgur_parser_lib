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

package com.fernandobarillas.albumparser.imgur.model.v3;

import com.fernandobarillas.albumparser.media.BaseApiResponse;
import com.fernandobarillas.albumparser.media.IMedia;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ImageResponseV3 extends BaseApiResponse {
    @SerializedName("data")
    @Expose
    public ImageDataV3 data;
    @SerializedName("success")
    @Expose
    public boolean     success;
    @SerializedName("status")
    @Expose
    public int         status;

    @Override
    public IMedia getMedia() {
        return data;
    }

    @Override
    public boolean isSuccessful() {
        return success && data != null;
    }

    @Override
    public String toString() {
        return "ImageResponse{"
                + "data="
                + data
                + ", success="
                + success
                + ", status="
                + status
                + '}';
    }

    public void setLowQuality(String lowQuality) {
        if (data == null) return;
        data.setLowQuality(lowQuality);
    }

    public void setPreviewQuality(String previewQuality) {
        if (data == null) return;
        data.setPreviewQuality(previewQuality);
    }
}
