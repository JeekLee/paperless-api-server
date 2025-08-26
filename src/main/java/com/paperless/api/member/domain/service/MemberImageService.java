package com.paperless.api.member.domain.service;

import com.paperless.api.core.utils.AmazonS3Util;
import com.paperless.api.core.utils.ImageUrlConverter;
import com.paperless.api.member.domain.model.vo.MemberImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberImageService {
    private final AmazonS3Util  amazonS3Util;

    private final static String DIRECTORY = "member/image";
    // TODO: Valid 어노테이션 활용하는 방향으로 변경
    private final static List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "gif");

    public MemberImage create(String imageUrl) {
        MultipartFile imageFile = ImageUrlConverter.convertUrlToMultipartFile(imageUrl);
        String path = amazonS3Util.upload(DIRECTORY, imageFile);
        return new MemberImage(path);
    }

    public MemberImage create(MultipartFile imageFile) {
        String path = amazonS3Util.upload(DIRECTORY, imageFile);
        return new MemberImage(path);
    }

    public void delete(MemberImage memberImage) {
        amazonS3Util.delete(memberImage.path());
    }
}
