/*
 * @(#)WriterRepositoryCustom.java        1.1.1 2022/2/28
 *
 * Copyright (c) 2022 YoungJun Yang.
 * ComputerScience, ProgrammingLanguage, Java, Pocheon-si, KOREA
 * All rights reserved.
 */

package com.dasd412.remake.api.domain.diary.writer;

import com.dasd412.remake.api.domain.diary.profile.Profile;

import java.util.Optional;

/**
 * 작성자 리포지토리 상위 인터페이스. Querydsl을 이용하기 위해 구현하였다.
 *
 * @author 양영준
 * @version 1.1.1 2022년 2월 28일
 */
public interface WriterRepositoryCustom {

    /**
     * @return 식별자의 최댓값. 작성자 생성 시 id를 지정하기 위해 사용된다.
     */
    Long findMaxOfId();

    /**
     * 회원 탈퇴 시 작성한 일지 및 연관된 하위 엔티티들 (식단, 음식) 모두 "한꺼번에" 삭제하는 메서드
     *
     * @param writerId 작성자 id
     */
    void bulkDeleteWriter(Long writerId);

    Optional<Writer> findWriterByName(String name);

    Boolean existsName(String name);

    Boolean existsEmail(String email, String provider);

    /**
     *
     * @param writerId 작성자 id
     * @return 작성자와의 1대1 관계인 프로필 정보
     */
    Optional<Profile> findProfile(Long writerId);

}
