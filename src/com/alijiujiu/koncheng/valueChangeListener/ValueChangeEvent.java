package com.alijiujiu.koncheng.valueChangeListener;

import java.util.EventObject;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;

public class ValueChangeEvent extends EventObject {

	/**
	 * 事件类
	 */
	private static final long serialVersionUID = 1L;
	
	private BDLocation location;
	private PoiResult result;
	private PoiDetailResult detailResult;

	public ValueChangeEvent(Object source) {
		this(source, null, null, null);
	}

	public ValueChangeEvent(Object source, BDLocation newLocation) {
		this(source, newLocation, null, null);
	}

	public ValueChangeEvent(Object source, PoiResult newResult) {
		this(source, null, newResult, null);
	}

	public ValueChangeEvent(Object source, PoiDetailResult newDetailResult) {
		this(source, null, null, newDetailResult);
	}

	public ValueChangeEvent(Object source, BDLocation newLocation, PoiResult newResult,
			PoiDetailResult newDetailResult) {
		super(source);
		location = newLocation;
		result = newResult;
		detailResult = newDetailResult;
	}

	public BDLocation getLocation() {
		return location;
	}

	public PoiResult getResult() {
		return result;
	}

	public PoiDetailResult getDetailResult() {
		return detailResult;
	}
}
