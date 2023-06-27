package com.MongMoong.MongBitProject.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageUploadResponse {

    private int status_code;
    private Data data;
    private Error error;

    public int getStatus() {
        return status_code;
    }

    public void setStatus(int status_code) {
        this.status_code = status_code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
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

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Error {
        private String message;
        private int code;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }
}
