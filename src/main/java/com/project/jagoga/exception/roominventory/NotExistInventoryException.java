package com.project.jagoga.exception.roominventory;

public class NotExistInventoryException extends RuntimeException {

    public NotExistInventoryException() {
        super("인벤토리가 존재하지 않습니다.");
    }
}