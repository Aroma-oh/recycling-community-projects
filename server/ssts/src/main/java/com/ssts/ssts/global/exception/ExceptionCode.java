package com.ssts.ssts.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

    //TODO 사용자한테 노출되는 예외랑 개발자한테만 보이는 예외를 나눠야할까?

    //사용자 관련 에러
    MEMBER_NOT_FOUND(RtnHttpStatus.Validation, 2000, "사용자를 찾을 수 없어요."),
    INVALID_MEMBER_STATUS(RtnHttpStatus.Validation, 2004, "Invalid member status"),
    EMAIL_DUPLICATE(RtnHttpStatus.Validation, 2002, "이메일이 중복되었습니다"),
    PHONENUMBER_DUPLICATE(RtnHttpStatus.Validation,2002, "휴대폰 번호가 중복되었습니다"),
    NICKNAME_DUPLICATE(RtnHttpStatus.Validation,2008, "닉네임이 중복되었어요."),

    //Auth 에러
    SECURITY_TEST_LOGIN_NO_MEMBER(RtnHttpStatus.Validation,2009, "Test용 로그인 에러: 사용자 없어요"),
    SECURITY_GUEST_OBJECT_SERIALIZE_ERROR(RtnHttpStatus.Validation,2006, "GUEST object information json serialization error"),
    SECURITY_NO_CREDENTIALS(RtnHttpStatus.Validation,2007,"사용자 정보가 없습니다"),

    //시리즈 에러
    SERIES_NOT_EXISTS(RtnHttpStatus.Validation, 2005, "시리즈를 찾을 수 없습니다"),
    NOT_ALLOWED_PERMISSION(RtnHttpStatus.Validation,2010, "허용되지 않은 사용자입니다."),

    //팔로우 에러
    FOLLOW_NOT_AVAILABLE(RtnHttpStatus.Validation,1050, "팔로우 할 수 없는 상대예요."),
    IS_ALREADY_FOLLOWING(RtnHttpStatus.Validation,1051,"이미 팔로우한 상대예요."),
    IS_ALREADY_UNFOLLOWING(RtnHttpStatus.Validation,1051,"팔로우한 상대가 아니예요."),

    //투표 에러
    MEMBER_ALREADY_VOTE(RtnHttpStatus.Validation,1001, "더 이상의 투표는 불가능해요."),
    VOTE_NOT_FOUND(RtnHttpStatus.Validation,1002, "존재하지 않는 투표예요."),
    CAN_NOT_MAKE_VOTE(RtnHttpStatus.Validation,1003, "더 이상 투표를 개설할 수 없어요."),
    DEADLINE_FALL_SHORT(RtnHttpStatus.Validation,1004, "이미 투표가 진행중이예요."),
    NOT_HAVE_VOTE_AUTHORITY(RtnHttpStatus.Validation,1005, "투표를 종료할 수 없는 사용자예요."),
    VOTE_RESULT_IS_NOT_EXSIST(RtnHttpStatus.Validation,1006, "투표 결과가 존재하지 않아요."),
    THIS_VOTE_RESULT_IS_TRUE(RtnHttpStatus.Validation,1007, "이 투표는 이미 졸업했어요!"),
    CAN_NOT_VOTE_VALUE(RtnHttpStatus.Validation,1008, "투표할 수 없는 값이예요."),

    //북마크 에러
    BOOKMARK_IS_DUPLICATION(RtnHttpStatus.Validation,1009, "이미 북마크한 시리즈예요."),

    //뱃지 에러
    BADGE_NOT_FOUND(RtnHttpStatus.Validation, 1010, "존재하지 않는 뱃지예요."),
    ALREADY_HAVE_BADGE(RtnHttpStatus.Validation, 1011, "이미 취득한 뱃지예요.");


    private RtnHttpStatus rtnHttpStatus;
    private int code;
    private String message;

}
