package com.paperless.api.core.utils;

import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

public class ImageUrlConverter {

    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList(
            "image/jpeg", "image/jpg", "image/png", "image/gif", "image/webp"
    );

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(
            "jpg", "jpeg", "png", "gif", "webp"
    );

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    private static final int CONNECT_TIMEOUT = 10000; // 10초
    private static final int READ_TIMEOUT = 30000; // 30초

    /**
     * URL에서 이미지를 다운로드하여 MultipartFile로 변환합니다.
     *
     * @param imageUrl 이미지 URL
     * @return MultipartFile 객체
     * @throws IllegalArgumentException URL이 유효하지 않거나 지원하지 않는 파일 형식인 경우
     * @throws RuntimeException 다운로드 실패 또는 파일 크기 초과시
     */
    public static MultipartFile convertUrlToMultipartFile(String imageUrl) {
        validateUrl(imageUrl);

        try {
            URL url = new URL(imageUrl);
            URLConnection connection = setupConnection(url);

            String contentType = validateContentType(connection);
            String filename = generateFilename(imageUrl, contentType);

            byte[] imageData = downloadImage(connection);

            return createMultipartFile(imageData, filename, contentType);

        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("잘못된 URL 형식입니다: " + imageUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("이미지 다운로드에 실패했습니다: " + imageUrl, e);
        }
    }

    private static void validateUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("이미지 URL이 비어있습니다.");
        }

        String lowerUrl = imageUrl.toLowerCase();
        if (!lowerUrl.startsWith("http://") && !lowerUrl.startsWith("https://")) {
            throw new IllegalArgumentException("HTTP 또는 HTTPS URL만 지원됩니다.");
        }
    }

    private static URLConnection setupConnection(URL url) throws IOException {
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
        connection.setConnectTimeout(CONNECT_TIMEOUT);
        connection.setReadTimeout(READ_TIMEOUT);
        return connection;
    }

    private static String validateContentType(URLConnection connection) {
        String contentType = connection.getContentType();
        if (contentType != null) {
            contentType = contentType.split(";")[0].trim().toLowerCase();
        }

        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType)) {
            throw new IllegalArgumentException("지원하지 않는 이미지 형식입니다. 지원 형식: " + ALLOWED_CONTENT_TYPES);
        }

        return contentType;
    }

    private static byte[] downloadImage(URLConnection connection) throws IOException {
        try (InputStream inputStream = connection.getInputStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[8192];
            int bytesRead;
            long totalBytesRead = 0;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                totalBytesRead += bytesRead;
                if (totalBytesRead > MAX_FILE_SIZE) {
                    throw new RuntimeException("파일 크기가 너무 큽니다. 최대 크기: " + (MAX_FILE_SIZE / 1024 / 1024) + "MB");
                }
                outputStream.write(buffer, 0, bytesRead);
            }

            return outputStream.toByteArray();
        }
    }

    private static String generateFilename(String imageUrl, String contentType) {
        // URL에서 파일명 추출 시도
        String filename = extractFilenameFromUrl(imageUrl);
        if (filename != null) {
            return filename;
        }

        // Content-Type에서 확장자 추출하여 파일명 생성
        String extension = getExtensionFromContentType(contentType);
        return "image_" + System.currentTimeMillis() + "." + extension;
    }

    private static String extractFilenameFromUrl(String imageUrl) {
        try {
            String path = new URL(imageUrl).getPath();
            if (path.contains("/")) {
                String filename = path.substring(path.lastIndexOf("/") + 1);
                if (filename.contains(".")) {
                    String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
                    if (ALLOWED_EXTENSIONS.contains(extension)) {
                        return filename;
                    }
                }
            }
        } catch (Exception e) {
            // URL 파싱 실패시 null 반환
        }
        return null;
    }

    private static String getExtensionFromContentType(String contentType) {
        switch (contentType) {
            case "image/jpeg":
            case "image/jpg":
                return "jpg";
            case "image/png":
                return "png";
            case "image/gif":
                return "gif";
            case "image/webp":
                return "webp";
            default:
                return "jpg"; // 기본값
        }
    }

    private static MultipartFile createMultipartFile(byte[] data, String filename, String contentType) {
        return new MultipartFile() {
            @Override
            public String getName() {
                return "file";
            }

            @Override
            public String getOriginalFilename() {
                return filename;
            }

            @Override
            public String getContentType() {
                return contentType;
            }

            @Override
            public boolean isEmpty() {
                return data.length == 0;
            }

            @Override
            public long getSize() {
                return data.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return data.clone(); // 원본 데이터 보호를 위한 복사
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(data);
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                try (FileOutputStream fos = new FileOutputStream(dest)) {
                    fos.write(data);
                }
            }
        };
    }
}