package com.zinzin.autochessguide.utils;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zinzin.autochessguide.R;
import com.zinzin.autochessguide.model.ClassList;
import com.zinzin.autochessguide.model.Creep;
import com.zinzin.autochessguide.model.RaceList;
import com.zinzin.autochessguide.model.Units;

import java.lang.reflect.Type;
import java.util.List;

public class SetImage {
    public static List<Units> fullUnitsList(Activity activity){
        String text = Utils.parseFile(activity,"Units.txt");
        Gson gson = new Gson();
        Type type = new TypeToken<List<Units>>() {}.getType();
        List<Units> unitsList = gson.fromJson(text, type);
        unitsList.get(0).setFull_image(R.drawable.slark_f);
        unitsList.get(0).setIcon_image(R.drawable.a1);
        unitsList.get(0).setMini_image(R.drawable.slark_ava);
        unitsList.get(0).setSkill_image(R.drawable.slark_skill);
        unitsList.get(0).setColor_name(R.color.color_cost_2);

        unitsList.get(1).setFull_image(R.drawable.slardar_full);
        unitsList.get(1).setIcon_image(R.drawable.a2);
        unitsList.get(1).setMini_image(R.drawable.slardar_ava);
        unitsList.get(1).setSkill_image(R.drawable.slardar_skill);
        unitsList.get(1).setColor_name(R.color.color_cost_2);

        unitsList.get(2).setFull_image(R.drawable.omniknight_full);
        unitsList.get(2).setIcon_image(R.drawable.a3);
        unitsList.get(2).setMini_image(R.drawable.omniknight_ava);
        unitsList.get(2).setSkill_image(R.drawable.omniknight_skill);
        unitsList.get(2).setColor_name(R.color.color_cost_3);

        unitsList.get(3).setFull_image(R.drawable.troll_warlord_full);
        unitsList.get(3).setIcon_image(R.drawable.a4);
        unitsList.get(3).setMini_image(R.drawable.troll_warlord_ava);
        unitsList.get(3).setSkill_image(R.drawable.troll_warlord_skill);
        unitsList.get(3).setColor_name(R.color.color_cost_4);

        unitsList.get(4).setFull_image(R.drawable.lich_full);
        unitsList.get(4).setIcon_image(R.drawable.a5);
        unitsList.get(4).setMini_image(R.drawable.lich_ava);
        unitsList.get(4).setSkill_image(R.drawable.lich_skill);
        unitsList.get(4).setColor_name(R.color.color_cost_5);

        unitsList.get(5).setFull_image(R.drawable.enigma_full);
        unitsList.get(5).setIcon_image(R.drawable.a6);
        unitsList.get(5).setMini_image(R.drawable.enigma_ava);
        unitsList.get(5).setSkill_image(R.drawable.enigma_skill);
        unitsList.get(5).setColor_name(R.color.color_cost_5);

        unitsList.get(6).setFull_image(R.drawable.shadow_shaman_full);
        unitsList.get(6).setIcon_image(R.drawable.a7);
        unitsList.get(6).setMini_image(R.drawable.shadow_shaman_ava);
        unitsList.get(6).setSkill_image(R.drawable.shadow_shaman_skill);
        unitsList.get(6).setColor_name(R.color.color_cost_1);

        unitsList.get(7).setFull_image(R.drawable.witch_doctor_full);
        unitsList.get(7).setIcon_image(R.drawable.a8);
        unitsList.get(7).setMini_image(R.drawable.witch_doctor_ava);
        unitsList.get(7).setSkill_image(R.drawable.witch_doctor_skill);
        unitsList.get(7).setColor_name(R.color.color_cost_2);

        unitsList.get(8).setFull_image(R.drawable.techies_full);
        unitsList.get(8).setIcon_image(R.drawable.a9);
        unitsList.get(8).setMini_image(R.drawable.techies_ava);
        unitsList.get(8).setSkill_image(R.drawable.techies_skill);
        unitsList.get(8).setColor_name(R.color.color_cost_5);

        unitsList.get(9).setFull_image(R.drawable.doom_full);
        unitsList.get(9).setIcon_image(R.drawable.a10);
        unitsList.get(9).setMini_image(R.drawable.doom_ava);
        unitsList.get(9).setSkill_image(R.drawable.doom_skill);
        unitsList.get(9).setColor_name(R.color.color_cost_4);

        unitsList.get(10).setFull_image(R.drawable.dragon_knight_full);
        unitsList.get(10).setIcon_image(R.drawable.a11);
        unitsList.get(10).setMini_image(R.drawable.dragon_knight_ava);
        unitsList.get(10).setSkill_image(R.drawable.dragon_knight_skill);
        unitsList.get(10).setColor_name(R.color.color_cost_4);

        unitsList.get(11).setFull_image(R.drawable.sniper_full);
        unitsList.get(11).setIcon_image(R.drawable.a12);
        unitsList.get(11).setMini_image(R.drawable.sniper_ava);
        unitsList.get(11).setSkill_image(R.drawable.sniper_skill);
        unitsList.get(11).setColor_name(R.color.color_cost_3);

        unitsList.get(12).setFull_image(R.drawable.drow_ranger_full);
        unitsList.get(12).setIcon_image(R.drawable.a13);
        unitsList.get(12).setMini_image(R.drawable.drow_ranger_ava);
        unitsList.get(12).setSkill_image(R.drawable.drow_ranger_skill);
        unitsList.get(12).setColor_name(R.color.color_cost_1);

        unitsList.get(13).setFull_image(R.drawable.abaddon_full);
        unitsList.get(13).setIcon_image(R.drawable.a14);
        unitsList.get(13).setMini_image(R.drawable.abaddon_a);
        unitsList.get(13).setSkill_image(R.drawable.abaddon_s);
        unitsList.get(13).setColor_name(R.color.color_cost_3);

        unitsList.get(14).setFull_image(R.drawable.terrorblade_full);
        unitsList.get(14).setIcon_image(R.drawable.a15);
        unitsList.get(14).setMini_image(R.drawable.terrorblade_a);
        unitsList.get(14).setSkill_image(R.drawable.terrorblade_s);
        unitsList.get(14).setColor_name(R.color.color_cost_3);

        unitsList.get(15).setFull_image(R.drawable.lina_full);
        unitsList.get(15).setIcon_image(R.drawable.a16);
        unitsList.get(15).setMini_image(R.drawable.lina_a);
        unitsList.get(15).setSkill_image(R.drawable.lina_s);
        unitsList.get(15).setColor_name(R.color.color_cost_3);

        unitsList.get(16).setFull_image(R.drawable.batrider_full);
        unitsList.get(16).setIcon_image(R.drawable.a17);
        unitsList.get(16).setMini_image(R.drawable.batrider_a);
        unitsList.get(16).setSkill_image(R.drawable.batrider_s);
        unitsList.get(16).setColor_name(R.color.color_cost_1);

        unitsList.get(17).setFull_image(R.drawable.tinker_full);
        unitsList.get(17).setIcon_image(R.drawable.a18);
        unitsList.get(17).setMini_image(R.drawable.tinker_a);
        unitsList.get(17).setSkill_image(R.drawable.tinker_s);
        unitsList.get(17).setColor_name(R.color.color_cost_1);

        unitsList.get(18).setFull_image(R.drawable.gyrocopter_full);
        unitsList.get(18).setIcon_image(R.drawable.a19);
        unitsList.get(18).setMini_image(R.drawable.gyrocopter_a);
        unitsList.get(18).setSkill_image(R.drawable.gyrocopter_s);
        unitsList.get(18).setColor_name(R.color.color_cost_5);

        unitsList.get(19).setFull_image(R.drawable.chaos_knight_full);
        unitsList.get(19).setIcon_image(R.drawable.a20);
        unitsList.get(19).setMini_image(R.drawable.chaos_knight_a);
        unitsList.get(19).setSkill_image(R.drawable.chaos_knight_s);
        unitsList.get(19).setColor_name(R.color.color_cost_2);

        unitsList.get(20).setFull_image(R.drawable.luna_full);
        unitsList.get(20).setIcon_image(R.drawable.a21);
        unitsList.get(20).setMini_image(R.drawable.luna_a);
        unitsList.get(20).setSkill_image(R.drawable.luna_s);
        unitsList.get(20).setColor_name(R.color.color_cost_2);

        unitsList.get(21).setFull_image(R.drawable.sand_king_f);
        unitsList.get(21).setIcon_image(R.drawable.a22);
        unitsList.get(21).setMini_image(R.drawable.sand_king_a);
        unitsList.get(21).setSkill_image(R.drawable.sand_king_s);
        unitsList.get(21).setColor_name(R.color.color_cost_3);

        unitsList.get(22).setFull_image(R.drawable.ogre_magi_f);
        unitsList.get(22).setIcon_image(R.drawable.a23);
        unitsList.get(22).setMini_image(R.drawable.ogre_magi_a);
        unitsList.get(22).setSkill_image(R.drawable.ogre_magi_s);
        unitsList.get(22).setColor_name(R.color.color_cost_1);

        unitsList.get(23).setFull_image(R.drawable.queen_of_pain_f);
        unitsList.get(23).setIcon_image(R.drawable.a24);
        unitsList.get(23).setMini_image(R.drawable.queen_of_pain_a);
        unitsList.get(23).setSkill_image(R.drawable.queen_of_pain_s);
        unitsList.get(23).setColor_name(R.color.color_cost_2);

        unitsList.get(24).setFull_image(R.drawable.kunkka_f);
        unitsList.get(24).setIcon_image(R.drawable.a25);
        unitsList.get(24).setMini_image(R.drawable.kunkka_a);
        unitsList.get(24).setSkill_image(R.drawable.kunkka_s);
        unitsList.get(24).setColor_name(R.color.color_cost_4);

        unitsList.get(25).setFull_image(R.drawable.venomancer_f);
        unitsList.get(25).setIcon_image(R.drawable.a26);
        unitsList.get(25).setMini_image(R.drawable.venomancer_a);
        unitsList.get(25).setSkill_image(R.drawable.venomancer_s);
        unitsList.get(25).setColor_name(R.color.color_cost_3);

        unitsList.get(26).setFull_image(R.drawable.lone_druid_f);
        unitsList.get(26).setIcon_image(R.drawable.a27);
        unitsList.get(26).setMini_image(R.drawable.lone_druid_a);
        unitsList.get(26).setSkill_image(R.drawable.lone_druid_s);
        unitsList.get(26).setColor_name(R.color.color_cost_4);

        unitsList.get(27).setFull_image(R.drawable.axe_f);
        unitsList.get(27).setIcon_image(R.drawable.a28);
        unitsList.get(27).setMini_image(R.drawable.axe_a);
        unitsList.get(27).setSkill_image(R.drawable.axe_s);
        unitsList.get(27).setColor_name(R.color.color_cost_1);

        unitsList.get(28).setFull_image(R.drawable.timbersaw_f);
        unitsList.get(28).setIcon_image(R.drawable.a29);
        unitsList.get(28).setMini_image(R.drawable.timbersaw_a);
        unitsList.get(28).setSkill_image(R.drawable.timbersaw_s);
        unitsList.get(28).setColor_name(R.color.color_cost_2);

        unitsList.get(29).setFull_image(R.drawable.shadow_fiend_f);
        unitsList.get(29).setIcon_image(R.drawable.a30);
        unitsList.get(29).setMini_image(R.drawable.shadow_fiend_a);
        unitsList.get(29).setSkill_image(R.drawable.shadow_fiend_s);
        unitsList.get(29).setColor_name(R.color.color_cost_3);

        unitsList.get(30).setFull_image(R.drawable.phantom_assassin_f);
        unitsList.get(30).setIcon_image(R.drawable.a31);
        unitsList.get(30).setMini_image(R.drawable.phantom_assassin_a);
        unitsList.get(30).setSkill_image(R.drawable.phantom_assassin_s);
        unitsList.get(30).setColor_name(R.color.color_cost_3);

        unitsList.get(31).setFull_image(R.drawable.templar_assassin_f);
        unitsList.get(31).setIcon_image(R.drawable.a32);
        unitsList.get(31).setMini_image(R.drawable.templar_assassin_a);
        unitsList.get(31).setSkill_image(R.drawable.templar_assassin_s);
        unitsList.get(31).setColor_name(R.color.color_cost_4);

        unitsList.get(32).setFull_image(R.drawable.puck_f);
        unitsList.get(32).setIcon_image(R.drawable.a33);
        unitsList.get(32).setMini_image(R.drawable.puck_a);
        unitsList.get(32).setSkill_image(R.drawable.puck_s);
        unitsList.get(32).setColor_name(R.color.color_cost_2);

        unitsList.get(33).setFull_image(R.drawable.medusa_f);
        unitsList.get(33).setIcon_image(R.drawable.a34);
        unitsList.get(33).setMini_image(R.drawable.medusa_a);
        unitsList.get(33).setSkill_image(R.drawable.medusa_s);
        unitsList.get(33).setColor_name(R.color.color_cost_4);

        unitsList.get(34).setFull_image(R.drawable.beastmaster_f);
        unitsList.get(34).setIcon_image(R.drawable.a35);
        unitsList.get(34).setMini_image(R.drawable.beastmaster_a);
        unitsList.get(34).setSkill_image(R.drawable.beastmaster_s);
        unitsList.get(34).setColor_name(R.color.color_cost_2);

        unitsList.get(35).setFull_image(R.drawable.clockwork_f);
        unitsList.get(35).setIcon_image(R.drawable.a36);
        unitsList.get(35).setMini_image(R.drawable.clockwork_a);
        unitsList.get(35).setSkill_image(R.drawable.clockwork_s);
        unitsList.get(35).setColor_name(R.color.color_cost_1);

        unitsList.get(36).setFull_image(R.drawable.bounty_hunter_f);
        unitsList.get(36).setIcon_image(R.drawable.a37);
        unitsList.get(36).setMini_image(R.drawable.bounty_hunter_a);
        unitsList.get(36).setSkill_image(R.drawable.bounty_hunter_s);
        unitsList.get(36).setColor_name(R.color.color_cost_1);

        unitsList.get(37).setFull_image(R.drawable.necrophos_f);
        unitsList.get(37).setIcon_image(R.drawable.a38);
        unitsList.get(37).setMini_image(R.drawable.necrophos_a);
        unitsList.get(37).setSkill_image(R.drawable.necrophos_s);
        unitsList.get(37).setColor_name(R.color.color_cost_4);

        unitsList.get(38).setFull_image(R.drawable.tiny_f);
        unitsList.get(38).setIcon_image(R.drawable.a39);
        unitsList.get(38).setMini_image(R.drawable.tiny_a);
        unitsList.get(38).setSkill_image(R.drawable.tiny_s);
        unitsList.get(38).setColor_name(R.color.color_cost_1);

        unitsList.get(39).setFull_image(R.drawable.disruptor_f);
        unitsList.get(39).setIcon_image(R.drawable.a40);
        unitsList.get(39).setMini_image(R.drawable.disruptor_a);
        unitsList.get(39).setSkill_image(R.drawable.disruptor_s);
        unitsList.get(39).setColor_name(R.color.color_cost_4);

        unitsList.get(40).setFull_image(R.drawable.juggernaut_f);
        unitsList.get(40).setIcon_image(R.drawable.a41);
        unitsList.get(40).setMini_image(R.drawable.juggernaut_a);
        unitsList.get(40).setSkill_image(R.drawable.juggernaut_s);
        unitsList.get(40).setColor_name(R.color.color_cost_2);

        unitsList.get(41).setFull_image(R.drawable.anti_mage_f);
        unitsList.get(41).setIcon_image(R.drawable.a42);
        unitsList.get(41).setMini_image(R.drawable.anti_mage_a);
        unitsList.get(41).setSkill_image(R.drawable.anti_mage_s);
        unitsList.get(41).setColor_name(R.color.color_cost_1);

        unitsList.get(42).setFull_image(R.drawable.crystal_maiden_f);
        unitsList.get(42).setIcon_image(R.drawable.a43);
        unitsList.get(42).setMini_image(R.drawable.crystal_maiden_a);
        unitsList.get(42).setSkill_image(R.drawable.crystal_maiden_s);
        unitsList.get(42).setColor_name(R.color.color_cost_2);

        unitsList.get(43).setFull_image(R.drawable.razor_f);
        unitsList.get(43).setIcon_image(R.drawable.a44);
        unitsList.get(43).setMini_image(R.drawable.razor_a);
        unitsList.get(43).setSkill_image(R.drawable.razor_s);
        unitsList.get(43).setColor_name(R.color.color_cost_3);

        unitsList.get(44).setFull_image(R.drawable.keeper_of_the_light_f);
        unitsList.get(44).setIcon_image(R.drawable.a45);
        unitsList.get(44).setMini_image(R.drawable.keeper_of_the_light_a);
        unitsList.get(44).setSkill_image(R.drawable.keeper_of_the_light_s);
        unitsList.get(44).setColor_name(R.color.color_cost_4);

        unitsList.get(45).setFull_image(R.drawable.tidehunter_f);
        unitsList.get(45).setIcon_image(R.drawable.a46);
        unitsList.get(45).setMini_image(R.drawable.tidehunter_a);
        unitsList.get(45).setSkill_image(R.drawable.tidehunter_s);
        unitsList.get(45).setColor_name(R.color.color_cost_5);

        unitsList.get(46).setFull_image(R.drawable.tusk_f);
        unitsList.get(46).setIcon_image(R.drawable.a47);
        unitsList.get(46).setMini_image(R.drawable.tusk_a);
        unitsList.get(46).setSkill_image(R.drawable.tusk_s);
        unitsList.get(46).setColor_name(R.color.color_cost_1);

        unitsList.get(47).setFull_image(R.drawable.enchantress_f);
        unitsList.get(47).setIcon_image(R.drawable.a48);
        unitsList.get(47).setMini_image(R.drawable.enchantress_a);
        unitsList.get(47).setSkill_image(R.drawable.enchantress_s);
        unitsList.get(47).setColor_name(R.color.color_cost_1);

        unitsList.get(48).setFull_image(R.drawable.viper_f);
        unitsList.get(48).setIcon_image(R.drawable.a49);
        unitsList.get(48).setMini_image(R.drawable.viper_a);
        unitsList.get(48).setSkill_image(R.drawable.viper_s);
        unitsList.get(48).setColor_name(R.color.color_cost_3);

        unitsList.get(49).setFull_image(R.drawable.alchemist_f);
        unitsList.get(49).setIcon_image(R.drawable.a50);
        unitsList.get(49).setMini_image(R.drawable.alchemist_a);
        unitsList.get(49).setSkill_image(R.drawable.alchemist_s);
        unitsList.get(49).setColor_name(R.color.color_cost_4);

        unitsList.get(50).setFull_image(R.drawable.treant_protector_f);
        unitsList.get(50).setIcon_image(R.drawable.a51);
        unitsList.get(50).setMini_image(R.drawable.treant_protector_a);
        unitsList.get(50).setSkill_image(R.drawable.treant_protector_s);
        unitsList.get(50).setColor_name(R.color.color_cost_3);

        unitsList.get(51).setFull_image(R.drawable.morphling_f);
        unitsList.get(51).setIcon_image(R.drawable.a52);
        unitsList.get(51).setMini_image(R.drawable.morphling_a);
        unitsList.get(51).setSkill_image(R.drawable.morphling_s);
        unitsList.get(51).setColor_name(R.color.color_cost_2);

        unitsList.get(52).setFull_image(R.drawable.lycan_f);
        unitsList.get(52).setIcon_image(R.drawable.a53);
        unitsList.get(52).setMini_image(R.drawable.lycan_a);
        unitsList.get(52).setSkill_image(R.drawable.lycan_s);
        unitsList.get(52).setColor_name(R.color.color_cost_3);

        unitsList.get(53).setFull_image(R.drawable.windranger_f);
        unitsList.get(53).setIcon_image(R.drawable.a54);
        unitsList.get(53).setMini_image(R.drawable.windranger_a);
        unitsList.get(53).setSkill_image(R.drawable.windranger_s);
        unitsList.get(53).setColor_name(R.color.color_cost_3);

        unitsList.get(54).setFull_image(R.drawable.furion_f);
        unitsList.get(54).setIcon_image(R.drawable.a55);
        unitsList.get(54).setMini_image(R.drawable.furion_a);
        unitsList.get(54).setSkill_image(R.drawable.furion_s);
        unitsList.get(54).setColor_name(R.color.color_cost_2);

        return unitsList;
    }

