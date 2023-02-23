package com.lambferret.game.soldier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Soldier implements Comparable<Soldier> {
    private static final Logger logger = LogManager.getLogger(Soldier.class.getName());
    public String ID;
    public String name;
    public String description;
    public int rank;
    public String texturePath;
    public float speed;
    public int rangeX;
    public int rangeY;
    public float runAwayProbability;

    public Soldier(
        String ID, String name, String description, int rank, String texturePath,
        float speed, int rangeX, int rangeY
    ) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.rank = rank;
        this.texturePath = texturePath;
        this.speed = speed;
        this.rangeX = rangeX;
        this.rangeY = rangeY;
    }

    @Override
    public int compareTo(Soldier o) {
        return this.ID.compareTo(o.ID);
    }

    static public class Rank {

        /**
         * 훈련병
         */
        public static final int RECRUIT = 1;
        /**
         * 이등병
         */
        public static final int PRIVATE = 2;
        /**
         * 일등병
         */
        public static final int PRIVATE_FIRST_CLASS = 3;
        /**
         * 상병
         */
        public static final int CORPORAL = 4;
        /**
         * 병장
         */
        public static final int SERGEANT = 5;
        /**
         * 하사
         */
        public static final int STAFF_SERGEANT = 6;
        /**
         * 중사
         */
        public static final int SERGEANT_FIRST_CLASS = 7;
        /**
         * 상사
         */
        public static final int FIRST_SERGEANT = 8;
        /**
         * 원사
         */
        public static final int SERGENT_MAJOR = 9;
        /**
         * 주임원사
         */
        public static final int COMMAND_SERGEANT_MAJOR = 10;
        /**
         * 육군주임원사
         */
        public static final int SERGEANT_MAJOR_OF_THE_ARMY = 11;
        /**
         * 준위
         */
        public static final int MASTER_WARRANT_OFFICER = 12;
        /**
         * 소위
         */
        public static final int SECOND_LIEUTENANT = 13;
        /**
         * 중위
         */
        public static final int FIRST_LIEUTENANT = 14;
        /**
         * 대위
         */
        public static final int CAPTAIN = 15;
        /**
         * 소령
         */
        public static final int MAJOR = 16;
        /**
         * 중령
         */
        public static final int LIEUTENANT_COLONEL = 17;
        /**
         * 대령
         */
        public static final int COLONEL = 18;
        /**
         * 준장
         */
        public static final int BRIGADIER_GENERAL = 19;
        /**
         * 소장
         */
        public static final int MAJOR_GENERAL = 20;
        /**
         * 중장
         */
        public static final int LIEUTENANT_GENERAL = 21;
        /**
         * 대장
         */
        public static final int GENERAL = 22;

    }

}






















