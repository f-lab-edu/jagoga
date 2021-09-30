package com.project.jagoga.exception.room;

public class NotExistRoomException extends RuntimeException {

    public NotExistRoomException() {
        super("존재하지 않는 룸 입니다.");
    }
}