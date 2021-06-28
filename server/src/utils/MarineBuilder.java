package utils;

import marine.Chapter;
import marine.Coordinates;
import marine.SpaceMarine;
import marine.Weapon;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class MarineBuilder {
    private Integer id;
    private String name;
    private double x;
    private double y;
    private LocalDateTime creationDate;
    private int health;
    private Integer heartCount;
    private Boolean loyal;
    private Weapon weaponType;
    private String chapterName;
    private String parentLegion;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate.toLocalDateTime();
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setHeartCount(Integer heartCount) {
        this.heartCount = heartCount;
    }

    public void setLoyal(Boolean loyal) {
        this.loyal = loyal;
    }

    public void setWeaponType(String weaponType) {
        this.weaponType = Weapon.valueOf(weaponType);
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public void setParentLegion(String parentLegion) {
        this.parentLegion = parentLegion;
    }

    public SpaceMarine buildMarine() {
        return new SpaceMarine(id, name, new Coordinates(x, y), creationDate, health, heartCount, loyal, weaponType, new Chapter(chapterName, parentLegion));
    }
}
