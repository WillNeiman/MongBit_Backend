package com.MongMoong.MongBitProject.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true) // Jackson에게 정의되지 않은 JSON 필드를 만났을 때 오류를 발생시키지 않고 무시
public class ImageUploadResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {
        private String url;

        @JsonProperty("url_viewer")
        private String urlViewer;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrlViewer() {
            return urlViewer;
        }

        public void setUrlViewer(String urlViewer) {
            this.urlViewer = urlViewer;
        }
    }
}
