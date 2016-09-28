package com.dopa;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class DopaDaoGenerator {


    DopaDaoGenerator Dopadb;

    public DopaDaoGenerator() {


    }


    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.kuviam.dopa.model");

        //Locus table
        Entity locus = schema.addEntity("Locus");
        locus.addIdProperty().autoincrement();
        locus.addStringProperty("name").unique().notNull();
        locus.addStringProperty("creator");
        locus.addStringProperty("type");

        //Locus Text type table
        Entity locus_text_list = schema.addEntity("Locus_text_list");
        locus_text_list.addIdProperty().autoincrement();
        Property locusId1 = locus_text_list.addLongProperty("locusId").notNull().getProperty();
        //locus_text_list.addToOne(locus, locusId1);
        ToMany locusToTextItems = locus.addToMany(locus_text_list, locusId1);
        locus_text_list.addStringProperty("item").notNull();

        //Locus image type table
        Entity locus_image_list = schema.addEntity("Locus_image_list");
        locus_image_list.addIdProperty().autoincrement();
        Property locusId2 = locus_image_list.addLongProperty("locusId").notNull().getProperty();
        locus_image_list.addToOne(locus, locusId2);
        locus_image_list.addByteArrayProperty("item").notNull();

        //Locus sound type table
        Entity locus_sound_list = schema.addEntity("Locus_sound_list");
        locus_sound_list.addIdProperty().autoincrement();
        Property locusId3 = locus_sound_list.addLongProperty("locusId").notNull().getProperty();
        locus_sound_list.addToOne(locus, locusId3);
        locus_sound_list.addByteArrayProperty("item").notNull();

        //Locus sound type table
        Entity locus_number_list = schema.addEntity("Locus_number_list");
        locus_number_list.addIdProperty().autoincrement();
        Property locusId4 = locus_number_list.addLongProperty("locusId").notNull().getProperty();
        locus_number_list.addToOne(locus, locusId4);
        locus_number_list.addLongProperty("item").notNull();

        //Discipline table
        Entity discipline = schema.addEntity("Discipline");
        discipline.addIdProperty().autoincrement();
        discipline.addStringProperty("name").unique().notNull();
        discipline.addIntProperty("no_of_items").notNull();
        discipline.addStringProperty("creator");
        discipline.addBooleanProperty("is_Ordered");
        discipline.addLongProperty("practice_time");
        discipline.addLongProperty("recall_time");
        discipline.addLongProperty("per_practice_time");
        discipline.addLongProperty("per_recall_time");
        discipline.addLongProperty("runs_to_sync");

        //Discipline Text type table
        Entity discipline_text_list = schema.addEntity("Discipline_text_list");
        discipline_text_list.addIdProperty().autoincrement();
        Property disciplineId1 = discipline_text_list.addLongProperty("disciplineId").notNull().getProperty();
        //discipline_text_list.addToOne(discipline, disciplineId1);
        ToMany disciplineToTextItems = discipline.addToMany(discipline_text_list, disciplineId1);
        discipline_text_list.addStringProperty("item").notNull();

        //Discipline image type table
        Entity discipline_image_list = schema.addEntity("Discipline_image_list");
        discipline_image_list.addIdProperty().autoincrement();
        Property disciplineId2 = discipline_image_list.addLongProperty("disciplineId").notNull().getProperty();
        discipline_image_list.addToOne(discipline, disciplineId2);
        discipline_image_list.addByteArrayProperty("item").notNull();

        //Discipline sound type table
        Entity discipline_sound_list = schema.addEntity("Discipline_sound_list");
        discipline_sound_list.addIdProperty().autoincrement();
        Property disciplineId3 = discipline_sound_list.addLongProperty("disciplineId").notNull().getProperty();
        discipline_sound_list.addToOne(discipline, disciplineId3);
        discipline_sound_list.addByteArrayProperty("item").notNull();

        //Discipline sound type table
        Entity discipline_number_list = schema.addEntity("Discipline_number_list");
        discipline_number_list.addIdProperty().autoincrement();
        Property disciplineId4 = discipline_number_list.addLongProperty("disciplineId").notNull().getProperty();
        discipline_number_list.addToOne(discipline, disciplineId4);
        discipline_number_list.addLongProperty("item").notNull();

        //Run table
        Entity run = schema.addEntity("Run");
        run.addIdProperty().autoincrement();
        run.addStringProperty("discipline").notNull();
        run.addStringProperty("locus");
        run.addIntProperty("no_of_items");
        run.addLongProperty("practice_time");
        run.addLongProperty("recall_time");
        run.addLongProperty("assigned_practice_time");
        run.addLongProperty("assigned_recall_time");
        run.addStringProperty("status");
        run.addLongProperty("per_practice_time");
        run.addLongProperty("per_recall_time");
        run.addDateProperty("start_timestamp");

        //Run_Item_list table
        Entity runitemlist = schema.addEntity("Run_discipline_item_list");
        runitemlist.addIdProperty().autoincrement();
        runitemlist.addLongProperty("discipline_item").notNull();
        runitemlist.addIntProperty("recall_attempt");
        runitemlist.addIntProperty("practice_attempt");
        Property runId = runitemlist.addLongProperty("runId").notNull().getProperty();
        // runitemlist.addToOne(run, runId);
        ToMany runToItems = run.addToMany(runitemlist, runId);

        runitemlist.addBooleanProperty("Status");
        runitemlist.addLongProperty("practice_time");
        runitemlist.addLongProperty("recall_time");

        Entity dic = schema.addEntity("Dictionary");
        dic.addIdProperty().autoincrement();
        dic.addStringProperty("word").notNull();

        //Generate all tables

        new DaoGenerator().generateAll(schema, "app/src/main/java");
    }


}
