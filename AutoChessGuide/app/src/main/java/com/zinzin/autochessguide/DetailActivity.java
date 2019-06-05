package com.zinzin.autochessguide;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.zinzin.autochessguide.adapter.DetailUnitAdapter;
import com.zinzin.autochessguide.adapter.MiniIconAdapter;
import com.zinzin.autochessguide.model.ClassList;
import com.zinzin.autochessguide.model.RaceList;
import com.zinzin.autochessguide.model.Units;
import com.zinzin.autochessguide.model.UnitsInfo;
import com.zinzin.autochessguide.model.UnitsTags;
import com.zinzin.autochessguide.utils.SetImage;
import com.zinzin.autochessguide.utils.Utils;
import com.zinzin.autochessguide.view.CustomLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private List<ClassList> classLists = new ArrayList<>();
    private List<RaceList> raceLists = new ArrayList<>();
    private MiniIconAdapter miniIconAdapter;
    private DetailUnitAdapter detailUnitAdapter;
    private ImageView ivFullUnit, ivSkillUnit;
    private TextView tvName, tvCost, tvHeart, tvArmor, tvMres, tvAtkDame, tvAtkSpd, tvAtkRange;
    private TextView tvSkillName, tvSkillStatus, tvSkillDes, tvSkillTag;
    private RecyclerView rcvUnit,rcvMiniIcon;
    private Units units;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String unitJson = getIntent().getStringExtra("unit");
        Gson gson = new Gson();
        units = gson.fromJson(unitJson, Units.class);
        classLists = SetImage.fullClassList(this);
        raceLists = SetImage.fullRaceList(this);
        initView();
    }

    private void initView() {
        ivFullUnit = findViewById(R.id.iv_units_full);
        ivSkillUnit = findViewById(R.id.iv_skill);
        tvName = findViewById(R.id.tv_name);
        tvCost = findViewById(R.id.tv_cost);
        tvHeart = findViewById(R.id.tv_heart);
        tvArmor = findViewById(R.id.tv_armor);
        tvMres = findViewById(R.id.tv_magical_resistance);
        tvAtkDame = findViewById(R.id.tv_atk_dame);
        tvAtkSpd = findViewById(R.id.tv_atk_speed);
        tvAtkRange = findViewById(R.id.tv_atk_range);
        tvSkillName = findViewById(R.id.tv_name_skill);
        tvSkillStatus = findViewById(R.id.tv_status_skill);
        tvSkillDes = findViewById(R.id.tv_des_skill);
        tvSkillTag = findViewById(R.id.tv_tag_skill);
//        rcvUnit = findViewById(R.id.iv_units_full);
        rcvMiniIcon = findViewById(R.id.rcv_mini);
        rcvUnit = findViewById(R.id.rcv_detail_unit);
        setData();
        setUpRcv();
    }

    private void setData() {
        ivFullUnit.setImageDrawable(getResources().getDrawable(units.getFull_image()));
        ivSkillUnit.setImageDrawable(getResources().getDrawable(units.getSkill_image()));
        tvName.setText(units.getName()+" ("+units.getDotaConvert()+")");
        tvName.setTextColor(getResources().getColor(units.getColor_name()));
        tvCost.setText("Cost: "+ units.getCost()+"$");
        tvHeart.setText("Heart: "+ Utils.linkStringFromArray(units.getHealth()));
        tvArmor.setText("Armor: "+Utils.linkStringFromArray(units.getArmor()));
        tvMres.setText("Magical Resistance: "+units.getResistance());
        tvAtkDame.setText("Attack Damage: "+Utils.linkStringFromArray(units.getAttack()));
        tvAtkDame.setText("Attack Speed: "+units.getSpeed());
        tvAtkDame.setText("Attack Range: "+units.getRange());
        tvSkillName.setText(units.getSkill().get(0).getName());
        tvSkillStatus.setText(units.getSkill().get(0).getType());
        tvSkillDes.setText(units.getSkill().get(0).getDescription());
        List<UnitsTags> unitsTags = units.getSkill().get(0).getTags();
        StringBuilder stringTag = new StringBuilder();
        for(int i = 0; i < unitsTags.size(); i++){
           String tag = unitsTags.get(i).getName()
                   +": "+Utils.linkStringFromArray(unitsTags.get(i).getBonus())+"\n";
            stringTag.append(tag);
        }
        tvSkillTag.setText(stringTag.toString().substring(0,stringTag.toString().length()-2));
    }

    private void setUpRcv() {
        List<Integer> listImg = new ArrayList<>();
        List<UnitsInfo> unitsInfos = new ArrayList<>();
        for(ClassList class_: classLists){
            if(class_.getName().equals(units.getClass_())){
                addClass(class_, listImg,unitsInfos);
            }
        }
        for(RaceList race: raceLists){
            if(race.getName().equals(units.getRace().get(0))){
                addRace(race,listImg,unitsInfos);
            }
            if(units.getRace().size()> 1){
                if(race.getName().equals(units.getRace().get(1))){
                    addRace(race,listImg,unitsInfos);
                }
            }
        }
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcvMiniIcon.setLayoutManager(layoutManager);
        miniIconAdapter = new MiniIconAdapter(this, listImg);
        rcvMiniIcon.setAdapter(miniIconAdapter);
        LinearLayoutManager layoutManager2
                = new CustomLayoutManager(this);
        rcvUnit.setLayoutManager(layoutManager2);
        detailUnitAdapter = new DetailUnitAdapter(this, unitsInfos);
        rcvUnit.setAdapter(detailUnitAdapter);
    }

    private void addClass(ClassList class_, List<Integer> listImg, List<UnitsInfo> unitsInfos) {
        listImg.add(class_.getImgClass());
        List<String> bonus = class_.getBonus();
        StringBuilder stringBonus = new StringBuilder();
        for(int i = 0; i < bonus.size(); i++){
            stringBonus.append(bonus.get(i)).append("\n");
        }
        unitsInfos.add(new UnitsInfo(class_.getImgClass(),class_.getName(),"Class",stringBonus.toString().substring(0,stringBonus.toString().length()-2)));

    }

    private void addRace(RaceList race, List<Integer> listImg, List<UnitsInfo> unitsInfos) {
        listImg.add(race.getImgRace());
        List<String> bonus = race.getBonus();
        StringBuilder stringBonus = new StringBuilder();
        for(int i = 0; i < bonus.size(); i++){
            stringBonus.append(bonus.get(i)).append("\n");
        }
        unitsInfos.add(new UnitsInfo(race.getImgRace(),race.getName(),"Race",stringBonus.toString().substring(0,stringBonus.toString().length()-2)));

    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
