﻿package com.datang.miou.views.gen;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.datang.miou.FragmentSupport;
import com.datang.miou.R;
import com.datang.miou.views.gen.params.GenParamsGsmDataFragment;
import com.datang.miou.views.gen.params.GenParamsLteDataFragment;
import com.datang.miou.views.gen.params.GenParamsTdDataFragment;
import com.datang.miou.views.gen.params.GenParamsTripleIdleFragment;
import com.datang.miou.views.gen.params.GenParamsVogsmFragment;
import com.datang.miou.views.gen.params.GenParamsVolteFragment;
import com.datang.miou.views.gen.params.GenParamsVotdFragment;

/**
 * ������
 * 
 * @author suntongwei
 */
public class GenParamsFragment extends FragmentSupport {
	public static final int PARAM_PAGES = 7;
	private ViewPager mViewPager;
	private TextView[] textViews;
	private TextView indicator;
	private View mView;
	private TableLayout servingCellInfoTable;
	public static boolean tableRowStyleFlag = true;
	private TableLayout neighborCellInfoTable;
	private TextView mmcMncTextView;
	private TextView tmTextView;
	private TextView ulDlFreqTextView;
	private TextView ulDlBandwidthTextView;
	private TextView workModeTextView;
	private TextView bandTextView;
	private TextView earfenTextView;
	private TextView pciTextView;
	private TextView ecgiTextView;
	private TextView rrcStateTextView;
	private TextView emmStateTextView;
	
