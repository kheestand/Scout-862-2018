package com.example.kyle.scout_862_template;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapThumbnail;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.example.kyle.scout_862_template.BlueAllianceData.DataFetcher;
import com.example.kyle.scout_862_template.EventCards.ListClass;
import com.example.kyle.scout_862_template.EventCards.RecycleListAdapter;
import com.example.kyle.scout_862_template.Scout862.MatchDatabase;
import com.example.kyle.scout_862_template.Scout862.PictureHandler;
import com.example.kyle.scout_862_template.Tabs.ViewPagerAdapter;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class MatchScouting extends AppCompatActivity {

    public static MatchDatabase matchDatabase;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.previousMatchArrow)
    AwesomeTextView previousMatchButton;
    @BindView(R.id.nextMatchArrow)
    AwesomeTextView nextMatchButton;
    @BindView(R.id.matchNumber)
    AwesomeTextView matchNumber;
    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout slidingPaneLayout;
    @BindView(R.id.robotThumbnail)
    BootstrapThumbnail robotThumbnail;
    @BindView(R.id.teamNumber)
    AwesomeTextView teamNumber;
    @BindView(R.id.robot_picture)
    BootstrapThumbnail robotPicture;
    @BindView(R.id.alliance_number)
    AwesomeTextView allianceNumber;
    @BindView(R.id.long_team_name)
    AwesomeTextView longTeamName;
    @BindView(R.id.team_location)
    AwesomeTextView teamLocation;
    @BindView(R.id.team_alliance_text)
    AwesomeTextView teamAllianceTextbox;
    @BindView(R.id.events_scroller)
    RecyclerView eventScroller;
    private DataFetcher fetcher = new DataFetcher();
    private File selectedFileFromPicker = MainMenu.selectedFileFromPicker;
    private PictureHandler pictureHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_match_scouting);
        ButterKnife.bind(this);
        matchDatabase = new MatchDatabase(selectedFileFromPicker);
        try {
            matchDatabase.importMatchSheet(true);
            fetcher.makeCache();
        } catch (IOException | ParseException | InvalidFormatException e) {
            e.printStackTrace();
        }

        setUpEventScroller();
        setUpTabHost();
        setColors();

        matchNumber.setMarkdownText(String.valueOf(matchDatabase.getMatchNumber()));
        nextMatchButton.setMarkdownText("{fa_arrow_right}");
        previousMatchButton.setMarkdownText("{fa_arrow_left}");
        pictureHandler = new PictureHandler(this);
        teamNumber.setMarkdownText(String.valueOf(matchDatabase.getInt(0)));
        allianceNumber.setMarkdownText(matchDatabase.getAllianceColor());
        allianceNumber.setVisibility(View.GONE);
        teamAllianceTextbox.setVisibility(View.GONE);
        setTeamMetadata(String.valueOf(matchDatabase.getInt(0)));
    }

    @Override
    public void onBackPressed() {
        if (slidingPaneLayout != null &&
                (slidingPaneLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || slidingPaneLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            slidingPaneLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.previousMatchArrow)
    public void goToPreviousMatch() {
        ViewPagerAdapter viewPagerAdapter = (ViewPagerAdapter) viewPager.getAdapter();
        viewPagerAdapter.autoFragment.writeTab();
        viewPagerAdapter.teleopFragment.writeTab();
        viewPagerAdapter.endGameFragment.writeTab();
        int matchCounter = matchDatabase.getMatchNumber();
        try {
            matchDatabase.writeMatchToSheet();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (matchCounter - 1 > 0) {
            matchDatabase.setMatchNumber(matchCounter - 1);
            matchNumber.setMarkdownText(String.valueOf(matchDatabase.getMatchNumber()));
            viewPager.setCurrentItem(0, true);
            viewPagerAdapter.autoFragment.readTab();
            viewPagerAdapter.teleopFragment.readTab();
            viewPagerAdapter.endGameFragment.readTab();
            teamNumber.setMarkdownText(String.valueOf(matchDatabase.getInt(0)));
            setTeamMetadata(String.valueOf(matchDatabase.getInt(0)));
        }
    }

    @OnLongClick(R.id.previousMatchArrow)
    public boolean goToPreviousMatchByFive() {
        ViewPagerAdapter viewPagerAdapter = (ViewPagerAdapter) viewPager.getAdapter();
        viewPagerAdapter.autoFragment.writeTab();
        viewPagerAdapter.teleopFragment.writeTab();
        viewPagerAdapter.endGameFragment.writeTab();
        int matchCounter = matchDatabase.getMatchNumber();
        try {
            matchDatabase.writeMatchToSheet();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (matchCounter - 5 > 0) {
            matchDatabase.setMatchNumber(matchCounter - 5);
            matchNumber.setMarkdownText(String.valueOf(matchDatabase.getMatchNumber()));
            viewPager.setCurrentItem(0, true);
            viewPagerAdapter.autoFragment.readTab();
            viewPagerAdapter.teleopFragment.readTab();
            viewPagerAdapter.endGameFragment.readTab();
            teamNumber.setMarkdownText(String.valueOf(matchDatabase.getInt(0)));
            setTeamMetadata(String.valueOf(matchDatabase.getInt(0)));
        }
        return true;
    }

    @OnLongClick(R.id.nextMatchArrow)
    public boolean goToNextMatchByFive() {
        ViewPagerAdapter viewPagerAdapter = (ViewPagerAdapter) viewPager.getAdapter();
        viewPager.setCurrentItem(2);
        viewPagerAdapter.autoFragment.writeTab();
        viewPagerAdapter.teleopFragment.writeTab();
        viewPagerAdapter.endGameFragment.writeTab();
        int matchCounter = matchDatabase.getMatchNumber();
        try {
            matchDatabase.writeMatchToSheet();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (matchCounter + 5 <= matchDatabase.getMaxMatches() - 2) {
            matchDatabase.setMatchNumber(matchCounter + 5);
            matchNumber.setMarkdownText(String.valueOf(matchDatabase.getMatchNumber()));
            viewPager.setCurrentItem(0, true);

            viewPagerAdapter.autoFragment.readTab();
            viewPagerAdapter.teleopFragment.readTab();
            viewPagerAdapter.endGameFragment.readTab();
            teamNumber.setMarkdownText(String.valueOf(matchDatabase.getInt(0)));
            setTeamMetadata(String.valueOf(matchDatabase.getInt(0)));
        }
        return true;
    }

    @OnClick(R.id.nextMatchArrow)
    public void goToNextMatch() {
        ViewPagerAdapter viewPagerAdapter = (ViewPagerAdapter) viewPager.getAdapter();
        viewPager.setCurrentItem(2);
        viewPagerAdapter.autoFragment.writeTab();
        viewPagerAdapter.teleopFragment.writeTab();
        viewPagerAdapter.endGameFragment.writeTab();
        int matchCounter = matchDatabase.getMatchNumber();
        try {
            matchDatabase.writeMatchToSheet();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (matchCounter + 1 <= matchDatabase.getMaxMatches() - 2) {
            matchDatabase.setMatchNumber(matchCounter + 1);
            matchNumber.setMarkdownText(String.valueOf(matchDatabase.getMatchNumber()));
            viewPager.setCurrentItem(0, true);
            viewPagerAdapter.autoFragment.readTab();
            viewPagerAdapter.teleopFragment.readTab();
            viewPagerAdapter.endGameFragment.readTab();
            teamNumber.setMarkdownText(String.valueOf(matchDatabase.getInt(0)));
            setTeamMetadata(String.valueOf(matchDatabase.getInt(0)));
        }
    }

    private void setTeamMetadata(String teamStr) {
        if (teamStr.equals(""))
            return;
        try {
            Bitmap pictureFromDatabase = pictureHandler.getRobotPicture(String.valueOf(matchDatabase.getInt(0)));
            robotThumbnail.setImageBitmap(pictureFromDatabase);
            robotPicture.setImageBitmap(pictureFromDatabase);

            JSONObject foundTeam = fetcher.getTeamObject(teamStr);

            if (foundTeam == null)
                return;

            String teamLocationFromFile = foundTeam.get("city").toString() + ", " + foundTeam.get("state_prov").toString() + ", " + foundTeam.get("country");
            String teamNicknameFromFile = foundTeam.get("nickname").toString();
            longTeamName.setMarkdownText(teamNicknameFromFile);
            teamLocation.setMarkdownText(teamLocationFromFile);
            makeMatchCards(teamStr);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void makeMatchCards(String team) {
        List<ListClass> matchCards = new ArrayList<>();
        JSONArray eventsCompeted = null;
        try {
            eventsCompeted = fetcher.getRobotEventKeys(team);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        if (eventsCompeted == null)
            return;
        else {
            for (int index = 0; index < eventsCompeted.size(); index++) {
                String currentEvent = eventsCompeted.get(index).toString();
                if (currentEvent.contains(Constants.year)) {
                    try {
                        JSONObject simpleEvent = fetcher.getSimpleEventFromCache(currentEvent);
                        JSONObject teamEventObj = fetcher.getRobotEventObjectFromCache(team, currentEvent);
                        matchCards.add(new ListClass(simpleEvent, teamEventObj, null));
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        RecycleListAdapter recycleListAdapter = new RecycleListAdapter(matchCards);
        eventScroller.setAdapter(recycleListAdapter);
    }

    private void setColors() {
        nextMatchButton.setTextColor(Constants.accentColor);
        previousMatchButton.setTextColor(Constants.accentColor);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setBackgroundColor(Constants.primaryColor);
        tabLayout.setSelectedTabIndicatorColor(Constants.accentColor);
        tabLayout.setTabTextColors(ColorStateList.valueOf(Constants.textColor));
    }

    private void setUpTabHost() {
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_1)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_2)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_3)));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
    }

    private void setUpEventScroller() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        eventScroller.setLayoutManager(linearLayoutManager);
        eventScroller.setAdapter(null);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(eventScroller.getContext(), linearLayoutManager.getOrientation());
        eventScroller.addItemDecoration(dividerItemDecoration);

        slidingPaneLayout.setScrollableView(eventScroller);

        slidingPaneLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                switch (newState) {
                    case EXPANDED:
                        robotThumbnail.setImageAlpha(0);
                        robotThumbnail.setBorderDisplayed(false);
                        robotPicture.setImageAlpha(255);
                        allianceNumber.setVisibility(View.VISIBLE);
                        teamAllianceTextbox.setVisibility(View.VISIBLE);
                        break;
                    case COLLAPSED:
                        robotThumbnail.setImageAlpha(255);
                        robotPicture.setImageAlpha(0);
                        robotThumbnail.setBorderDisplayed(true);
                        allianceNumber.setVisibility(View.GONE);
                        teamAllianceTextbox.setVisibility(View.GONE);
                        break;
                    case DRAGGING:
                        break;
                }
            }
        });
    }
}
