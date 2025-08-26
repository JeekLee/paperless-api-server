package com.paperless.api.member.domain.service;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class NicknameGenerator {
    private final Random random = new SecureRandom();

    private final List<String> adjectives = Arrays.asList(
            "귀여운", "멋진", "똑똑한", "용감한", "친절한", "활발한", "조용한", "차분한",
            "밝은", "따뜻한", "시원한", "빠른", "느린", "큰", "작은", "높은",
            "깊은", "넓은", "좁은", "강한", "약한", "새로운", "오래된", "젊은",
            "늙은", "예쁜", "잘생긴", "건강한", "행복한", "슬픈", "기쁜", "화난",
            "평화로운", "자유로운", "신비한", "마법의", "특별한", "일반적인", "독특한", "평범한",
            "화려한", "수줍은", "대담한", "정직한", "성실한", "게으른", "부지런한", "꼼꼼한",
            "대충한", "정확한", "민첩한", "느긋한", "급한", "천천히하는", "신중한", "무모한"
    );

    private final List<String> animals = Arrays.asList(
            "고양이", "강아지", "토끼", "햄스터", "기니피그", "앵무새", "카나리아", "금붕어",
            "사자", "호랑이", "표범", "치타", "코끼리", "기린", "하마", "코뿔소",
            "원숭이", "고릴라", "침팬지", "오랑우탄", "판다", "코알라", "캥거루", "왈라비",
            "늑대", "여우", "너구리", "오소리", "다람쥐", "청설모", "비버", "수달",
            "곰", "북극곰", "펭귄", "바다표범", "고래", "돌고래", "상어", "문어",
            "독수리", "매", "부엉이", "까마귀", "참새", "비둘기", "제비", "까치",
            "사슴", "멧돼지", "양", "염소", "말", "얼룩말", "당나귀", "라마",
            "알파카", "미어캣", "프레리독", "치와와", "하이에나", "말티즈", "푸들", "리트리버"
    );

    private String generateRandomNumber() {
        return String.format("%04d", random.nextInt(10000));
    }

    public String generate() {
        return adjectives.get(random.nextInt(adjectives.size()))
                + animals.get(random.nextInt(animals.size()))
                + "#"
                + generateRandomNumber();
    }

    public String generate(String nickname) {
        return nickname + "#" + generateRandomNumber();
    }
}