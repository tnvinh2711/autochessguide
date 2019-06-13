package com.zinzin.autochessguide.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zinzin.autochessguide.R;
import com.zinzin.autochessguide.adapter.DetailUnitAdapter;
import com.zinzin.autochessguide.adapter.MiniIconAdapter;
import com.zinzin.autochessguide.model.ClassList;
import com.zinzin.autochessguide.model.RaceList;
import com.zinzin.autochessguide.model.Units;
import com.zinzin.autochessguide.model.UnitsInfo;
import com.zinzin.autochessguide.model.UnitsTags;
import com.zinzin.autochessguide.utils.Utils;
import com.zinzin.autochessguide.view.CustomLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class DetailFragment extends Fragment {
    public static String TAG = DetailFragment.class.getSimpleName();
    private List<ClassList> classLists = new ArrayList<>();
    private List<RaceList> raceLists = new ArrayList<>();
    private MiniIconAdapter miniIconAdapter;
    private DetailUnitAdapter detailUnitAdapter;
    private ImageView ivFullUnit, ivSkillUnit;
    private TextView tvName, tvCost, tvHeart, tvArmor, tvMres, tvAtkDame, tvAtkSpd, tvAtkRange, tvStat;
    private TextView tvSkillName, tvSkillStatus, tvSkillDes, tvSkillTag;
    private RecyclerView rcvUnit, rcvMiniIcon;
    private Units units;
    private DrawerLocker drawerLocker;

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        ((DrawerLocker) getActivity()).setDrawerEnabled(true);
        initView(view);
        return view;
    }

    public void setData(Units units, List<RaceList> raceList, List<ClassList> classList) {
        this.units = units;
        this.raceLists.addAll(raceList);
        this.classLists.addAll(classList);
    }

    public interface DrawerLocker {
        void setDrawerEnabled(boolean enabled);
    }

    private void initView(View view) {
        Utils.hideSoftKeyboard(getActivity());
        ivFullUnit = view.findViewById(R.id.iv_units_full);
        ivSkillUnit = view.findViewById(R.id.iv_skill);
        tvName = view.findViewById(R.id.tv_name);
        tvStat = view.findViewById(R.id.tv_stat);
        tvCost = view.findViewById(R.id.tv_cost);
        tvHeart = view.findViewById(R.id.tv_heart);
        tvArmor = view.findViewById(R.id.tv_armor);
        tvMres = view.findViewById(R.id.tv_magical_resistance);
        tvAtkDame = view.findViewById(R.id.tv_atk_dame);
        tvAtkSpd = view.findViewById(R.id.tv_atk_speed);
        tvAtkRange = view.findViewById(R.id.tv_atk_range);
        tvSkillName = view.findViewById(R.id.tv_name_skill);
        tvSkillStatus = view.findViewById(R.id.tv_status_skill);
        tvSkillDes = view.findViewById(R.id.tv_des_skill);
        tvSkillTag = view.findViewById(R.id.tv_tag_skill);
//        rcvUnit = findViewById(R.id.iv_units_full);
        rcvMiniIcon = view.findViewById(R.id.rcv_mini);
        rcvUnit = view.findViewById(R.id.rcv_detail_unit);
        setData();
        setUpRcv();
    }

    private void setData() {
        if (units.getBuff() == 0) {
            tvStat.setBackgroundResource(R.drawable.background_buff);
            tvStat.setText("BUFFED");
            tvStat.setVisibility(View.VISIBLE);
        } else if (units.getNerf() == 0) {
            tvStat.setBackgroundResource(R.drawable.background_nerf);
            tvStat.setText("NERFED");
            tvStat.setVisibility(View.VISIBLE);
        } else if (units.getBuff() != 0 && units.getNerf() != 0) {
            tvStat.setVisibility(View.GONE);
        }
        ivFullUnit.setImageDrawable(getResources().getDrawable(units.getFull_image()));
        Glide.with(getActivity()).load(getResources().getDrawable(units.getSkill_image())).apply(RequestOptions.circleCropTransform()).into(ivSkillUnit);
        tvName.setText(units.getName() + " (" + units.getDotaConvert() + ")");
        tvName.setTextColor(getResources().getColor(units.getColor_name()));
        tvCost.setText("Cost: " + units.getCost() + "$");
        tvHeart.setText("Heart: " + Utils.linkStringFromArray(units.getHealth()));
        tvArmor.setText("Armor: " + Utils.linkStringFromArray(units.getArmor()));
        tvMres.setText("Magical Resistance: " + units.getResistance());
        tvAtkDame.setText("Attack Damage: " + Utils.linkStringFromArray(units.getAttack()));
        tvAtkSpd.setText("Attack Speed: " + units.getSpeed());
        tvAtkRange.setText("Attack Range: " + units.getRange());
        tvSkillName.setText(units.getSkill().get(0).getName());
        tvSkillStatus.setText(units.getSkill().get(0).getType());
        tvSkillDes.setText(units.getSkill().get(0).getDescription());
        List<UnitsTags> unitsTags = units.getSkill().get(0).getTags();
        StringBuilder stringTag = new StringBuilder();
        for (int i = 0; i < unitsTags.size(); i++) {
            String tag = unitsTags.get(i).getName()
                    + ": " + Utils.linkStringFromArray(unitsTags.get(i).getBonus()) + "\n";
            stringTag.append(tag);
        }
        tvSkillTag.setText(stringTag.toString().substring(0, stringTag.toString().length() - 2));
    }

    private void setUpRcv() {
        List<Integer> listImg = new ArrayList<>();
        List<UnitsInfo> unitsInfos = new ArrayList<>();
        for (ClassList class_ : classLists) {
            if (class_.getName().equals(units.getClass_())) {
                addClass(class_, listImg, unitsInfos);
            }
        }
        for (RaceList race : raceLists) {
            if (race.getName().equals(units.getRace().get(0))) {
                addRace(race, listImg, unitsInfos);
            }
            if (units.getRace().size() > 1) {
                if (race.getName().equals(units.getRace().get(1))) {
                    addRace(race, listImg, unitsInfos);
                }
            }
        }
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rcvMiniIcon.setLayoutManager(layoutManager);
        miniIconAdapter = new MiniIconAdapter(getActivity(), listImg);
        rcvMiniIcon.setAdapter(miniIconAdapter);
        LinearLayoutManager layoutManager2
                = new CustomLayoutManager(getActivity());
        rcvUnit.setLayoutManager(layoutManager2);
        detailUnitAdapter = new DetailUnitAdapter(getActivity(), unitsInfos);
        rcvUnit.setAdapter(detailUnitAdapter);
    }

    private void addClass(ClassList class_, List<Integer> listImg, List<UnitsInfo> unitsInfos) {
        listImg.add(class_.getImgClass());
        List<String> bonus = class_.getBonus();
        StringBuilder stringBonus = new StringBuilder();
        for (int i = 0; i < bonus.size(); i++) {
            stringBonus.append(bonus.get(i)).append("\n");
        }
        unitsInfos.add(new UnitsInfo(class_.getImgClass(), class_.getName(), "Class", stringBonus.toString().substring(0, stringBonus.toString().length() - 2)));

    }

    private void addRace(RaceList race, List<Integer> listImg, List<UnitsInfo> unitsInfos) {
        listImg.add(race.getImgRace());
        List<String> bonus = race.getBonus();
        StringBuilder stringBonus = new StringBuilder();
        for (int i = 0; i < bonus.size(); i++) {
            stringBonus.append(bonus.get(i)).append("\n");
        }
        unitsInfos.add(new UnitsInfo(race.getImgRace(), race.getName(), "Race", stringBonus.toString().substring(0, stringBonus.toString().length() - 2)));

    }

    @Override
    public void onDestroy() {
        ((DrawerLocker) getActivity()).setDrawerEnabled(false);
        super.onDestroy();
    }
}
