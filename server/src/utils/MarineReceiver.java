package utils;

import commands.RequestData;
import exceptions.NoDataException;
import marine.Chapter;
import marine.Coordinates;
import marine.SpaceMarine;
import marine.Weapon;
import request.MarineData;
import java.time.LocalDateTime;

public class MarineReceiver {
    public SpaceMarine buildMarine(RequestData requestData, Integer id) throws NoDataException {
        if (requestData.getMarineData() != null) {
            MarineData marineData = requestData.getMarineData();
            return new SpaceMarine(id,
                    marineData.getName(),
                    new Coordinates(marineData.getX(),
                            marineData.getY()),
                    LocalDateTime.now(),
                    marineData.getHealth(),
                    marineData.getHeartCount(),
                    marineData.getLoyal(),
                    Weapon.valueOf(marineData.getWeaponType()),
                    new Chapter(marineData.getChapterName(),
                            marineData.getParentLegion()));
        }
        else throw new NoDataException();
    }

    public SpaceMarine buildMarine(RequestData requestData) throws NoDataException {
        return buildMarine(requestData, 0);
    }
}
