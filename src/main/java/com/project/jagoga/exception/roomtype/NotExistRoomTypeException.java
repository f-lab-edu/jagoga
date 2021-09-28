package com.project.jagoga.exception.roomtype;

public class NotExistRoomTypeException extends RuntimeException {

    public NotExistRoomTypeException() {
        super("존재하지 않는 룸타입 입니다.");
    }
}