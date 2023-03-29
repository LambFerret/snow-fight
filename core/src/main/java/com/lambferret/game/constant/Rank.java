package com.lambferret.game.constant;

public enum Rank {

    /**
     * 훈련병
     */
    RECRUIT(0),
    /**
     * 이등병
     */
    PRIVATE(1),
    /**
     * 일등병
     */
    PRIVATE_FIRST_CLASS(2),
    /**
     * 상병
     */
    CORPORAL(3),
    /**
     * 병장
     */
    SERGEANT(4),
    /**
     * 하사
     */
    STAFF_SERGEANT(5),
    /**
     * 중사
     */
    SERGEANT_FIRST_CLASS(6),
    /**
     * 상사
     */
    FIRST_SERGEANT(7),
    /**
     * 원사
     */
    SERGENT_MAJOR(8),
    /**
     * 주임원사
     */
    COMMAND_SERGEANT_MAJOR(9),
    /**
     * 육군주임원사
     */
    SERGEANT_MAJOR_OF_THE_ARMY(10),
    /**
     * 준위
     */
    MASTER_WARRANT_OFFICER(11),
    /**
     * 소위
     */
    SECOND_LIEUTENANT(12),
    /**
     * 중위
     */
    FIRST_LIEUTENANT(13),
    /**
     * 대위
     */
    CAPTAIN(14),
    /**
     * 소령
     */
    MAJOR(15),
    /**
     * 중령
     */
    LIEUTENANT_COLONEL(16),
    /**
     * 대령
     */
    COLONEL(17),
    /**
     * 준장
     */
    BRIGADIER_GENERAL(18),
    /**
     * 소장
     */
    MAJOR_GENERAL(19),
    /**
     * 중장
     */
    LIEUTENANT_GENERAL(20),
    /**
     * 대장
     */
    GENERAL(21),
    ;
    final int value;

    Rank(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