    public static List<ClassList> fullClassList(Activity activity){
        String text = Utils.parseFile(activity,"Class.txt");
        Gson gson = new Gson();
        Type type = new TypeToken<List<ClassList>>() {}.getType();
        List<ClassList> classLists = gson.fromJson(text, type);
        classLists.get(0).setImgClass(R.drawable.assassin);
        classLists.get(1).setImgClass(R.drawable.druid);
        classLists.get(2).setImgClass(R.drawable.hunter);
        classLists.get(3).setImgClass(R.drawable.knight);
        classLists.get(4).setImgClass(R.drawable.mage);
        classLists.get(5).setImgClass(R.drawable.mech);
        classLists.get(6).setImgClass(R.drawable.shaman);
        classLists.get(7).setImgClass(R.drawable.warlock);
        classLists.get(8).setImgClass(R.drawable.warrior);
        classLists.get(9).setImgClass(R.drawable.witcher);
        return classLists;
    }

    public static List<RaceList> fullRaceList(Activity activity){
        String text = Utils.parseFile(activity,"Races.txt");
        Gson gson = new Gson();
        Type type = new TypeToken<List<RaceList>>() {}.getType();
        List<RaceList> raceList = gson.fromJson(text, type);
        raceList.get(0).setImgRace(R.drawable.beast);
        raceList.get(1).setImgRace(R.drawable.cave_clan);
        raceList.get(2).setImgRace(R.drawable.demon);
        raceList.get(3).setImgRace(R.drawable.dragon);
        raceList.get(4).setImgRace(R.drawable.dwarf);
        raceList.get(5).setImgRace(R.drawable.egersis);
        raceList.get(6).setImgRace(R.drawable.feathered);
        raceList.get(7).setImgRace(R.drawable.glacier_clan);
        raceList.get(8).setImgRace(R.drawable.goblin);
        raceList.get(9).setImgRace(R.drawable.human);
        raceList.get(10).setImgRace(R.drawable.kira);
        raceList.get(11).setImgRace(R.drawable.marine);
        raceList.get(12).setImgRace(R.drawable.spirit);
        return raceList;
    }

    public static List<Creep> fullCreepList(Activity activity){
        String text = Utils.parseFile(activity,"Creeps.txt");
        Gson gson = new Gson();
        Type type = new TypeToken<List<Creep>>() {}.getType();
        List<Creep> creepLists = gson.fromJson(text, type);
        return creepLists;
    }
}
