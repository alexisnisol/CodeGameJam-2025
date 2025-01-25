package com.smartdawgs.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.smartdawgs.game.entity.items.EntityItem;
import com.smartdawgs.game.entity.items.EntityJukebox;
import com.smartdawgs.game.items.Item;
import com.smartdawgs.game.items.ItemDisque;
import com.smartdawgs.game.world.WorldElement;

public class EntityRegister {

    // Pour créer direct les ons à chaque création d'item
    private static Sound sound(String path) {
        return Gdx.audio.newSound(Gdx.files.internal(path));
    }

        public static Sound SOUND_EQUIP = sound("items/sound/item-equip.mp3");
        public static Sound SOUND_DROP = sound("items/sound/item-drop.mp3");

    // MUSHROOM
    public static final Item MUSHROOM = new Item(
        "Mushroom",
        new Texture("items/sprite/mushroom.png"),
        null
    );

    public static final Item BLACK_KEY = new Item(
        "Black Key",
        new Texture("items/sprite/blackKey.png"),
        sound("items/sound/keys.wav")
    );

    public static final Item RED_KEY = new Item(
        "Red Key",
        new Texture("items/sprite/redKey.png"),
        sound("items/sound/keys.wav")
    );

    public static final Item CHEST_KEY = new Item(
        "Chest Key",
        new Texture("items/sprite/chestKey.png"),
        sound("items/sound/keys.wav")
    );

    public static final Item CHEST = new Item(
        "Chest",
        new Texture("items/sprite/chest.png"),
        sound("items/sound/chest.wav")
    );

    public static final Item HAMMER = new Item(
        "Hammer",
        new Texture("items/sprite/hammer.png"),
        sound("items/sound/hammer.wav")
    );

    public static final Item JOURNAL = new Item(
        "Journal",
        new Texture("items/sprite/Journal.png"),
        sound("items/sound/journaux.wav") // "journaux.wav"
    );

    public static final Item JUKEBOX = new Item(
        "JukeBox",
        new Texture("items/sprite/JukeBox_resized.png"),
        sound("items/sound/jukebox.wav")
    );

    public static final Item SCISSORS = new Item(
        "Scissors",
        new Texture("items/sprite/scissors.png"),
        sound("items/sound/scissors.wav")
    );

    public static final Item SHOVEL = new Item(
        "Shovel",
        new Texture("items/sprite/shovel.png"),
        sound("items/sound/shovel.wav")
    );

    public static final Item WATER = new Item(
        "Water",
        new Texture("items/sprite/water.png"),
        sound("items/sound/water.wav")
    );

    public static final Item BUCKET = new Item(
        "Bucket",
        new Texture("items/sprite/bucket.png"),
        null // On pourrait lui associer "water.wav" si besoin
    );

    public static final Item WATER_BUCKET = new Item(
        "Bucket of Water",
        new Texture("items/sprite/waterBucket.png"),
        sound("items/sound/water.wav")
    );

    public static final Item DEADLY_WATER = new Item(
        "DeadlyWater",
        new Texture("items/sprite/deadlyWater.png"),
        sound("items/sound/water.wav")
    );

    public static final Item FIRELIGHTER = new Item(
        "Firelighter",
        new Texture("items/sprite/firelighter.png"),
        sound("items/sound/fire.wav")
    );

    public static final Item TREASURE = new Item(
        "Treasure",
        new Texture("items/sprite/treasure.png"),
        sound("items/sound/shovel.wav") // Son de pelle puisqu'on creuse
    );

    public static final Item DISQUE_1 = new ItemDisque(
        "Disc 1",
        new Texture("items/sprite/disque1.png"),
        sound("items/sound/jukebox.wav")
    );
    public static final Item DISQUE_2 = new ItemDisque(
        "Disc 2",
        new Texture("items/sprite/disque2.png"),
        sound("items/sound/jukebox.wav")
    );
    public static final Item DISQUE_3 = new ItemDisque(
        "Disc 3",
        new Texture("items/sprite/disque3.png"),
        sound("items/sound/jukebox.wav")
    );
    public static final Item DISQUE_4 = new ItemDisque(
        "Disc 4",
        new Texture("items/sprite/disque4.png"),
        sound("items/sound/jukebox.wav")
    );

    public static void registerEntities(WorldElement world) {

        // Exemple : On enregistre quelques entités dans le monde, avec des positions espacées
        world.getEntities().add(new EntityItem(world, WATER_BUCKET).spawn(700, 850));
        world.getEntities().add(new EntityItem(world, CHEST_KEY).spawn(1500, 800));
        world.getEntities().add(new EntityItem(world, BUCKET).spawn(300, 1100));
        world.getEntities().add(new EntityItem(world, FIRELIGHTER).spawn(1800, 1800));
        world.getEntities().add(new EntityItem(world, MUSHROOM).spawn(100, 500));
        world.getEntities().add(new EntityItem(world, BLACK_KEY).spawn(900, 1200));
        world.getEntities().add(new EntityItem(world, RED_KEY).spawn(900, 400));
        // world.getEntities().add(new EntityItem(world, CHEST).spawn(450, 100)); // si besoin plus tard
        world.getEntities().add(new EntityItem(world, HAMMER).spawn(1600, 400));
        // world.getEntities().add(new EntityItem(world, TREASURE).spawn(550, 100)); // si besoin plus tard
        // world.getEntities().add(new EntityItemBook(world, JOURNAL).spawn(550, 100)); // si besoin plus tard

        world.getEntities().add(new EntityItem(world, SHOVEL).spawn(1200, 175));
        world.getEntities().add(new EntityItem(world, SCISSORS).spawn(600, 900));
        world.getEntities().add(new EntityItem(world, DISQUE_1).spawn(1700, 1400));
        world.getEntities().add(new EntityItem(world, DISQUE_2).spawn(400, 700));
        world.getEntities().add(new EntityItem(world, DISQUE_3).spawn(1200, 1400));
        world.getEntities().add(new EntityItem(world, DISQUE_4).spawn(200, 175));
        world.getEntities().add(new EntityJukebox(world, JUKEBOX).spawn(1450, 1250));

    }
}

