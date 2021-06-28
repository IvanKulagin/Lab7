package commands;

import marine.Weapon;
import response.Response;
import utils.CollectionManager;

public class CommandCountGreaterThanWeaponType implements Command {
    private final CollectionManager manager;

    public CommandCountGreaterThanWeaponType(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public Response execute(String[] args) {
        return new Response("Количство элементов: " + manager.getStream().filter(marine -> marine.getWeaponType().compareTo(Weapon.valueOf(args[0].toUpperCase())) > 0).count());
    }

    @Override
    public String getDescription() {
        return "Вывести количество элементов, значение поля weaponType которых больше заданного";
    }

    @Override
    public String getUsage() {
        return "Использование: count_greater_than_weapon_type weaponType";
    }
}