	private class ViewPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO ���������������������������
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO ���������������������������
			
		}

		@Override
		public void onPageSelected(int position) {
			// TODO ���������������������������
			textViews[position].setTextColor(Color.BLUE);
			for (int i = 0; i < PARAM_PAGES; i++) {		
				if (i != position) {
					textViews[i].setTextColor(Color.BLACK);
				}
			}
		}
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO ���������������������������
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO ���������������������������

		/*
		 * ������������������������������������������������������������PageViewer���������������������������
		 */
		if (mView != null) {
			ViewGroup parent = (ViewGroup) mView.getParent();
			if (parent != null) {
				parent.removeView(mView);
			}
			return mView;
		}
		
		mView = inflater.inflate(R.layout.gen_params, container, false);
		mViewPager = (ViewPager) mView.findViewById(R.id.viewPager);
		FragmentManager fm = getFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			@Override
			public int getCount() {
				return PARAM_PAGES;
			}

			@Override
			public Fragment getItem(int pos) {
				// TODO ���������������������������			
				switch (pos) {
					case 0:
						return new GenParamsVolteFragment();
					case 1:
						return new GenParamsLteDataFragment();
					case 2:
						return new GenParamsVotdFragment();
					case 3:
						return new GenParamsTdDataFragment();
					case 4:
						return new GenParamsVogsmFragment();
					case 5:
						return new GenParamsGsmDataFragment();
					case 6:
						return new GenParamsTripleIdleFragment();
					default:
						return null;	
				}
				
			}
		});
		mViewPager.setOnPageChangeListener(new ViewPageChangeListener());
		
		AddPageViewrContents();
		
		AddServingCellInfoTable();
		
		tableRowStyleFlag = true;
		AddNeighborCellInfoTable();

		return mView;
	}

	private void AddNeighborCellInfoTable() {
		// TODO ���������������������������
		neighborCellInfoTable = (TableLayout) mView.findViewById(R.id.neighbor_cell_info_table);
		
		addSevenColumnsForTable(getActivity(), neighborCellInfoTable, "EARFCN");
		addSevenColumnsForTable(getActivity(), neighborCellInfoTable, "PCI");
		addSevenColumnsForTable(getActivity(), neighborCellInfoTable, "RSRP");
	}

	private void AddServingCellInfoTable() {
		// TODO ���������������������������
		servingCellInfoTable = (TableLayout) mView.findViewById(R.id.serving_cell_info_table);
		
		TextView[] views;
		
		views = addFourColumnsForTable(getActivity(), servingCellInfoTable, "MMC/MNC", "TM");
		mmcMncTextView = views[0];
		tmTextView = views[1];
				
		views = addFourColumnsForTable(getActivity(), servingCellInfoTable, "UL/DL Freq", "UL/DL Bandwidth");
		ulDlFreqTextView = views[0];
		ulDlBandwidthTextView = views[1];
		
		views = addFourColumnsForTable(getActivity(), servingCellInfoTable, "Work Mode", "Band");
		workModeTextView = views[0];
		bandTextView = views[1];
		
		views = addFourColumnsForTable(getActivity(), servingCellInfoTable, "EARFEN", "PCI");
		earfenTextView = views[0];
		pciTextView = views[1];
		
		ecgiTextView = addTwoColumnsForTable(getActivity(), servingCellInfoTable, "ECGI");
		
		rrcStateTextView = addTwoColumnsForTable(getActivity(), servingCellInfoTable, "RRC STATE");
		
		emmStateTextView = addTwoColumnsForTable(getActivity(), servingCellInfoTable, "EMM STATE");
		
	}
	
	private static TextView[] addSevenColumnsForTable(Context context, TableLayout table, String name) {
		TableRow row;
		if (tableRowStyleFlag) {
			row = (TableRow) LayoutInflater.from(context).inflate(R.layout.table_row_7_items_blue, null); 
			tableRowStyleFlag = false;
		} else {
			row = (TableRow) LayoutInflater.from(context).inflate(R.layout.table_row_7_items_white, null); 
			tableRowStyleFlag = true;
		}
		
		TextView nameField = (TextView) row.findViewById(R.id.name_textView);
		nameField.setText(name);
		
		table.addView(row);
		
		TextView[] views = { (TextView) row.findViewById(R.id.value1_textView), 
							 (TextView) row.findViewById(R.id.value2_textView), 
							 (TextView) row.findViewById(R.id.value3_textView), 
							 (TextView) row.findViewById(R.id.value4_textView), 
							 (TextView) row.findViewById(R.id.value5_textView), 
							 (TextView) row.findViewById(R.id.value6_textView) };
		return views;
	}
	
	public static TextView addTwoColumnsForTable(Context context, TableLayout table, String name) {
		
		TableRow row;
		if (tableRowStyleFlag) {
			row = (TableRow) LayoutInflater.from(context).inflate(R.layout.table_row_4_items_blue, null); 
			tableRowStyleFlag = false;
		} else {
			row = (TableRow) LayoutInflater.from(context).inflate(R.layout.table_row_4_items_white, null); 
			tableRowStyleFlag = true;
		}
		
		TextView nameField = (TextView) row.findViewById(R.id.name1_textView);
		nameField.setText(name);
		
		table.addView(row);
		
		/* 
		 * ������������������������������TextView���������������������������
		 */
		return (TextView) row.findViewById(R.id.name2_textView);
	}
	
	public static TextView[] addFourColumnsForTable(Context context, TableLayout table, String name1, String name2) {
		TableRow row;
		if (tableRowStyleFlag) {
			row = (TableRow) LayoutInflater.from(context).inflate(R.layout.table_row_4_items_blue, null); 
			tableRowStyleFlag = false;
		} else {
			row = (TableRow) LayoutInflater.from(context).inflate(R.layout.table_row_4_items_white, null); 
			tableRowStyleFlag = true;
		}
		
		TextView name1Field = (TextView) row.findViewById(R.id.name1_textView);
		name1Field.setText(name1);
		
		TextView name2Field = (TextView) row.findViewById(R.id.name2_textView);
		name2Field.setText(name2);
		
		table.addView(row);
		
		TextView[] views = { (TextView) row.findViewById(R.id.value1_textView), (TextView) row.findViewById(R.id.value2_textView) };	
		return views;
	}
	
	private void AddPageViewrContents() {
		// TODO ���������������������������
		textViews = new TextView[PARAM_PAGES];
		
		indicator = (TextView) mView.findViewById(R.id.indicator_1);
		indicator.setTextColor(Color.BLUE);
		textViews[0] = indicator;
		
		indicator = (TextView) mView.findViewById(R.id.indicator_2);
		textViews[1] = indicator;
		
		indicator = (TextView) mView.findViewById(R.id.indicator_3);
		textViews[2] = indicator;
		
		indicator = (TextView) mView.findViewById(R.id.indicator_4);
		textViews[3] = indicator;
		
		indicator = (TextView) mView.findViewById(R.id.indicator_5);
		textViews[4] = indicator;
		
		indicator = (TextView) mView.findViewById(R.id.indicator_6);
		textViews[5] = indicator;
		
		indicator = (TextView) mView.findViewById(R.id.indicator_7);
		textViews[6] = indicator;
	}	
}
